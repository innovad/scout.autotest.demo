package org.eclipse.scout.autotest.server.services.common.sql;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.scout.autotest.server.Activator;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.rt.services.common.jdbc.AbstractDerbySqlService;
import org.eclipse.scout.service.IService2;

public class DerbySqlService extends AbstractDerbySqlService implements IService2 {

  @Override
  protected String getConfiguredJdbcMappingName() {
    String derbyPath = Platform.getBundle(Activator.PLUGIN_ID).getBundleContext().getProperty("derby.path");
    if (StringUtility.isNullOrEmpty(derbyPath)) {
      // Workaround to find DerbyDB during build
      File f = new File(".");
      String path = f.getAbsolutePath();
      path = StringUtility.replace(path, "org.eclipse.scout.autotest.server.test.fragment", "org.eclipse.scout.autotest.parent");
      path = StringUtility.replace(path, "org.eclipse.scout.autotest.client.test.fragment", "org.eclipse.scout.autotest.parent");
      path = StringUtility.removeSuffixes(path, ".");
      derbyPath = path + "DerbyDB";
      System.out.println("DB Path: " + derbyPath);
      // End workaround
    }
    return "jdbc:derby:" + derbyPath;
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
