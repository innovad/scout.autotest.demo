package org.eclipse.scout.autotest.server.test;

import org.eclipse.autotest.server.test.TychoSurefireServerTestRunner;
import org.eclipse.scout.autotest.server.ServerSession;
import org.eclipse.scout.autotest.shared.services.outline.IStandardOutlineService;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.eclipse.scout.service.SERVICES;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TychoSurefireServerTestRunner.class)
@ScoutServerTestRunner.ServerTest(serverSessionClass = ServerSession.class, runAs = "admin")
public final class ServerServiceTest {

  @Test
  public void test() throws Exception {
    // Dummy Server Test with Service
    Object[][] data = SERVICES.getService(IStandardOutlineService.class).getCompanyTableData();
    Assert.assertNotNull("Not Null", data);
  }

}
