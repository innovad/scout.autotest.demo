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

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.shared.services.common.exceptionhandler.IExceptionHandlerService;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.testing.client.form.FormTestUtility;
import org.junit.Assert;

/**
 * Runnable used for testing blocking conditions.
 */
public abstract class TestingRunnable implements Runnable {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(TestingRunnable.class);

  protected abstract void runTest() throws ProcessingException;

  @Override
  public final void run() {
    try {
      runTest();
    }
    catch (ProcessingException e) {
      SERVICES.getService(IExceptionHandlerService.class).handleException(e);
    }
    finally {
      try {
        FormTestUtility.closeAllBlockingForms();
      }
      catch (ProcessingException e1) {
        LOG.error("Could not close forms", e1);
        Assert.fail("Could not close forms, " + e1.getMessage());
      }
    }
  }

}
