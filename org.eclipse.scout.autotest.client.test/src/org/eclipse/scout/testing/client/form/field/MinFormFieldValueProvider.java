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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.IBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.bigintegerfield.IBigIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.IBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.IDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.IIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.IListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.ILongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.IRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.ISmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.ITreeBox;

/**
 * Inserts a given value into a given ValueField. If the value is null: inserts the smallest possible value of the given
 * ValueField. If the field does not have a smallest value, a default value is inserted.
 * <p>
 * Special cases (if no map entry is available):
 * <ul>
 * <li>Treeboxes: unchecks every node (independent of active/inactive)</li>
 * <li>Listboxes: unchecks every entry (independent of active/inactive)</li>
 * <li>Smartfield: takes the value of the first row from the corresponding lookup call (if row value is invalid, try up
 * to 9 others)</li>
 * <li>Datefield: takes the current date</li>
 * </ul>
 * <p>
 * 
 * @author Dominic Plangger
 */
public class MinFormFieldValueProvider extends AbstractFormFieldValueProvider {

  @Override
  protected boolean getDefaultBoolean(IBooleanField field) {
    return false;
  }

  @Override
  protected BigDecimal getDefaultBigDecimalValue(IBigDecimalField field) {
    if (field.getMinValue() != null) {
      return field.getMinValue();
    }
    return BigDecimal.TEN;
  }

  @Override
  protected Double getDefaultDoubleValue(IDoubleField field) {
    if (field.getMinValue() != null) {
      return field.getMinValue();
    }
    return -(Double.MAX_VALUE - 1) / 100000d;
  }

  @Override
  protected Integer getDefaultIntegerValue(IIntegerField field) {
    if (field.getMinValue() != null) {
      return field.getMinValue();
    }
    return Integer.MIN_VALUE / 100000;
  }

  @Override
  protected Long getDefaultLongValue(ILongField field) {
    if (field.getMinValue() != null) {
      return field.getMinValue();
    }
    return Long.MIN_VALUE / 100000;
  }

  @Override
  protected BigInteger getDefaultBigIntegerValue(IBigIntegerField field) {
    if (field.getMinValue() != null) {
      return field.getMinValue();
    }
    return BigInteger.TEN;
  }

  @Override
  protected String getDefaultStringValue(IStringField field) {
    int len = 0;
    if (field.isMandatory()) {
      len = 1;
    }
    if (len == 0) {
      return null;
    }
    return StringUtility.rpad("", "abcdefghijklmnopqrstuvwxyz0123456789", len);
  }

  @Override
  protected Object getDefaultRadioButtonValue(IRadioButtonGroup<?> field) {
    if (field.isMandatory()) {
      return super.getDefaultRadioButtonValue(field);
    }
    return null;
  }

  @Override
  protected Object getDefaultSmartFieldValue(ISmartField<?> field) {
    if (field.isMandatory()) {
      return super.getDefaultSmartFieldValue(field);
    }
    return null;
  }

  @Override
  protected Object[] getDefaultListBoxValue(IListBox<?> field) {
    if (field.isMandatory()) {
      Object[] maxResult = super.getDefaultListBoxValue(field);
      return extractMinResult(field.getHolderType(), maxResult);
    }
    return null;
  }

  @Override
  protected Object[] getDefaultTreeBoxValue(ITreeBox<?> field) {
    if (field.isMandatory()) {
      Object[] maxResult = super.getDefaultTreeBoxValue(field);
      return extractMinResult(field.getHolderType(), maxResult);
    }
    return null;
  }

  @Override
  protected Date getDefaultDate(IDateField field) {
    if (field.isMandatory()) {
      return new Date();
    }
    return null;
  }

  private Object[] extractMinResult(Class<?> fieldHolderType, Object[] maxResult) {
    if (maxResult == null || maxResult.length <= 1) {
      return maxResult;
    }
    Object[] minResult = (Object[]) Array.newInstance(fieldHolderType.getComponentType(), 1);
    minResult[0] = maxResult[0];
    return minResult;
  }

}
