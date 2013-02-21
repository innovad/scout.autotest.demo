/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.scout.autotest.client.ui.desktop.outlines.pages.CompanyTablePage;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.testing.client.blocking.TestingRunnable;
import org.eclipse.scout.testing.client.form.FormTestUtility;
import org.eclipse.scout.testing.client.page.PageTestUtility;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Am example how to test a menu which opens a blocking form
 * 
 * @author Adrian Moser
 */
@RunWith(ScoutClientTestRunner.class)
public class BlockingMenuTest {

  /**
   * This test demonstrates a case where a {@link VetoException} is expected inside a blocking condition
   */
  @Test(expected = VetoException.class)
  public void testNewMenuException() throws Exception {
    CompanyTablePage page = new CompanyTablePage();
    page.nodeAddedNotify();
    page.loadChildren();

    TestingRunnable runnableAfterMenuExecution = new TestingRunnable() {
      @Override
      public void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        throw new VetoException("Force Exception");
      }
    };

    PageTestUtility.runBlockingMenu(page, CompanyTablePage.Table.NewMenu.class, runnableAfterMenuExecution);
  }

  /**
   * This test demonstrates a case where an Assert should fail inside a blocking condition (the AssertionError is here
   * expected because we do not want this demonstration test to fail)
   */
  @Test(expected = AssertionError.class)
  public void testNewMenuAssertionError() throws Exception {
    CompanyTablePage page = new CompanyTablePage();
    page.nodeAddedNotify();
    page.loadChildren();

    TestingRunnable runnableAfterMenuExecution = new TestingRunnable() {
      @Override
      public void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        Assert.fail("Forced Failure");
      }
    };

    PageTestUtility.runBlockingMenu(page, CompanyTablePage.Table.NewMenu.class, runnableAfterMenuExecution);
  }

  /**
   * A blocking condition without failure or assertion error
   */
  @Test
  public void testNewMenuOk() throws Exception {
    CompanyTablePage page = new CompanyTablePage();
    page.nodeAddedNotify();
    page.loadChildren();

    TestingRunnable runnableAfterMenuExecution = new TestingRunnable() {
      @Override
      public void runTest() throws ProcessingException {
        CompanyForm form = FormTestUtility.findLastAddedForm(CompanyForm.class);
        Assert.assertNotNull("Form is not null", form);
        Assert.assertTrue("Form is open", form.isFormOpen());
        Assert.assertNull("CompanyNr is empty", form.getCompanyNr());
        form.doClose();
      }
    };

    PageTestUtility.runBlockingMenu(page, CompanyTablePage.Table.NewMenu.class, runnableAfterMenuExecution);
  }

}
