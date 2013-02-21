/**
 *
 */
package org.eclipse.autotest.client.ui.forms;

import org.eclipse.scout.autotest.client.ui.forms.BlockingButtonForm;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.testing.client.form.AbstractFormTest;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.runner.RunWith;

/**
 * @author Adrian Moser
 */
@RunWith(ScoutClientTestRunner.class)
public class BlockingButtonFormTest extends AbstractFormTest<BlockingButtonForm> {

  @Override
  protected BlockingButtonForm getStartedForm() throws ProcessingException {
    BlockingButtonForm blockingButtonForm = new BlockingButtonForm();
    blockingButtonForm.startNew();
    return blockingButtonForm;
  }

}
