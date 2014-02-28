package org.eclipse.scout.autotest.shared.services.outline;

import org.eclipse.scout.service.IService2;
import org.eclipse.scout.commons.exception.ProcessingException;

public interface IStandardOutlineService extends IService2{

  public Object[][] getCompanyTableData() throws ProcessingException;
}
