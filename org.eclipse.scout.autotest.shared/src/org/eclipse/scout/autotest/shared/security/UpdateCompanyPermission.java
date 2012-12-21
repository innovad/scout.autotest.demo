package org.eclipse.scout.autotest.shared.security;

import java.security.BasicPermission;

public class UpdateCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateCompanyPermission() {
    super("UpdateCountry");
  }
}
