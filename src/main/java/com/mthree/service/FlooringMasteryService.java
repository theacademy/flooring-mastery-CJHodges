package com.mthree.service;

import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {


    void createOrder(Order order) throws
            FlooringMasteryDuplicateIdException,
            FlooringMasteryDataValidationException,
            FlooringMasteryPersistenceException;

    List<Order> getAllOrders() throws
            FlooringMasteryPersistenceException;

    Order getOrder(int orderId) throws
            FlooringMasteryPersistenceException;

    Order removeOrder(int orderId, LocalDate date) throws
            FlooringMasteryPersistenceException;

}
