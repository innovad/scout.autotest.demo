/**
 *
 */
package org.eclipse.autotest.client.ui.desktop.outlines.pages;

import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.client.ui.desktop.outlines.pages.CompanyTablePage;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.page.AbstractTablePageWithSearchFormTest;
import org.junit.runner.RunWith;

@RunWith(TychoSurefireClientTestRunner.class)
public class CompanyTablePageTest extends AbstractTablePageWithSearchFormTest<CompanyTablePage> {

  @Override
  protected CompanyTablePage getTablePage() throws ProcessingException {
    return new CompanyTablePage();
  }

}
