package org.eclipse.scout.autotest.server.services.outline;

import org.eclipse.scout.autotest.shared.services.outline.IStandardOutlineService;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public Object[][] getCompanyTableData() throws ProcessingException {
    return SQL.select("" +
        "SELECT COMPANY_NR," +
        "       SHORT_NAME," +
        "       NAME" +
        " FROM  COMPANY");
  }
}
