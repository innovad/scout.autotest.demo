package org.eclipse.autotest.client.test;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.rt.testing.shared.ScoutJUnitPluginTestExecutor;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;

/**
 * Application for running client tests.
 */
public class AutotestClientTestApplication implements IApplication {

  @Override
  public Object start(IApplicationContext context) throws Exception {
    AutotestClientUtility.installNetAuthenticator();
    AutotestClientUtility.installCookieStore();
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
    return new ScoutJUnitPluginTestExecutor().runAllTests();
  }

  @Override
  public void stop() {
  }

}
