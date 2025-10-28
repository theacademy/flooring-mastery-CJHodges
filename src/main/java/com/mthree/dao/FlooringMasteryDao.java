package com.mthree.dao;

import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import com.mthree.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryDao {

    List<Product> getAllProduct()
            throws FlooringMasteryPersistenceException;

    List<Tax> getAllTax()
            throws FlooringMasteryPersistenceException;


    List<Order> getAllOrder()
            throws FlooringMasteryPersistenceException;

    Order addorder(Product product, Tax tax, String customerName, int orderNumber, BigDecimal area)
            throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date)
            throws FlooringMasteryPersistenceException;

    Order removeOrder(int orderNumber, LocalDate date)
            throws FlooringMasteryPersistenceException;

    Order editOrder(int orderNumber, LocalDate date)
            throws FlooringMasteryPersistenceException;

}
