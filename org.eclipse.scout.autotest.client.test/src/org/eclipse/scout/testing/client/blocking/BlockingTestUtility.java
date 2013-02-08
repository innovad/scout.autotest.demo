/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.testing.client.blocking;

import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.Activator;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.JobChangeAdapterEx;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.ServiceRegistration;

/**
 * Utility for testing blocking code
 * 
 * @author Adrian Moser
 */
public final class BlockingTestUtility {

  private BlockingTestUtility() {
  }

  /**
   * Helper method to test blocking code
   * 
   * @param action
   * @param runnableAfterBlockingAction
   *          code to be executed after blocking state has been reached
   * @throws ProcessingException
   */
  public static void runBlockingAction(IBlockingTestAction action, TestingRunnable runnableAfterBlockingAction) throws ProcessingException {
    TestingExceptionHandlerService exceptionHandler = new TestingExceptionHandlerService();
    @SuppressWarnings("rawtypes")
    List<ServiceRegistration> regs = TestingUtility.registerServices(Activator.getDefault().getBundle(), 1000, exceptionHandler);

    WaitForListener waitForListener = new WaitForListener(runnableAfterBlockingAction);
    ClientJob currentJob = (ClientJob) ClientJob.getJobManager().currentJob();
    currentJob.addJobChangeListenerEx(waitForListener);
    try {
      // run action which blocks with waitFor()
      action.run();
      exceptionHandler.assertNoException();
      exceptionHandler.clear();
    }
    finally {
      currentJob.removeJobChangeListenerEx(waitForListener);
      TestingUtility.unregisterServices(regs);
    }
  }

  private static class WaitForListener extends JobChangeAdapterEx {
    private Runnable runnable;

    public WaitForListener(Runnable runnable) {
      this.runnable = runnable;
    }

    @Override
    public void blockingConditionStart(IJobChangeEvent event) {
      try {
        runnable.run();
      }
      catch (Throwable t) {
        //Exception would be catched by the listener so it is necessary to use the exception service in order to make the test fail.
        SERVICES.getService(TestingExceptionHandlerService.class).handleException(new ProcessingException("", t));
      }
    }
  }

}
