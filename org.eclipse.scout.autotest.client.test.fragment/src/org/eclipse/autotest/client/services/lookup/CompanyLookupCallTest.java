/**
 *
 */
package org.eclipse.autotest.client.services.lookup;

import org.eclipse.autotest.client.test.TychoSurefireClientTestRunner;
import org.eclipse.scout.autotest.shared.services.lookup.CompanyLookupCall;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.lookup.AbstractLookupCallTest;
import org.junit.runner.RunWith;

/**
 * @author Adrian Moser
 */
@RunWith(TychoSurefireClientTestRunner.class)
public class CompanyLookupCallTest extends AbstractLookupCallTest<CompanyLookupCall> {

  @Override
  protected CompanyLookupCall getLookupCall() throws ProcessingException {
    return new CompanyLookupCall();
  }

}
