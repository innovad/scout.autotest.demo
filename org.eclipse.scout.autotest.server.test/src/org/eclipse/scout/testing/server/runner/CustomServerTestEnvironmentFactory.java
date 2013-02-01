/**
 * 
 */
package org.eclipse.scout.testing.server.runner;

import org.eclipse.autotest.server.test.IServerTestEnvironmentFactory;
import org.eclipse.scout.autotest.server.ServerSession;
import org.eclipse.scout.rt.server.IServerSession;

/**
 * @author amo
 */
public class CustomServerTestEnvironmentFactory implements IServerTestEnvironmentFactory {

  @Override
  public void setup() {
  }

  @Override
  public Class<? extends IServerSession> getDefaultServerSessionClass() {
    return ServerSession.class;
  }

  @Override
  public String getDefaultPrincipalName() {
    return "admin";
  }

}
