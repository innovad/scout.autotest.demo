package org.eclipse.autotest.client.test;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Hashtable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.rt.testing.shared.ScoutJUnitPluginTestExecutor;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientAuthenticator;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientSessionCookieStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 * Application for running client tests.
 */
public class AutotestClientTestApplication implements IApplication {

  @Override
  public Object start(IApplicationContext context) throws Exception {
    intallNetAuthenticator();
    installCookieStore();
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
    return new ScoutJUnitPluginTestExecutor().runAllTests();
  }

  /**
   * Installs an authenticator that is aware of different client sessions connecting concurrently to the same URL. The
   * authenticator is registered as an OSGi service and used by Scout's network support which is based on Eclipse's
   * networking features.
   * 
   * @return Returns the authenticator's OSGi service registration.
   */
  private Object intallNetAuthenticator() {
    // add credentials of test users
    MultiClientAuthenticator.addUser("admin", "admin");
    MultiClientAuthenticator.addUser("standard", "standard");

    MultiClientAuthenticator.setDefaultUser("admin");

    BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
    Hashtable<String, Object> map = new Hashtable<String, Object>();
    map.put(Constants.SERVICE_RANKING, 1);
    @SuppressWarnings("rawtypes")
    ServiceRegistration reg = bundleContext.registerService(java.net.Authenticator.class.getName(), new MultiClientAuthenticator(), map);

    // start the netBundle, it is not started from OSGI because no references exists
    Bundle netBundle = Platform.getBundle("org.eclipse.scout.net");
    if (netBundle != null) {
      try {
        netBundle.start();
      }
      catch (Throwable t) {
        netBundle = null;
      }
    }
    return reg;
  }

  /**
   * Installs a cookie store that is aware of different client sessions connecting concurrently to the same URL.
   */
  private void installCookieStore() {
    CookieManager.setDefault(new CookieManager(new MultiClientSessionCookieStore(), CookiePolicy.ACCEPT_ALL));
  }

  @Override
  public void stop() {
  }

}
