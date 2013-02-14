/**
 *
 */
package org.eclipse.scout.testing.client.runner;

import org.eclipse.autotest.client.test.AutotestClientUtility;
import org.eclipse.autotest.client.test.IClientTestEnvironment;
import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;

/**
 * @author amo
 */
public class CustomClientTestEnvironment implements IClientTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    AutotestClientUtility.installCookieStore();
    AutotestClientUtility.installNetAuthenticator();
    TychoSurefireClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
  }

  @Override
  public void setupInstanceEnvironment() {
    AutotestClientUtility.installCookieStore();
  }

}
