package org.eclipse.autotest.server.test;

import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author amo
 */
public class TychoSurefireServerTestRunner extends ScoutServerTestRunner {

  /**
   * @param klass
   * @throws InitializationError
   */
  public TychoSurefireServerTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
    // would be nice to set default session here, but it is too late
    // workaround: use @ScoutServerTestRunner.ServerTest(serverSessionClass = ServerSession.class, runAs = "admin")
    // ScoutServerTestRunner.setDefaultServerSessionClass(ServerSession.class);
  }

}
