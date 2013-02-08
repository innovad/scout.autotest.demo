package org.eclipse.scout.autotest.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public class CompanySearchFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public CompanySearchFormData() {
  }

  public Name getName() {
    return getFieldByClass(Name.class);
  }

  public ShortName getShortName() {
    return getFieldByClass(ShortName.class);
  }

  public static class Name extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Name() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }

  public static class ShortName extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public ShortName() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }
}
