/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(TychoSurefireClientTestRunner.class)
public class CompanyFormTest {

  @Test
  public void testForm() throws Exception {
    CompanyForm form = new CompanyForm();
    form.startModify();

    form.doClose();
  }

}
