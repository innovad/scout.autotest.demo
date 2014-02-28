package org.eclipse.scout.autotest.server.services.lookup;

import org.eclipse.scout.autotest.shared.services.lookup.ICompanyLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

public class CompanyLookupService extends AbstractSqlLookupService implements ICompanyLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT COMPANY_NR, NAME FROM COMPANY";
  }
}
