/**
 *
 */
package org.eclipse.scout.testing.server.runner;

import org.eclipse.autotest.server.test.IServerTestEnvironment;
import org.eclipse.autotest.server.test.TychoSurefireServerTestRunner;
import org.eclipse.scout.autotest.server.ServerSession;

/**
 * @author amo
 */
public class CustomServerTestEnvironment implements IServerTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    TychoSurefireServerTestRunner.setDefaultServerSessionClass(ServerSession.class);
    TychoSurefireServerTestRunner.setDefaultPrincipalName("admin");
  }

  @Override
  public void setupInstanceEnvironment() {
  }

}
