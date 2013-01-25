/**
 *
 */
package org.eclipse.autotest.client;

import org.eclipse.autotest.client.test.TychoSurfireClientTestRunner;
import org.eclipse.scout.autotest.client.ClientSession;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner.ClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author amo
 */
@RunWith(TychoSurfireClientTestRunner.class)
@ClientTest(clientSessionClass = ClientSession.class)
public class DummyTest {

  @Test
  public void testName() throws Exception {

  }

}
