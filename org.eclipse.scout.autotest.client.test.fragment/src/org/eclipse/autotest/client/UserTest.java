/**
 * 
 */
package org.eclipse.autotest.client;

import junit.framework.Assert;

import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(TychoSurefireClientTestRunner.class)
@TychoSurefireClientTestRunner.ClientTest(clientSessionClass = ClientSession.class, runAs = "allen")
public class UserTest {

  @Test
  public void testSession() throws Exception {
    ClientSession clientSession = ClientSession.get();
    Assert.assertTrue("Session is active", clientSession.isActive());
    Assert.assertTrue("Session is loaded", clientSession.isLoaded());
    Assert.assertEquals("User", "allen", ClientSession.get().getUserId());
  }

}
