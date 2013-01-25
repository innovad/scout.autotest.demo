/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.autotest.client.test.TychoSurfireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.rt.testing.shared.DevTestMarker;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(TychoSurfireClientTestRunner.class)
@ClientTest(clientSessionClass = ClientSession.class)
@DevTestMarker
public class CompanyFormTest {

  @Test
  public void testForm() throws Exception {
    CompanyForm form = new CompanyForm();
    form.startModify();

    form.doClose();
  }

}
