package com.mthree.service;

import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {
    void createOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;
    List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException;
    Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException;
    void editOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;
    Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException;
    List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
    List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException;
    Product getProductByType(String type) throws FlooringMasteryPersistenceException;
    Tax getTaxByState(String state) throws FlooringMasteryPersistenceException;
    int getNextOrderNumber() throws FlooringMasteryPersistenceException;
    void validateOrder(Order order) throws FlooringMasteryDataValidationException;
}