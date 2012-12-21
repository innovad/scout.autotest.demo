package org.eclipse.scout.autotest.server.services.process;

import org.eclipse.scout.autotest.shared.security.CreateCompanyPermission;
import org.eclipse.scout.autotest.shared.security.ReadCompanyPermission;
import org.eclipse.scout.autotest.shared.security.UpdateCompanyPermission;
import org.eclipse.scout.autotest.shared.services.process.CountryFormData;
import org.eclipse.scout.autotest.shared.services.process.ICompanyProcessService;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

public class CompanyProcessService extends AbstractService implements ICompanyProcessService {

  @Override
  public CountryFormData prepareCreate(CountryFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    return formData;
  }

  @Override
  public CountryFormData create(CountryFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT MAX(COMPANY_NR)+1 " +
        "FROM   COMPANY " +
        "INTO   :companyNr"
        , formData);
    SQL.insert("" +
        "INSERT INTO COMPANY (COMPANY_NR, SHORT_NAME, NAME) " +
        "VALUES (:companyNr, :shortName, :name)"
        , formData);
    return formData;
  }

  @Override
  public CountryFormData load(CountryFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.selectInto("" +
        "SELECT SHORT_NAME, " +
        "       NAME " +
        "FROM   COMPANY " +
        "WHERE  COMPANY_NR = :companyNr " +
        "INTO   :shortName," +
        "       :name"
        , formData);
    return formData;
  }

  @Override
  public CountryFormData store(CountryFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    SQL.update(
        "UPDATE COMPANY SET" +
            "       SHORT_NAME = :shortName, " +
            "       NAME = :name " +
            "WHERE  COMPANY_NR = :companyNr", formData);

    return formData;
  }
}
