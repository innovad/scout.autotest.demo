package org.eclipse.scout.autotest.shared.services.process;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

public interface ICompanyProcessService extends IService {

  CountryFormData prepareCreate(CountryFormData formData) throws ProcessingException;

  CountryFormData create(CountryFormData formData) throws ProcessingException;

  CountryFormData load(CountryFormData formData) throws ProcessingException;

  CountryFormData store(CountryFormData formData) throws ProcessingException;
}
