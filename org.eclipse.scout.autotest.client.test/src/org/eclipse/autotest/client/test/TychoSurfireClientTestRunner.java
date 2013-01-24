/**
 *
 */
package org.eclipse.autotest.client.test;

import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author amo
 */
public class TychoSurfireClientTestRunner extends ScoutClientTestRunner {

  /**
   * @param klass
   * @throws InitializationError
   */
  public TychoSurfireClientTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
  }

}
