package org.eclipse.autotest.client;

import org.eclipse.autotest.client.test.TychoSurfireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TychoSurfireClientTestRunner.class)
@ClientTest(clientSessionClass = ClientSession.class)
public class ClientSessionTest {

  @Test
  public void testDesktop() throws Exception {
//    ClientSession clientSession = ClientSession.get();
//    IDesktop desktop = clientSession.getDesktop();
//    Assert.assertNotNull("desktop must not be null", desktop);
  }

}
