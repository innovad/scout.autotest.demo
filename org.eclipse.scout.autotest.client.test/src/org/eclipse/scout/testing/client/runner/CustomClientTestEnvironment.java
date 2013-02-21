/**
 *
 */
package org.eclipse.scout.testing.client.runner;

import org.eclipse.autotest.client.test.AutotestClientUtility;
import org.eclipse.scout.autotest.client.ClientSession;

/**
 * @author amo
 */
public class CustomClientTestEnvironment implements IClientTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    AutotestClientUtility.installCookieStore();
    AutotestClientUtility.installNetAuthenticator();
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
  }

  @Override
  public void setupInstanceEnvironment() {
    AutotestClientUtility.installCookieStore();
  }

}
