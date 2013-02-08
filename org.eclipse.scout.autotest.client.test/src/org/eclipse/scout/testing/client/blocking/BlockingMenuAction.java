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
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.junit.Assert;

/**
 * A blocking action implementation for testing menues which open forms
 * 
 * @author Adrian Moser
 */
public class BlockingMenuAction implements IBlockingTestAction {

  private IPageWithTable<?> m_page;
  private Class<? extends IMenu> m_menuType;

  /**
   * Test a blocking menu on a table page
   * 
   * @param page
   *          the page containing the menu. Make sure rows are selected (if required), otherwise the execution of the
   *          menu will fail
   * @param menuType
   *          the menu class to be executed
   */
  public BlockingMenuAction(IPageWithTable<?> page, Class<? extends IMenu> menuType) {
    super();
    m_page = page;
    m_menuType = menuType;
  }

  @Override
  public void run() throws ProcessingException {
    boolean run = m_page.getTable().runMenu(m_menuType);
    Assert.assertTrue(run);
  }

}
