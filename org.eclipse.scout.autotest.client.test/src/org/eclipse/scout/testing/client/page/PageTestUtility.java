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
package org.eclipse.scout.testing.client.page;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.testing.client.blocking.BlockingMenuAction;
import org.eclipse.scout.testing.client.blocking.BlockingTestUtility;
import org.eclipse.scout.testing.client.blocking.TestingRunnable;

/**
 * Utility for testing instances of {@link IPage}
 * 
 * @author Adrian Moser
 */
public final class PageTestUtility {

  private PageTestUtility() {
  }

  /**
   * Test a blocking menu on a {@link IPageWithTable}
   * 
   * @param page
   *          the table page, make sure the rows are loaded and selected if required
   * @param menuType
   *          the menu class
   * @param runnableAfterMenuExecution
   *          testing code to be executed after the menu enters a blocking condition
   * @throws ProcessingException
   */
  public static void runBlockingMenu(IPageWithTable<?> page, Class<? extends IMenu> menuType, TestingRunnable runnableAfterMenuExecution) throws ProcessingException {
    BlockingMenuAction action = new BlockingMenuAction(page, menuType);
    BlockingTestUtility.runBlockingAction(action, runnableAfterMenuExecution);
  }

}
