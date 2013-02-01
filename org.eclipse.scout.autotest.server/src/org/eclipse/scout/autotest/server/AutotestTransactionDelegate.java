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
package org.eclipse.scout.autotest.server;

import org.eclipse.scout.rt.server.DefaultTransactionDelegate;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * 
 */
public class AutotestTransactionDelegate extends DefaultTransactionDelegate {

  /**
   * @param loaderBundles
   * @param requestMinVersion
   * @param debug
   */
  public AutotestTransactionDelegate(Bundle[] loaderBundles, Version requestMinVersion, boolean debug) {
    super(loaderBundles, requestMinVersion, debug);
  }

  @Override
  protected Throwable replaceOutboundException(Throwable t) {
    // disable exception filtering
    if (t != null) {
      t.printStackTrace();
    }
    return t;
  }

}
