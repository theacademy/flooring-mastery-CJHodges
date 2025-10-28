package com.mthree.dto;

import java.math.BigDecimal;

public class Tax {
    private String state;
    private String stateName;
    private BigDecimal taxRate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }



    public Tax (String state, String stateName, BigDecimal taxRate){
        this.taxRate = taxRate;
        this.state = state;
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return "Tax{" + " Tax Rate= " + taxRate + ", StateID= " + state + ", State Name= " + stateName + '}';
    }


}
