/**
 *
 */
package org.eclipse.autotest.client.test;

/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.testing.shared.services.common.exceptionhandler.ProcessingRuntimeExceptionUnwrappingStatement;
import org.eclipse.scout.testing.client.DefaultTestClientSessionProvider;
import org.eclipse.scout.testing.client.ITestClientSessionProvider;
import org.eclipse.scout.testing.client.runner.RunAftersInSeparateScoutClientSession;
import org.eclipse.scout.testing.client.runner.RunBeforesInSeparateScoutClientSession;
import org.eclipse.scout.testing.client.runner.ScoutClientJobWrapperStatement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * JUnit test runner that runs the annotated test class within a Scout client job. Test cases executed by this runner
 * may be configured with a {@link ClientTest} annotation.
 */
public class TychoSurefireClientTestRunner extends BlockJUnit4ClassRunner {

  private static ITestClientSessionProvider s_defaultClientSessionProvider = new DefaultTestClientSessionProvider();
  private static Class<? extends IClientSession> s_defaultClientSessionClass;
  private static final IScoutLogger LOG;
  private static final IClientTestEnvironmentFactory FACTORY;

  static {
    LOG = ScoutLogManager.getLogger(TychoSurefireClientTestRunner.class);
    FACTORY = createClientTestEnvironmentFactory();
  }

  private final IClientSession m_clientSession;

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE, ElementType.METHOD})
  public @interface ClientTest {

    Class<? extends IClientSession> clientSessionClass() default IClientSession.class;

    Class<? extends ITestClientSessionProvider> sessionProvider() default NullTestClientSessionProvider.class;

    String runAs() default "";

    boolean forceNewSession() default false;
  }

  /**
   * Null-provider used as default value in the {@link ClientTest} annotation (since annotation values must not be
   * <code>null</code>).
   */
  public interface NullTestClientSessionProvider extends ITestClientSessionProvider {
  }

  /**
   * @param klass
   * @throws InitializationError
   */
  public TychoSurefireClientTestRunner(Class<?> klass) throws InitializationError {
    super(klass);

    // *** Must inject stuff before Client Session instantiation ***
    if (FACTORY != null) {
      setDefaultClientSessionClass(FACTORY.getDefaultClientSessionClass());
      FACTORY.installCookieStore();
      FACTORY.installNetAuthenticator();
    }
    // ***

    try {
      m_clientSession = getOrCreateClientSession(klass.getAnnotation(ClientTest.class), null);
    }
    catch (InitializationError e) {
      throw e;
    }
    catch (Exception e) {
      List<Throwable> errors = new ArrayList<Throwable>();
      errors.add(e);
      throw new InitializationError(errors);
    }
  }

  public static ITestClientSessionProvider getDefaultClientSessionProvider() {
    return s_defaultClientSessionProvider;
  }

  public static void setDefaultClientSessionProvider(ITestClientSessionProvider defaultClientSessionProvider) {
    s_defaultClientSessionProvider = defaultClientSessionProvider;
  }

  public static Class<? extends IClientSession> getDefaultClientSessionClass() {
    return s_defaultClientSessionClass;
  }

  public static void setDefaultClientSessionClass(Class<? extends IClientSession> defaultClientSessionClass) {
    s_defaultClientSessionClass = defaultClientSessionClass;
  }

  /**
   * @return Returns the default client session class used by this test runner. Defaults to
   *         {@link #getDefaultClientSessionClass()}. Subclasses may override this method to provide another default
   *         value.
   */
  protected Class<? extends IClientSession> defaultClientSessionClass() {
    return getDefaultClientSessionClass();
  }

  /**
   * @return Returns the default client session provider used by this test runner. Defaults to
   *         {@link #getDefaultClientSessionProvider()}. Subclasses may override this method to provide another default
   *         value.
   */
  protected ITestClientSessionProvider defaultClientSessionProvider() {
    return getDefaultClientSessionProvider();
  }

  @Override
  protected Statement withBeforeClasses(Statement statement) {
    // run all methods annotated with @BeforeClass in a separate ServerSession
    List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
    return befores.isEmpty() ? statement : new RunBeforesInSeparateScoutClientSession(m_clientSession, statement, befores, null);
  }

  @Override
  protected Statement withAfterClasses(Statement statement) {
    // run all methods annotated with @AfterClass in a separate ServerSession
    List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(AfterClass.class);
    return afters.isEmpty() ? statement : new RunAftersInSeparateScoutClientSession(m_clientSession, statement, afters, null);
  }

  @Override
  protected Statement methodBlock(FrameworkMethod method) {
    IClientSession clientSession = m_clientSession;
    ClientTest methodLevelClientTest = method.getAnnotation(ClientTest.class);
    if (methodLevelClientTest != null) {
      try {
        ClientTest classLevelClientTest = getTestClass().getJavaClass().getAnnotation(ClientTest.class);
        clientSession = getOrCreateClientSession(classLevelClientTest, methodLevelClientTest);
      }
      catch (final Throwable e) {
        return new Statement() {
          @Override
          public void evaluate() throws Throwable {
            throw e;
          }
        };
      }
    }
    // run each test method in a separate ClientSession
    return new ScoutClientJobWrapperStatement(clientSession, super.methodBlock(method));
  }

  @Override
  @SuppressWarnings("deprecation")
  protected Statement possiblyExpectingExceptions(FrameworkMethod method, Object test, Statement next) {
    // unpack wrapped ProcessingExceptions and rethrow them
    return super.possiblyExpectingExceptions(method, test, new ProcessingRuntimeExceptionUnwrappingStatement(next));
  }

  protected IClientSession getOrCreateClientSession(ClientTest classLevelClientTest, ClientTest methodLevelClientTest) throws Exception {
    // process default values
    Class<? extends IClientSession> clientSessionClass = defaultClientSessionClass();
    ITestClientSessionProvider sessionProvider = defaultClientSessionProvider();
    String runAs = null;
    boolean forceNewSession = false;

    // process class-level client test configuration
    if (classLevelClientTest != null) {
      clientSessionClass = extractClientSessionClass(classLevelClientTest, clientSessionClass);
      sessionProvider = extractSessionProvider(classLevelClientTest, sessionProvider);
      runAs = extractRunAs(classLevelClientTest, runAs);
      forceNewSession = extractForceNewSession(classLevelClientTest, forceNewSession);
    }

    // process method-level client test configuration
    if (methodLevelClientTest != null) {
      clientSessionClass = extractClientSessionClass(methodLevelClientTest, clientSessionClass);
      sessionProvider = extractSessionProvider(methodLevelClientTest, sessionProvider);
      runAs = extractRunAs(methodLevelClientTest, runAs);
      forceNewSession = extractForceNewSession(methodLevelClientTest, forceNewSession);
    }

    // sanity check
    if (clientSessionClass == null) {
      throw new InitializationError("Client session class is not set. Either set the default client session using '"
          + TychoSurefireClientTestRunner.class.getSimpleName()
          + ".setDefaultClientSessionClass' or annotate your test class and/or method with '"
          + ClientTest.class.getSimpleName() + "'");
    }

    // return existing or create new client session
    return sessionProvider.getOrCreateClientSession(clientSessionClass, runAs, forceNewSession);
  }

  /**
   * @param clientTest
   * @return
   */
  protected Class<? extends IClientSession> extractClientSessionClass(ClientTest clientTest, Class<? extends IClientSession> defaultValue) {
    if (clientTest == null || clientTest.clientSessionClass() == null || clientTest.clientSessionClass() == IClientSession.class) {
      return defaultValue;
    }
    return clientTest.clientSessionClass();
  }

  /**
   * @param clientTest
   * @return
   */
  protected ITestClientSessionProvider extractSessionProvider(ClientTest clientTest, ITestClientSessionProvider defaultValue) throws Exception {
    if (clientTest == null || clientTest.sessionProvider() == null || clientTest.sessionProvider() == NullTestClientSessionProvider.class) {
      return defaultValue;
    }
    return clientTest.sessionProvider().newInstance();
  }

  /**
   * @param clientTest
   * @return
   */
  protected String extractRunAs(ClientTest clientTest, String defaultValue) {
    String runAs = defaultValue;
    if (clientTest != null && clientTest.runAs() != null) {
      String s = clientTest.runAs().trim();
      if (s.length() > 0) {
        runAs = s;
      }
    }
    return runAs;
  }

  /**
   * @param clientTest
   * @return
   */
  protected boolean extractForceNewSession(ClientTest clientTest, boolean defaultValue) {
    boolean forceCreateNewSession = defaultValue;
    if (clientTest != null) {
      forceCreateNewSession = clientTest.forceNewSession();
    }
    return forceCreateNewSession;
  }

  private static IClientTestEnvironmentFactory createClientTestEnvironmentFactory() {
    IClientTestEnvironmentFactory factory = null;
    if (Activator.getDefault() != null) {
      // check whether there is a custom test environment factory available
      try {
        Class<?> customSerializerFactory = Activator.getDefault().getBundle().loadClass("org.eclipse.scout.testing.client.runner.CustomClientTestEnvironmentFactory");
        LOG.info("loaded custom object serializer factory: [" + customSerializerFactory + "]");
        if (!IClientTestEnvironmentFactory.class.isAssignableFrom(customSerializerFactory)) {
          LOG.warn("custom object serializer factory is not implementing [" + IClientTestEnvironmentFactory.class + "]");
        }
        else if (Modifier.isAbstract(customSerializerFactory.getModifiers())) {
          LOG.warn("custom object serializer factory is an abstract class [" + customSerializerFactory + "]");
        }
        else {
          factory = (IClientTestEnvironmentFactory) customSerializerFactory.newInstance();
        }
      }
      catch (ClassNotFoundException e) {
        // no custom object serializer factory installed
      }
      catch (Exception e) {
        LOG.warn("Unexpected problem while creating a new instance of custom object serializer factory", e);
      }
    }
    return factory;
  }

}
