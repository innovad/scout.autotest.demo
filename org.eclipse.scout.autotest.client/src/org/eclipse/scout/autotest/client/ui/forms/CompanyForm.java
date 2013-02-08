package org.eclipse.scout.autotest.client.ui.forms;

import org.eclipse.scout.autotest.client.ui.forms.CompanyForm.MainBox.CancelButton;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm.MainBox.NameField;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm.MainBox.OkButton;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm.MainBox.ShortNameField;
import org.eclipse.scout.autotest.shared.security.UpdateCompanyPermission;
import org.eclipse.scout.autotest.shared.services.process.CompanyFormData;
import org.eclipse.scout.autotest.shared.services.process.ICompanyProcessService;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

@FormData(value = CompanyFormData.class, sdkCommand = SdkCommand.CREATE)
public class CompanyForm extends AbstractForm {

  private Long companyNr;

  public CompanyForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  @FormData
  public Long getCompanyNr() {
    return companyNr;
  }

  @FormData
  public void setCompanyNr(Long countryNr) {
    this.companyNr = countryNr;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public ShortNameField getShortNameField() {
    return getFieldByClass(ShortNameField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ShortNameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShortName");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 60;
      }

    }

    @Order(20.0)
    public class NameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Country");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 120;
      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateCompanyPermission());
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.create(formData);
      importFormData(formData);
    }
  }
}
