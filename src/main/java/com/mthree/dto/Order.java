package com.mthree.dto;

//OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Order {
    private Product product;
    private Tax tax;
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal labourCost;
    private BigDecimal taxCost;
    private BigDecimal total;
    private LocalDate ld;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return  "Order Number: " +orderNumber +
                " Name: " +customerName +
                " State:  "+tax.getState() +
                " Tax Rate: " + tax.getTaxRate() +
                " Product Type: " + product.getProductType() +
                " area: " + area +
                " Cost Per Square Foot: " + product.getCostPerSquareFoot() +
                " labour Cost Per Square Foot: " + product.getLaborCostPerSquareFoot() +
                " Material Cost: " + materialCost +
                " Labour Cost : " + labourCost +
                " Tax Cost: " + taxCost +
                " Total: " + total +
                '}';
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



    private static final BigDecimal taxDivider = new BigDecimal(100);

    public Order(Product product, Tax tax, String customerName, int orderNumber, BigDecimal area, String date){
        this.product=product;
        this.tax = tax;
        this.customerName = customerName;
        this.orderNumber = orderNumber;
        this.area = area;
        materialCost = area.multiply(product.getCostPerSquareFoot());
        labourCost = area.multiply(product.getLaborCostPerSquareFoot());
        taxCost = (materialCost.add(labourCost)).multiply (tax.getTaxRate().divide(taxDivider ,2, RoundingMode.HALF_UP));
        total = materialCost.add(labourCost.add(taxCost));
        ld = LocalDate.parse(date);

    }



}
