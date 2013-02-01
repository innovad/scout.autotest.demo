/**
 * 
 */
package org.eclipse.autotest.client.test;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Hashtable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientAuthenticator;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientSessionCookieStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author amo
 */
public final class AutotestClientUtility {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(AutotestClientUtility.class);

  /**
   * Installs an authenticator that is aware of different client sessions connecting concurrently to the same URL. The
   * authenticator is registered as an OSGi service and used by Scout's network support which is based on Eclipse's
   * networking features.
   * 
   * @return Returns the authenticator's OSGi service registration.
   */
  public static Object installNetAuthenticator() {
    LOG.info("Test Authentication Installation");
    BundleContext bundleContext = FrameworkUtil.getBundle(AutotestClientUtility.class).getBundleContext();
    ServiceRegistration<?> reg = null;
    Hashtable<String, Object> map = new Hashtable<String, Object>();
    map.put(Constants.SERVICE_RANKING, 1);

    // add credentials of test users
    MultiClientAuthenticator.addUser("admin", "manager");
    MultiClientAuthenticator.addUser("allen", "allen");
    MultiClientAuthenticator.addUser("blake", "blake");
    MultiClientAuthenticator.setDefaultUser("admin");

    MultiClientAuthenticator multiClientAuthenticator = new MultiClientAuthenticator();
    reg = bundleContext.registerService(java.net.Authenticator.class.getName(), multiClientAuthenticator, map);

    // start the netBundle, it is not started from OSGI because no references exists
    Bundle netBundle = Platform.getBundle("org.eclipse.scout.net");
    if (netBundle != null) {
      try {
        netBundle.start();
        System.out.println("Testing Net Authenticator STARTED");
      }
      catch (Throwable t) {
        t.printStackTrace();
        netBundle = null;
      }
    }
    else {
      System.out.println("Testing Net Authenticator NOT STARTED");
    }
    return reg;
  }

  /**
   * Installs a cookie store that is aware of different client sessions connecting concurrently to the same URL.
   */
  public static void installCookieStore() {
    CookieManager.setDefault(new CookieManager(new MultiClientSessionCookieStore(), CookiePolicy.ACCEPT_ALL));
  }

}
