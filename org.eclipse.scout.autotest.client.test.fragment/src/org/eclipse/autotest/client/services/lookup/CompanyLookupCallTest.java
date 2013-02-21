/**
 *
 */
package org.eclipse.autotest.client.services.lookup;

import org.eclipse.scout.autotest.shared.services.lookup.CompanyLookupCall;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.lookup.AbstractLookupCallTest;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.runner.RunWith;

/**
 * @author Adrian Moser
 */
@RunWith(ScoutClientTestRunner.class)
public class CompanyLookupCallTest extends AbstractLookupCallTest<CompanyLookupCall> {

  @Override
  protected CompanyLookupCall getLookupCall() throws ProcessingException {
    return new CompanyLookupCall();
  }

}
