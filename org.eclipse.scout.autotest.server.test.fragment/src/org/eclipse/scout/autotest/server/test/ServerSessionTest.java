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
public class ServerSessionTest {

  @Test
  public void testServerSession() throws Exception {
    ServerSession session = ServerSession.get();
    Assert.assertNotNull("Session not null", session);
    Assert.assertTrue("Session is active", session.isActive());
    Assert.assertEquals("Admin User", "admin", session.getUserId());
  }

}
