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
import org.eclipse.scout.rt.client.BlockingCondition;
import org.eclipse.scout.rt.client.ui.form.IForm;

/**
 * Execute blocking testing tasks (that are calling {@link BlockingCondition#waitFor()} with the
 * {@link BlockingTestUtility#runBlockingAction(IBlockingTestAction, org.eclipse.scout.testing.client.TestingRunnable)}
 * 
 * @author Adrian Moser
 */
public interface IBlockingTestAction {

  /**
   * Implement your blocking action here. Example is testing opening a menu or clicking a button which is again calling
   * a form
   * with {@link IForm#waitFor()}
   * 
   * @throws ProcessingException
   */
  void run() throws ProcessingException;

}
