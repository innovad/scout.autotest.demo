/**
 *
 */
package org.eclipse.scout.testing.client.lookup;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.junit.Test;

/**
 * @author Adrian Moser
 */
public abstract class AbstractLookupCallTest<T extends LookupCall> {

  protected abstract T getLookupCall() throws ProcessingException;

  @Test
  public void testLookupAll() throws Exception {
    T call = getLookupCall();
    call.getDataByAll();
  }

  @Test
  public void testLookupByKey() throws Exception {
    T call = getLookupCall();
    call.getDataByKey();
  }

  @Test
  public void testLookupByText() throws Exception {
    T call = getLookupCall();
    call.getDataByText();
  }

}
