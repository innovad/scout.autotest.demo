package org.eclipse.scout.autotest.server.services.common.sql;

import org.eclipse.scout.rt.services.common.jdbc.AbstractDerbySqlService;
import org.eclipse.scout.service.IService2;

public class DerbySqlService extends AbstractDerbySqlService implements IService2 {

  @Override
  protected String getConfiguredJdbcMappingName() {
    return "jdbc:derby:C:\\DerbyDB";
  }

  @Override
  protected String getConfiguredUsername() {
    return "minicrm";
  }

  @Override
  protected String getConfiguredPassword() {
    return "minicrm";
  }

}
