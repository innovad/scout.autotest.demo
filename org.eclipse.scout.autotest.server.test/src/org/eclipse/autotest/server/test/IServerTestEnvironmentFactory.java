/**
 * 
 */
package org.eclipse.autotest.server.test;

import org.eclipse.scout.rt.server.IServerSession;

/**
 * @author amo
 */
public interface IServerTestEnvironmentFactory {

  void setup();

  Class<? extends IServerSession> getDefaultServerSessionClass();

  String getDefaultPrincipalName();

}
