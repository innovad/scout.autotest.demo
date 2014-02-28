package org.eclipse.scout.autotest.shared.security;

import java.security.BasicPermission;

public class CreateCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateCompanyPermission() {
    super("CreateCountry");
  }
}
