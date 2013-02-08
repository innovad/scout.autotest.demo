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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.eclipse.scout.commons.DateUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.IBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.bigintegerfield.IBigIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.IBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.IDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.IIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.IListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.ILongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.IRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.ISmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.client.ui.form.fields.treebox.ITreeBox;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * Inserts a given value into a given ValueField. If the value is null: a random value is inserted.
 * <p>
 * Special cases (if no map entry is available):
 * <ul>
 * <li>Treeboxes: loops through every node and checks a node with a probability of 0.5 (independent of active/inactive)</li>
 * <li>Listboxes: same as Treeboxes</li>
 * <li>Smartfield: chooses a random value out of the top 20 lookups (if row value is invalid, try up to 19 others)</li>
 * <li>Datefield: takes the current date</li>
 * </ul>
 * <p>
 * 
 * @author Dominic Plangger
 */
public class RandFormFieldValueProvider extends AbstractFormFieldValueProvider {

  private Random gen = new Random(new Date().getTime());
  /**
   * to get the max for some fields we use
   */
  private MaxFormFieldValueProvider m_maxFFVPRovider = new MaxFormFieldValueProvider();

  @Override
  protected Double getDefaultDoubleValue(IDoubleField field) {
    return Double.valueOf(gen.nextDouble());
  }

  @Override
  protected Integer getDefaultIntegerValue(IIntegerField field) {
    return gen.nextInt(m_maxFFVPRovider.getDefaultIntegerValue(field));
  }

  @Override
  protected Long getDefaultLongValue(ILongField field) {
    long randomLong = gen.nextLong();
    if (randomLong == Long.MIN_VALUE) {
      randomLong = 0;
    }
    else {
      randomLong = Math.abs(randomLong);
    }
    return randomLong % m_maxFFVPRovider.getDefaultLongValue(field);
  }

  @Override
  protected BigInteger getDefaultBigIntegerValue(IBigIntegerField field) {
    return new BigInteger(m_maxFFVPRovider.getDefaultBigIntegerValue(field).bitCount(), gen).min(m_maxFFVPRovider.getDefaultBigIntegerValue(field));
  }

  @Override
  protected BigDecimal getDefaultBigDecimalValue(IBigDecimalField field) {
    return BigDecimal.valueOf(gen.nextDouble()).multiply(m_maxFFVPRovider.getDefaultBigDecimalValue(field));
  }

  @Override
  protected String getDefaultStringValue(IStringField field) {
    int maxLength = field.getMaxLength();
    // limit max length for performance reasons
    if (maxLength > 4000) {
      maxLength = 4000;
    }

    int minLength = 0;
    return StringUtility.rpad("TestString", "abcdefghijklmnopqrstuvwxyz0123456789", minLength + gen.nextInt(maxLength - minLength));
  }

  /*
   * choose random button. if it is not visible or enabled, take subsequent button until a valid button is found or the start button is reached again
   */
  @Override
  protected Object getDefaultRadioButtonValue(IRadioButtonGroup<?> field) {
    IButton[] buttons = field.getButtons();
    if (buttons != null) {
      int startIndex = gen.nextInt(buttons.length);
      int i = startIndex;
      do {
        IButton button = buttons[i];
        if (button.isVisible() && button.isEnabled()) {
          return button.getRadioValue();
        }
        i++;
        if (i >= buttons.length) {
          i = 0;
        }
      }
      while (i != startIndex);
    }
    return null;
  }

  @Override
  protected Object[] getDefaultListBoxValue(IListBox<?> field) {
    Long[] allValidKeys = (Long[]) super.getDefaultListBoxValue(field);
    assertTrue("No valid listbox entry for field [" + field.getClass().getName() + "] found", allValidKeys != null && allValidKeys.length > 0);
    return filterRows(allValidKeys, field.isMandatory());
  }

  @Override
  protected Object[] getDefaultTreeBoxValue(ITreeBox<?> field) {
    Long[] allValidKeys = (Long[]) super.getDefaultTreeBoxValue(field);
    assertTrue("No valid treebox entry for field [" + field.getClass().getName() + "] found", allValidKeys != null && allValidKeys.length > 0);
    return filterRows(allValidKeys, field.isMandatory());
  }

  private Long[] filterRows(Long[] allValidKeys, boolean isMandatory) {
    List<Long> filteredKeys = new ArrayList<Long>();
    for (Long key : allValidKeys) {
      if (gen.nextBoolean()) {
        filteredKeys.add(key);
      }
    }
    if (filteredKeys.isEmpty() && isMandatory) {
      filteredKeys.add(allValidKeys[0]);
    }
    return filteredKeys.toArray(new Long[filteredKeys.size()]);
  }

  @Override
  protected Object getDefaultSmartFieldValue(ISmartField<?> field) {
    try {
      LookupRow[] lookupRows = field.callBrowseLookup(null, 20);
      if (lookupRows != null && lookupRows.length > 0) {
        int startIndex = gen.nextInt(lookupRows.length);
        int i = startIndex;
        do {
          LookupRow row = lookupRows[i];
          if (row.isActive() && row.isEnabled()) {
            return row.getKey();
          }
          i++;
          if (i >= lookupRows.length) {
            i = 0;
          }
        }
        while (i != startIndex);
      }
    }
    catch (ProcessingException e) {
      throw new RuntimeException("Unexpected exception while filling smartfield [" + field.getClass().getName() + "]", e);
    }
    return null;
  }

  @Override
  protected boolean getDefaultBoolean(IBooleanField field) {
    return gen.nextBoolean();
  }

  @Override
  protected Date getDefaultDate(IDateField field) {
    return DateUtility.addDays(new Date(), gen.nextDouble() * gen.nextInt(3000));
  }
}
