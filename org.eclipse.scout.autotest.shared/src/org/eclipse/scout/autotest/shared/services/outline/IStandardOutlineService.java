package org.eclipse.scout.autotest.shared.services.outline;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

public interface IStandardOutlineService extends IService {

  public Object[][] getCompanyTableData() throws ProcessingException;
}
