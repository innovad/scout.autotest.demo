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
      // TODO: make this configurable, use same parent pom for Buildhive and manual build
      File f = new File(".");
      String path = f.getAbsolutePath();
      path = StringUtility.removeSuffixes(path, ".");
      String server = "org.eclipse.scout.autotest.server.test.fragment";
      String client = "org.eclipse.scout.autotest.client.test.fragment";
      String parent = "org.eclipse.scout.autotest.parent";
      path = StringUtility.replace(path, "target\\cargo\\configurations\\jetty8x", "");
      path = StringUtility.replace(path, "target/cargo/configurations/jetty8x", "");
      if (path.contains(server)) {
        path = StringUtility.replace(path, server, parent);
        derbyPath = path + "DerbyDB";
      }
      else if (path.contains(client)) {
        path = StringUtility.replace(path, client, parent);
        derbyPath = path + "DerbyDB";
      }
      else if (path.contains(parent)) {
        derbyPath = path + "DerbyDB";
      }
      else {
        derbyPath = path + parent + File.separator + "DerbyDB";
      }
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
