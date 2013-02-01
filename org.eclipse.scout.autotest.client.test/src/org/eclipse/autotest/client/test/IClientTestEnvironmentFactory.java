/**
 * 
 */
package org.eclipse.autotest.client.test;

import org.eclipse.scout.rt.client.IClientSession;

/**
 * @author amo
 */
public interface IClientTestEnvironmentFactory {

  void installCookieStore();

  void installNetAuthenticator();

  Class<? extends IClientSession> getDefaultClientSessionClass();

}
