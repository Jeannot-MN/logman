package com.jmn.logman.service.util.security;

import java.util.ArrayList;
import java.util.List;

public class PasswordDecision {

    private boolean valid;
    private List<PasswordCriterion> criteria = new ArrayList<>();

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<PasswordCriterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<PasswordCriterion> criteria) {
        this.criteria = criteria;
    }
}
