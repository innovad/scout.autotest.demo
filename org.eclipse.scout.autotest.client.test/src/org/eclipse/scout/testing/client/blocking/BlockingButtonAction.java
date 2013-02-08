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
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;

/**
 * A blocking action implementation for testing buttons which open forms
 * 
 * @author Adrian Moser
 */
public class BlockingButtonAction implements IBlockingTestAction {

  private IButton m_button;

  /**
   * Test a blocking button
   * 
   * @param button
   *          the button which is performing a blocking condition
   */
  public BlockingButtonAction(IButton button) {
    super();
    this.m_button = button;
  }

  @Override
  public void run() throws ProcessingException {
    m_button.doClick();
  }

}
