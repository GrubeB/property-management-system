package pl.app.common.core.service.criteria;


import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteria implements Serializable {
    private List<@Valid SearchCriteriaItem> criteria;

    public SearchCriteria() {
        this.criteria = new ArrayList<>();
    }

    public SearchCriteria(List<SearchCriteriaItem> criteria) {
        this.criteria = criteria;
    }

    public void addCriteria(SearchCriteriaItem item) {
        this.criteria.add(item);
    }

    public void setCriteria(List<SearchCriteriaItem> criteria) {
        this.criteria = criteria;
    }

    public List<SearchCriteriaItem> getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "criteria=" + criteria +
                '}';
    }
}
