/**
 *
 */
package org.eclipse.autotest.client.ui.desktop.outlines.pages;

import org.eclipse.scout.autotest.client.ui.desktop.outlines.pages.CompanyTablePage;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.page.AbstractTablePageWithSearchFormTest;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.runner.RunWith;

@RunWith(ScoutClientTestRunner.class)
public class CompanyTablePageTest extends AbstractTablePageWithSearchFormTest<CompanyTablePage> {

  @Override
  protected CompanyTablePage getTablePage() throws ProcessingException {
    return new CompanyTablePage();
  }

}
