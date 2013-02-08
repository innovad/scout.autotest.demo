/**
 *
 */
package org.eclipse.scout.testing.client.form;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.junit.Test;

/**
 * @author Adrian Moser
 */
public abstract class AbstractFormTest<T extends IForm> {

  /**
   * @return the form to be tested (a new instance), should already be started with <code>startInternal()</code>.
   *         Typically a method <code>startNew()</code> should be called.
   */
  protected abstract T getStartedForm() throws ProcessingException;

  @Test
  public void testFieldGetters() throws Exception {
    FormTestUtility.testFieldGetters(getStartedForm());
  }

}
