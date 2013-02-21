/**
 *
 */
package org.eclipse.scout.autotest.server.test;

import org.eclipse.scout.autotest.server.ServerSession;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(ScoutServerTestRunner.class)
@ScoutServerTestRunner.ServerTest(runAs = "allen")
public class UserTest {

  @Test
  public void testUser() throws Exception {
    ServerSession session = ServerSession.get();
    Assert.assertNotNull("Session not null", session);
    Assert.assertTrue("Session is active", session.isActive());
    Assert.assertEquals("Allen User", "allen", session.getUserId());
  }

}
