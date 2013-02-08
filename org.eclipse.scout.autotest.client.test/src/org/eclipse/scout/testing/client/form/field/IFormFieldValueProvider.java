/*******************************************************************************
 * Copyright (c) 2013 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.testing.client.form.field;

import org.eclipse.scout.rt.client.ui.form.fields.IValueField;

/**
 * Defines an interface for filling most of the possible IValueFields with a certain value. The implementation must take
 * care of the special case when the value argument is null (e.g. insert default value)
 * 
 * @author Dominic Plangger
 */
public interface IFormFieldValueProvider {

  void fillValueField(IValueField<?> field, Object defaultValue);
}
