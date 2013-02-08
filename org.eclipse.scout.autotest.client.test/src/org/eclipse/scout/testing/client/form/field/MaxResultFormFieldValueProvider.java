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

import java.util.Calendar;
import java.util.Date;

import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.rt.client.ui.basic.table.columnfilter.ColumnFilterForm.MainBox.DateDetailBox.DateSequenceBox;
import org.eclipse.scout.rt.client.ui.basic.table.columnfilter.ColumnFilterForm.MainBox.NumberDetailBox.NumberSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.IDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.IIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.ILongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;

/**
 * Inserts a given value into a given ValueField. The goal of this ValueProvider is to fill a <code>SearchForm</code> in
 * a way, that lunching a search query would result in the biggest possible result set.
 * <p>
 * If the value is null, following values are inserted:
 * <p>
 * <ul>
 * <li>Double-,Integer- or LongField: if parent is a {@link AbstractFromToSequenceBox}, the biggest or smallest value is
 * inserted (depending whether it is the from or to field), else zero</li>
 * <li>StringField: '*' is inserted</li>
 * <li>DateField: if parent is a {@link AbstractFromToSequenceBox}, today minus one year or plus one year is inserted,
 * else today</li>
 * </ul>
 * <p>
 * 
 * @author Dominic Plangger
 */
public class MaxResultFormFieldValueProvider extends MaxFormFieldValueProvider {

  @Override
  protected String getDefaultStringValue(IStringField field) {
    return "*";
  }

  @Override
  protected Long getDefaultLongValue(ILongField field) {
    if (field.getParentField() instanceof NumberSequenceBox) {
      if (CompareUtility.equals(field, field.getParentField().getFields()[0])) {
        if (field.getMinValue() != null) {
          return field.getMinValue();
        }
        return Long.MIN_VALUE / 100000L;
      }
      return super.getDefaultLongValue(field);
    }
    return 0L;
  }

  @Override
  protected Integer getDefaultIntegerValue(IIntegerField field) {
    if (field.getParentField() instanceof NumberSequenceBox) {
      if (CompareUtility.equals(field, field.getParentField().getFields()[0])) {
        if (field.getMinValue() != null) {
          return field.getMinValue();
        }
        return Integer.MIN_VALUE / 100;
      }
      return super.getDefaultIntegerValue(field);
    }
    return 0;
  }

  @Override
  protected Double getDefaultDoubleValue(IDoubleField field) {
    if (field.getParentField() instanceof NumberSequenceBox) {
      if (CompareUtility.equals(field, field.getParentField().getFields()[0])) {
        if (field.getMinValue() != null) {
          return field.getMinValue();
        }
        return Double.MIN_VALUE;
      }
      return super.getDefaultDoubleValue(field);
    }
    return 0D;
  }

  @Override
  protected Date getDefaultDate(IDateField field) {
    if (field.getParentField() instanceof DateSequenceBox) {
      if (CompareUtility.equals(field, field.getParentField().getFields()[0])) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
      }
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, 1);
      return cal.getTime();
    }
    return new Date();
  }

}
