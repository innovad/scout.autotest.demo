package org.eclipse.scout.autotest.server.test;

import org.eclipse.scout.autotest.shared.services.outline.IStandardOutlineService;
import org.eclipse.scout.rt.testing.server.runner.ScoutServerTestRunner;
import org.eclipse.scout.service.SERVICES;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ScoutServerTestRunner.class)
public final class ServerServiceTest {

  @Test
  public void test() throws Exception {
    // Dummy Server Test with Service
    Object[][] data = SERVICES.getService(IStandardOutlineService.class).getCompanyTableData();
    Assert.assertNotNull("Not Null", data);
  }

}
