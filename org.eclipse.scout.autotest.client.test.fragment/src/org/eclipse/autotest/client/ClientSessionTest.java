package org.eclipse.autotest.client;

import junit.framework.Assert;

import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ScoutClientTestRunner.class)
@ClientTest(clientSessionClass = ClientSession.class, runAs = "admin")
public class ClientSessionTest {
  private ClientSession m_clientSession;

  @Before
  public void before() throws Exception {
    m_clientSession = ClientSession.get();
  }

  @Test
  public void testDesktop() throws Exception {
    IDesktop desktop = m_clientSession.getDesktop();
    Assert.assertNotNull("desktop must not be null", desktop);
  }

}
