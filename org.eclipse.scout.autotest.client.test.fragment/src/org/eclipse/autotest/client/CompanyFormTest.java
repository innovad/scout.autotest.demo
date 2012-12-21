/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(ScoutClientTestRunner.class)
public class CompanyFormTest {

  @Test
  public void testForm() throws Exception {
    CompanyForm form = new CompanyForm();
    form.startModify();

    form.doClose();
  }

}
