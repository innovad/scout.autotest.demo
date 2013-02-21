/**
 *
 */
package org.eclipse.scout.testing.server.runner;

import org.eclipse.scout.autotest.server.ServerSession;
import org.eclipse.scout.rt.testing.server.runner.IServerTestEnvironment;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;

/**
 * @author amo
 */
public class CustomServerTestEnvironment implements IServerTestEnvironment {

  @Override
  public void setupGlobalEnvironment() {
    ScoutServerTestRunner.setDefaultServerSessionClass(ServerSession.class);
    ScoutServerTestRunner.setDefaultPrincipalName("admin");
  }

  @Override
  public void setupInstanceEnvironment() {
  }

}
