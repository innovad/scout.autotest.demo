package org.eclipse.autotest.client;

import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.autotest.client.ui.desktop.outlines.pages.CompanyTablePage;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ScoutClientTestRunner.class)
@ScoutClientTestRunner.ClientTest(clientSessionClass = ClientSession.class, runAs = "admin")
public class ClientSessionTest {

  /**
   * Test if ClientSession is available, loaded and active
   */
  @Test
  public void testSession() throws Exception {
    ClientSession clientSession = ClientSession.get();
    Assert.assertTrue("Session is active", clientSession.isActive());
    Assert.assertTrue("Session is loaded", clientSession.isLoaded());
    Assert.assertEquals("User", "admin", ClientSession.get().getUserId());
  }

  /**
   * Test if Desktop is available
   */
  @Test
  public void testDesktop() throws Exception {
    ClientSession clientSession = ClientSession.get();
    IDesktop desktop = clientSession.getDesktop();
    Assert.assertNotNull("desktop must not be null", desktop);
  }

  /**
   * Test database access via service. Note: This test makes assumptions about the content of the database.
   */
  @Test
  public void testTablePage() throws Exception {
    CompanyTablePage page = new CompanyTablePage();
    page.nodeAddedNotify();
    page.loadChildren();

    int count = page.getTable().getRowCount();
    Assert.assertTrue(count > 0);
    System.out.println("Database Row Count: " + count);
  }

}
