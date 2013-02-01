package org.eclipse.scout.autotest.server.test;

import org.eclipse.autotest.server.test.TychoSurefireServerTestRunner;
import org.eclipse.scout.autotest.shared.services.outline.IStandardOutlineService;
import org.eclipse.scout.service.SERVICES;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TychoSurefireServerTestRunner.class)
public final class ServerServiceTest {

  @Test
  public void test() throws Exception {
    // Dummy Server Test with Service
    Object[][] data = SERVICES.getService(IStandardOutlineService.class).getCompanyTableData();
    Assert.assertNotNull("Not Null", data);
  }

}
