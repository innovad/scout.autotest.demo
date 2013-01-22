package org.eclipse.scout.autotest.server.test;

import org.eclipse.autotest.server.test.TychoSurfireServerTestRunner;
import org.eclipse.scout.autotest.server.ServerSession;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TychoSurfireServerTestRunner.class)
@ScoutServerTestRunner.ServerTest(serverSessionClass = ServerSession.class, runAs = "admin")
public final class ServerTest {

  @Test
  public void test() throws Exception {
    // Dummy Test
  }

}
