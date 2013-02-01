/**
 * 
 */
package org.eclipse.scout.autotest.server.test;

import org.eclipse.autotest.server.test.TychoSurefireServerTestRunner;
import org.eclipse.scout.autotest.server.ServerSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(TychoSurefireServerTestRunner.class)
@TychoSurefireServerTestRunner.ServerTest(runAs = "allen")
public class UserTest {

  @Test
  public void testUser() throws Exception {
    ServerSession session = ServerSession.get();
    Assert.assertNotNull("Session not null", session);
    Assert.assertTrue("Session is active", session.isActive());
    Assert.assertEquals("Allen User", "allen", session.getUserId());
  }

}
