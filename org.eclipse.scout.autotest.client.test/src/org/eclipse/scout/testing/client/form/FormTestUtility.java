/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.testing.client.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.DateUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.IBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.bigintegerfield.IBigIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.IDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.IIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBoxFilterBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.ILongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.ISmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.testing.client.blocking.BlockingButtonAction;
import org.eclipse.scout.testing.client.blocking.BlockingTestUtility;
import org.eclipse.scout.testing.client.blocking.TestingRunnable;
import org.eclipse.scout.testing.client.form.field.IFormFieldValueProvider;
import org.eclipse.scout.testing.client.form.field.MaxFormFieldValueProvider;
import org.junit.Assert;

/**
 * Utility class for testing {@link IForm}.
 * 
 * @author Andreas Ottiger
 * @author Adrian Moser
 */
public final class FormTestUtility {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(FormTestUtility.class);

  private FormTestUtility() {
  }

  /**
   * Goes through every value of <code> fieldValues </code> and checks whether the corresponding field in
   * <code> form </code> has the same value
   * 
   * @param expectedFieldValues
   *          the values expected, map of field ids with assigned values, created by {@link fillFormFields}
   * @param form
   *          the form to be checked
   */
  public static void assertValueFields(Map<String, Object> expectedFieldValues, IForm form) {
    if (form == null || expectedFieldValues == null) {
      fail("form or fieldValues argument is null");
      return;
    }
    for (Map.Entry<String, Object> fieldValue : expectedFieldValues.entrySet()) {
      IFormField formField = form.getFieldById(fieldValue.getKey());
      if (!(formField instanceof IValueField<?>)) {
        continue;
      }
      IValueField<?> field = (IValueField<?>) formField;
      Object actualValue = field.getValue();
      if (field.getHolderType().isArray() && actualValue != null && fieldValue.getValue() != null) {
        Set<Object> actual = CollectionUtility.hashSet((Object[]) actualValue);
        Set<Object> expected = CollectionUtility.hashSet((Object[]) fieldValue.getValue());
        assertEquals("Unexpected values in field '" + field.getLabel() + "' [" + field.getClass().getName() + "]", expected, actual);
      }
      //DB may allow less fraction digits then a Double has, but still enough to return a number which gets displayed correctly
      else if (field instanceof IDoubleField && actualValue != null && fieldValue.getValue() != null) {
        int p = ((IDoubleField) field).getMaxFractionDigits();
        assertEquals("Unexpected value in field '" + field.getLabel() + "' [" + field.getClass().getName() + "]"
            , (Double) fieldValue.getValue(), (Double) actualValue, Math.pow(0.1, p + 1));
      }
      else {
        assertEquals("Unexpected value in field '" + field.getLabel() + "' [" + field.getClass().getName() + "]", fieldValue.getValue(), actualValue);
      }
    }
  }

  /**
   * Inserts a value in every ValueField of <code> form </code>. If a ValueField contains an entry
   * in the map <code> fixValues </code>, then the value from the map is inserted into the field. If there is no entry
   * in the map, the corresponding fill method from the <code> fieldFiller </code> is called. On Default,
   * <code>fieldFiller</code> is an instance of {@link MaxFormFieldValueProvider}
   * <p>
   * Exception: if the parent of the ValueField is a TableField, the ValueField is ignored
   * <p>
   * 
   * @param form
   *          the form to be filled
   * @param valueProvider
   *          a value provider defines the values to be used
   * @param orderedFieldPairs
   *          make sure all fields that should be are correctly ordered, if if they have been assigned randomly
   * @param fixedValues
   *          The values from the <code> fixValues </code> argument are inserted at the beginning.
   *          A {@link LinkedHashMap} as map argument as the order of the entries matters (master fields must precede
   *          the slave fields). For convenience use {@link fillFormFields}, it creates
   *          automatically a {@link LinkedHashMap} out of the FieldValues
   *          <p>
   *          fails if not all values from the <code>fixValues</code> list could be set.
   *          </p>
   * @return map of field ids with assigned values, to be used with {@link #assertValueFields(Map, IForm)}
   * @throws ProcessingException
   */
  public static Map<String, Object> fillFormFields(IForm form, IFormFieldValueProvider valueProvider, List<OrderedFieldPair> orderedFieldPairs, LinkedHashMap<Class<? extends IValueField<?>>, Object> fixedValues) throws ProcessingException {
    if (form == null) {
      return null;
    }

    assertNotNull("value provider must be set", valueProvider);

    if (fixedValues == null) {
      fixedValues = new LinkedHashMap<Class<? extends IValueField<?>>, Object>();
    }

    // Step 1: insert all values from the map of fixed values
    for (Entry<Class<? extends IValueField<?>>, Object> entry : fixedValues.entrySet()) {
      Object value = entry.getValue();
      IValueField<?> formField = form.getFieldByClass(entry.getKey());
      valueProvider.fillValueField(formField, value);
    }

    // step 2: sort fields, first non-slave fields, then slave fields in transitive order
    // e.g. f1 has no master and is master of f2, f2 is master of f3 and f3 is master of f4
    // then the master list is: {f1}, and slave list: {f2->f3->f4}
    List<IValueField<?>> masterList = new LinkedList<IValueField<?>>();
    List<IValueField<?>> slaveList = new LinkedList<IValueField<?>>();
    for (IFormField formField : form.getAllFields()) {
      if (formField.getParentField() instanceof AbstractTableField
          || !(formField instanceof IValueField)
          || fixedValues.containsKey(formField.getClass())) {
        continue;
      }
      IValueField<?> valueField = (IValueField<?>) formField;
      if (valueField.getMasterField() != null) {
        int index = slaveList.indexOf(valueField.getMasterField());
        if (index > 0) {
          slaveList.add(index + 1, valueField);
        }
        else {
          slaveList.add(0, valueField);
        }
      }
      else {
        masterList.add(valueField);
      }
    }
    // Step 3: insert default values into the master fields
    for (IValueField<?> valueField : masterList) {
      valueProvider.fillValueField(valueField, null);
    }

    // Step 4: insert default values into the slave fields
    for (IValueField<?> valueField : slaveList) {
      valueProvider.fillValueField(valueField, null);
    }

    // Step 5: make sure all fields that should be are correctly ordered, if if they have been
    //         assigned randomly
    if (orderedFieldPairs != null) {
      for (OrderedFieldPair orderedFieldPair : orderedFieldPairs) {
        ensureAscending(form, orderedFieldPair);
      }
    }

    // Step 6: collect all values. Collecting is done at last because subsequent value changes
    //         might affect previously changed value fields
    Map<String, Object> setFieldValues = collectFieldValues(form);

    // Step 7: check whether all fixed values are really set as desired
    for (Entry<Class<? extends IValueField<?>>, Object> fixValue : fixedValues.entrySet()) {
      IFormField field = form.getFieldByClass(fixValue.getKey());
      if (!(field instanceof IValueField<?>)) {
        fail("Field [" + field.getClass() + "] could not be set as it is not a value field.");
      }
      if (!Long.valueOf(0).equals(fixValue.getValue()) && !Integer.valueOf(0).equals(fixValue.getValue())) {
        IValueField<?> valueField = (IValueField<?>) field;
        if (fixValue.getValue() != null && !fixValue.getValue().equals(valueField.getValue())) {
          // don't check for NULL / 0;
          assertEquals("Field has been set for field: " + field.getClass(), fixValue.getValue(), valueField.getValue());
        }

      }
    }

    return setFieldValues;
  }

  /**
   * Executes all getters on the form and verifies the correct field is returned.
   * 
   * @param form
   *          the form to be tested
   */
  public static void testFieldGetters(IForm form) {
    Method[] methods = form.getClass().getMethods();

    for (IFormField field : form.getAllFields()) {
      for (Method m : methods) {
        if (m.getName().equalsIgnoreCase("get" + field.getClass().getSimpleName())) {
          Assert.assertEquals(0, m.getParameterTypes().length);
          try {
            Object o = m.invoke(form);
            Assert.assertEquals(field.getClass(), o.getClass());
          }
          catch (Exception e) {
            Assert.fail(e.getMessage());
          }
        }
      }
    }
  }

  /**
   * Searches for a form on the current session desktop
   * 
   * @param formType
   *          the type of the form to be searched for
   * @return the form found
   */
  public static <T extends IForm> T findLastAddedForm(Class<T> formType) {
    IClientSession session = ClientSyncJob.getCurrentSession();
    if (session != null) {
      IDesktop desktop = session.getDesktop();
      if (desktop != null) {
        T[] forms = desktop.findForms(formType);
        if (forms == null || forms.length == 0) {
          return null;
        }
        return forms[forms.length - 1];
      }
      else {
        LOG.warn("Trying to find form, but Desktop is null");
      }
    }
    else {
      LOG.warn("Trying to find form, but ClientSession is null");
    }
    return null;
  }

  /**
   * Closes all open forms on the current session
   * 
   * @throws ProcessingException
   */
  public static void closeAllBlockingForms() throws ProcessingException {
    IClientSession session = ClientSyncJob.getCurrentSession();
    if (session != null) {
      IForm[] forms = session.getDesktop().getDialogStack();
      for (IForm form : forms) {
        form.doClose();
      }
    }
    else {
      LOG.warn("Trying to close all forms, but ClientSession is null");
    }
  }

  /**
   * checks recursively if a field is visible (perhaps it inherited invisibility from parent)
   */
  public static boolean isFieldVisible(IFormField field) {
    if (field == null) {
      return true;
    }
    if (!field.isVisible()) {
      return false;
    }
    return isFieldVisible(field.getParentField());
  }

  /**
   * Test a blocking button
   * 
   * @param button
   *          the button to be tested
   * @param runnableAfterButtonExecution
   *          testing code to be executed after the buttons enters a blocking condition
   * @throws ProcessingException
   */
  public static void runBlockingButton(IButton button, TestingRunnable runnableAfterButtonExecution) throws ProcessingException {
    BlockingButtonAction action = new BlockingButtonAction(button);
    BlockingTestUtility.runBlockingAction(action, runnableAfterButtonExecution);
  }

  /**
   * gets the first entry of a smartfield that is enabled and active
   */
  public static <T> boolean selectFirstEntry(ISmartField<T> smartField) {
    if (smartField == null || smartField.getLookupCall() == null) {
      return false;
    }
    try {
      LookupRow[] lookupRows = smartField.callBrowseLookup(null, 10);
      if (lookupRows != null) {
        for (LookupRow row : lookupRows) {
          if (row.isActive() && row.isEnabled()) {
            @SuppressWarnings("unchecked")
            T value = (T) row.getKey();
            smartField.setValue(value);
            if (smartField.getErrorStatus() == null) {
              return true;
            }
          }
        }
      }
    }
    catch (ProcessingException e) {
      LOG.warn("enexpected exception while selecting first valid value in smart field [" + smartField + "]", e);
    }
    return false;
  }

  private static Map<String, Object> collectFieldValues(IForm form) {
    Map<String, Object> result = new HashMap<String, Object>();
    for (IFormField formField : form.getAllFields()) {
      if (formField.getParentField() instanceof AbstractTableField) {
        continue;
      }
      if (formField.getParentField() instanceof AbstractListBoxFilterBox) {
        continue;
      }
      if (formField instanceof IValueField && formField.isEnabled() && FormTestUtility.isFieldVisible(formField)) {
        IValueField<?> valueField = (IValueField<?>) formField;
        result.put(valueField.getFieldId(), valueField.getValue());
      }
    }
    return result;
  }

  /**
   * Ascertains that the second field has a higher value than the first. <code>null</code> as value is always allowed.
   * 
   * @param form
   *          the form in which the fields are read or edited, so the condition is met.
   * @return map of field ids with assigned values, to be used with {@link #assertValueFields(Map, IForm)}; must be read
   *         from the last call;
   */
  private static void ensureAscending(IForm form, OrderedFieldPair orderedPair) {
    IValueField<?> field1 = form.getFieldByClass(orderedPair.getFirstField());
    IValueField<?> field2 = form.getFieldByClass(orderedPair.getSecondField());
    if (field1.getValue() == null || field2.getValue() == null) {
      return;
    }
    assertTrue("both field values must be of the same type: " + field1.getClass() + " and " + field2.getClass()
        , field1.getValue().getClass().equals(field2.getValue().getClass()));

    if (field1 instanceof IDateField) {
      IDateField dateField1 = (IDateField) field1;
      IDateField dateField2 = (IDateField) field2;
      dateField2.setValue(DateUtility.addDays(dateField1.getValue(), 1d));
    }
    else if (field1 instanceof IBigDecimalField) {
      IBigDecimalField bigDecimalField1 = (IBigDecimalField) field1;
      IBigDecimalField bigDecimalField2 = (IBigDecimalField) field2;
      bigDecimalField2.setValue(bigDecimalField1.getValue().add(BigDecimal.valueOf(0.5d)));
    }
    else if (field1 instanceof IDoubleField) {
      IDoubleField doubleField1 = (IDoubleField) field1;
      IDoubleField doubleField2 = (IDoubleField) field2;
      doubleField2.setValue(doubleField1.getValue() + 0.5d);
    }
    else if (field1 instanceof IIntegerField) {
      IIntegerField integerField1 = (IIntegerField) field1;
      IIntegerField integerField2 = (IIntegerField) field2;
      integerField2.setValue(integerField1.getValue() + 1);
    }
    else if (field1 instanceof ILongField) {
      ILongField longField1 = (ILongField) field1;
      ILongField longField2 = (ILongField) field2;
      longField2.setValue(Long.valueOf(longField1.getValue() + 1L));

    }
    else if (field1 instanceof IBigIntegerField) {
      IBigIntegerField bigIntegerField1 = (IBigIntegerField) field1;
      IBigIntegerField bigIntegerField2 = (IBigIntegerField) field2;
      bigIntegerField2.setValue(bigIntegerField1.getValue().add(BigInteger.valueOf(1L)));
    }
    else {
      fail("type not supported: " + field1.getClass());
    }
    return;
  }

  /**
   * class for pairs of fields, of which the first must be smaller than the second by definition
   */
  public static class OrderedFieldPair {
    private Class<? extends IValueField<?>> m_firstField;
    private Class<? extends IValueField<?>> m_secondField;

    Class<? extends IValueField<?>> getFirstField() {
      return m_firstField;
    }

    Class<? extends IValueField<?>> getSecondField() {
      return m_secondField;
    }

    /**
     * @param firstField
     *          class of the first field, is supposed to be <code>null</code> or smaller than the first field
     * @param secondField
     *          class of the second field; is supposed to be <code>null</code> or larger than the first field
     */
    public OrderedFieldPair(Class<? extends IValueField<?>> firstField, Class<? extends IValueField<?>> secondField) {
      super();
      m_firstField = firstField;
      m_secondField = secondField;
    }
  }

}
