package org.eclipse.scout.autotest.client.ui.forms;

import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm.MainBox.CancelButton;
import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm.MainBox.CompanyButton;
import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm.MainBox.CompanySmartField;
import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm.MainBox.OkButton;
import org.eclipse.scout.autotest.shared.services.lookup.CompanyLookupCall;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class BlockingButtonForm extends AbstractForm {

  public BlockingButtonForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Countries");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public CompanyButton getCompanyButton() {
    return getFieldByClass(CompanyButton.class);
  }

  public CompanySmartField getCompanySmartField() {
    return getFieldByClass(CompanySmartField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class CompanySmartField extends AbstractSmartField<Long> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Company");
      }

      @Override
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return CompanyLookupCall.class;
      }

    }

    @Order(20.0)
    public class CompanyButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Company");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        CompanyForm form = new CompanyForm();
        form.startNew();
        form.waitFor();
      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }
}
