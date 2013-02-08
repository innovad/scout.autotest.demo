/**
 *
 */
package org.eclipse.autotest.client;

import junit.framework.Assert;

import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * You can do user dependent tests using the {@link ClientTest#runAs()} annotation
 * 
 * @author Adrian Moser
 */
@RunWith(TychoSurefireClientTestRunner.class)
@TychoSurefireClientTestRunner.ClientTest(clientSessionClass = ClientSession.class, runAs = "allen")
public class UserDependentTest {

  @Test
  public void testSession() throws Exception {
    ClientSession clientSession = ClientSession.get();
    Assert.assertTrue("Session is active", clientSession.isActive());
    Assert.assertTrue("Session is loaded", clientSession.isLoaded());
    Assert.assertEquals("User", "allen", ClientSession.get().getUserId());
  }

}
