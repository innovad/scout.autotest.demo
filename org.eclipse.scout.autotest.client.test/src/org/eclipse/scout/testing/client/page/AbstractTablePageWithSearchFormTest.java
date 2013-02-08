/**
 *
 */
package org.eclipse.scout.testing.client.page;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.testing.client.form.FormTestUtility;
import org.eclipse.scout.testing.client.form.field.IFormFieldValueProvider;
import org.eclipse.scout.testing.client.form.field.MaxFormFieldValueProvider;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author Adrian Moser
 */
public abstract class AbstractTablePageWithSearchFormTest<T> extends AbstractTablePageTest<IPageWithTable<?>> {

  @Test
  public void testSearchMax() throws ProcessingException {
    testSearchForm(new MaxFormFieldValueProvider());
  }

  @Test
  public void testSearchForm() throws ProcessingException {
    IPageWithTable<?> tablePage = getTablePage();
    ISearchForm searchForm = tablePage.getSearchFormInternal();
    Assume.assumeNotNull(searchForm);

    FormTestUtility.testFieldGetters(searchForm);
  }

  private void testSearchForm(IFormFieldValueProvider valueProvider) throws ProcessingException {
    IPageWithTable<?> tablePage = getTablePage();
    ISearchForm searchForm = tablePage.getSearchFormInternal();
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

}
