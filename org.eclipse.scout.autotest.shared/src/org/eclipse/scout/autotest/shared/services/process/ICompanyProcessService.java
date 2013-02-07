package org.eclipse.scout.autotest.shared.services.process;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

public interface ICompanyProcessService extends IService {

  CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException;

  CompanyFormData create(CompanyFormData formData) throws ProcessingException;

  CompanyFormData load(CompanyFormData formData) throws ProcessingException;

  CompanyFormData store(CompanyFormData formData) throws ProcessingException;

  void delete(CompanyFormData formData) throws ProcessingException;
}
