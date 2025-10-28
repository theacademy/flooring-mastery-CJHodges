package com.mthree.dao;

import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
    @Override
    public List<Product> getAllProduct() {
        return List.of();
    }

    @Override
    public List<Tax> getAllTax() {
        return List.of();
    }

    @Override
    public List<Order> getAllOrder() {
        return List.of();
    }

    @Override
    public Order addorder(Product product, Tax tax, String customerName, int orderNumber, BigDecimal area) throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override
    public Order getOrder(LocalDate date) throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override
    public Order editOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        return null;
    }
}
