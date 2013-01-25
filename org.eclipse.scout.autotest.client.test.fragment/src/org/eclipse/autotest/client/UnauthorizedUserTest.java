/**
 * 
 */
package org.eclipse.autotest.client;

import org.eclipse.autotest.client.test.TychoSurfireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.autotest.client.ui.forms.CompanyForm;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 *         Since the user "unauthorized" is not registered with the BasicSecurityFilter, we expect a ProcessingException
 *         here (401 UNAUTHORIZED).
 */
@RunWith(TychoSurfireClientTestRunner.class)
@ClientTest(clientSessionClass = ClientSession.class, runAs = "unauthorized")
public class UnauthorizedUserTest {

  @Test(expected = ProcessingException.class)
  public void testUser() throws Exception {
    CompanyForm form = new CompanyForm();
    form.startNew();
  }

}
