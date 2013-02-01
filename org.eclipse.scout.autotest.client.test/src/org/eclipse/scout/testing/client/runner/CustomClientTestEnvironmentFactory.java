/**
 * 
 */
package org.eclipse.scout.testing.client.runner;

import org.eclipse.autotest.client.test.AutotestClientUtility;
import org.eclipse.autotest.client.test.IClientTestEnvironmentFactory;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.rt.client.IClientSession;

/**
 * @author amo
 */
public class CustomClientTestEnvironmentFactory implements IClientTestEnvironmentFactory {

  @Override
  public void installCookieStore() {
    AutotestClientUtility.installCookieStore();
  }

  @Override
  public void installNetAuthenticator() {
    AutotestClientUtility.installNetAuthenticator();
  }

  @Override
  public Class<? extends IClientSession> getDefaultClientSessionClass() {
    return ClientSession.class;
  }

}
