/**
 *
 */
package org.eclipse.autotest.client.ui.forms;

import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.autotest.shared.services.process.CompanyFormData;
import org.eclipse.scout.autotest.shared.services.process.ICompanyProcessService;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.testing.client.form.AbstractCRUDFormTest;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.runner.RunWith;

/**
 * @author Adrian Moser
 */
@RunWith(ScoutClientTestRunner.class)
public class CompanyFormTest extends AbstractCRUDFormTest<CompanyForm> {

  @Override
  protected CompanyForm getStartedForm() throws ProcessingException {
    CompanyForm form = new CompanyForm();
    form.startNew();
    return form;
  }

  @Override
  protected CompanyForm getModifyForm() throws ProcessingException {
    CompanyForm form = new CompanyForm();
    form.setCompanyNr(getForm().getCompanyNr());
    form.startModify();
    return form;
  }

  @Override
  public void cleanup() throws ProcessingException {
    CompanyFormData formData = new CompanyFormData();
    formData.setCompanyNr(getForm().getCompanyNr());
    SERVICES.getService(ICompanyProcessService.class).delete(formData);
  }

}
