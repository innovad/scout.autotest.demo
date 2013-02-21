/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.testing.client.blocking.TestingRunnable;
import org.eclipse.scout.testing.client.form.FormTestUtility;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adrian Moser
 */
@RunWith(ScoutClientTestRunner.class)
public class BlockingButtonTest {

  /**
   * This test demonstrates a case where a {@link VetoException} is expected inside a blocking condition
   */
  @Test(expected = VetoException.class)
  public void testBlockingButtonVetoException() throws Exception {
    BlockingButtonForm button = new BlockingButtonForm();
    button.startNew();

    TestingRunnable runnableAfterButtonExecution = new TestingRunnable() {
      @Override
      protected void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        throw new VetoException("Force Exception");
      }
    };

    FormTestUtility.runBlockingButton(button.getCompanyButton(), runnableAfterButtonExecution);
  }

  /**
   * This test demonstrates a case where a {@link ProcessingException} is expected inside a blocking condition
   */
  @Test(expected = ProcessingException.class)
  public void testBlockingButtonProcessingException() throws Exception {
    BlockingButtonForm button = new BlockingButtonForm();
    button.startNew();

    TestingRunnable runnableAfterButtonExecution = new TestingRunnable() {
      @Override
      protected void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        throw new ProcessingException("Force Exception");
      }
    };

    FormTestUtility.runBlockingButton(button.getCompanyButton(), runnableAfterButtonExecution);
  }

  /**
   * This test demonstrates a case where an Assert should fail inside a blocking condition (the AssertionError is here
   * expected because we do not want this demonstration test to fail)
   */
  @Test(expected = AssertionError.class)
  public void testBlockingButtonAssertionError() throws Exception {
    BlockingButtonForm button = new BlockingButtonForm();
    button.startNew();

    TestingRunnable runnableAfterButtonExecution = new TestingRunnable() {
      @Override
      protected void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        Assert.fail("Forced Failure");
      }
    };

    FormTestUtility.runBlockingButton(button.getCompanyButton(), runnableAfterButtonExecution);
  }

  /**
   * A blocking condition without failure or assertion error
   */
  @Test
  public void testBlockingButtonOk() throws Exception {
    BlockingButtonForm button = new BlockingButtonForm();
    button.startNew();

    TestingRunnable runnableAfterButtonExecution = new TestingRunnable() {
      @Override
      protected void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        Assert.assertTrue("Form is open", form.isFormOpen());
        Assert.assertNull("CompanyNr is empty", form.getCompanyNr());
        form.doClose();
      }
    };

    FormTestUtility.runBlockingButton(button.getCompanyButton(), runnableAfterButtonExecution);
  }

}
