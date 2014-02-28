package org.eclipse.scout.autotest.shared.security;

import java.security.BasicPermission;

public class ReadCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadCompanyPermission() {
    super("ReadCountry");
  }
}
