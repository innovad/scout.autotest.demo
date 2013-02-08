package org.eclipse.scout.testing.client.page;

import java.lang.reflect.Method;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.testing.client.form.field.IFormFieldValueProvider;
import org.eclipse.scout.testing.client.form.field.MaxFormFieldValueProvider;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @param <T>
 *          the type of the table page to be tested
 */
@RunWith(ScoutClientTestRunner.class)
public abstract class AbstractTablePageTest<T extends IPageWithTable<?>> {

  @Test
  public void testLoad() throws ProcessingException {
    IPageWithTable<?> page = getTablePage();
    Assert.assertNotNull(page);
    page.nodeAddedNotify();
    page.loadChildren();
  }

  @Test
  public void testSearchFormMax() throws ProcessingException {
    testSearchForm(new MaxFormFieldValueProvider());
  }

  @Test
  public void testColumns() throws ProcessingException {
    T page = getTablePage();
    Method[] methods = page.getTable().getClass().getMethods();
    for (IColumn<?> column : page.getTable().getColumns()) {
      for (Method m : methods) {
        if (m.getName().equalsIgnoreCase("get" + column.getClass().getSimpleName())) {
          Assert.assertEquals(0, m.getParameterTypes().length);
          try {
            Object o = m.invoke(page.getTable());
            Assert.assertEquals(column.getClass(), o.getClass());
          }
          catch (Exception e) {
            Assert.fail(e.getMessage());
          }
        }
      }
    }
  }

  private void testSearchForm(IFormFieldValueProvider valueProvider) throws ProcessingException {
    T tablePage = getTablePage();
    ISearchForm searchForm = tablePage.getSearchFormInternal();

    // ignore test if there is no search form
    Assume.assumeNotNull(searchForm);

    for (IFormField field : searchForm.getAllFields()) {
      if (field instanceof IValueField) {
        searchForm.doReset();
        fillMandatoryFields(searchForm, valueProvider);
        valueProvider.fillValueField((IValueField<?>) field, null);
        searchForm.resetSearchFilter();
        tablePage.loadChildren();
      }
    }
  }

  private void fillMandatoryFields(ISearchForm searchForm, IFormFieldValueProvider valueProvider) throws ProcessingException {
    if (searchForm == null) {
      return;
    }
    for (IFormField field : searchForm.getAllFields()) {
      if (field instanceof IValueField && ((IValueField<?>) field).isMandatory()) {
        valueProvider.fillValueField((IValueField<?>) field, null);
      }
    }
  }

  /**
   * @return an instance of the table page to be tested. The page must not be loaded or added to a node.
   * @throws ProcessingException
   */
  protected abstract T getTablePage() throws ProcessingException;

}
