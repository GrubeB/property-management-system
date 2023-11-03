package pl.app.common.core.service.criteria;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaItem implements Serializable {

    @NotNull
    private String field;

    @NotNull
    private Operator operator;
    private transient String value;

    private transient String valueTo;

    private transient List<String> values;

    public SearchCriteriaItem() {
        this.values = new ArrayList<>();
    }

    public SearchCriteriaItem(String field, Operator operator, String value, String valueTo, List<String> values) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.valueTo = valueTo;
        this.values = values;
    }

    public SearchCriteriaItem(String field, Operator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public SearchCriteriaItem(String field, Operator operator, String value, String valueTo) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.valueTo = valueTo;
    }

    public SearchCriteriaItem(String field, Operator operator, List<String> values) {
        this.field = field;
        this.operator = operator;
        this.values = values;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "SearchCriteriaItem{" +
                "key='" + field + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                ", valueTo=" + valueTo +
                ", values=" + values +
                '}';
    }
}
