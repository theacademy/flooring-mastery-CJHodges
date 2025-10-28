package com.mthree.dao;

import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import com.mthree.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryDao {
        List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
        List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException;
        Order addOrder(Order order) throws FlooringMasteryPersistenceException;
        List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException;
        Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException;
        Order editOrder(Order order) throws FlooringMasteryPersistenceException;
        Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException;
        int getNextOrderNumber() throws FlooringMasteryPersistenceException;
    }
