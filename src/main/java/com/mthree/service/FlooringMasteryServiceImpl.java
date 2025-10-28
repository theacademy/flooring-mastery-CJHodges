package com.mthree.service;

import com.mthree.dao.FlooringMasteryAuditDao;
import com.mthree.dao.FlooringMasteryDao;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    private final FlooringMasteryDao dao;
    private final FlooringMasteryAuditDao auditDao;

    public FlooringMasteryServiceImpl(FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void createOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        validateOrder(order);
        dao.addOrder(order);
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " CREATED.");
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        return dao.getOrdersByDate(date);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        return dao.getOrder(orderNumber, date);
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        validateOrder(order);
        dao.editOrder(order);
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " UPDATED.");
    }

    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        Order removed = dao.removeOrder(orderNumber, date);
        if (removed != null) {
            auditDao.writeAuditEntry("Order " + orderNumber + " REMOVED.");
        }
        return removed;
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        return dao.getAllTaxes();
    }

    @Override
    public Product getProductByType(String type) throws FlooringMasteryPersistenceException {
        return getAllProducts().stream()
                .filter(p -> p.getProductType().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Tax getTaxByState(String state) throws FlooringMasteryPersistenceException {
        return getAllTaxes().stream()
                .filter(t -> t.getState().equalsIgnoreCase(state))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getNextOrderNumber() throws FlooringMasteryPersistenceException {
        return dao.getNextOrderNumber();
    }

    @Override
    public void validateOrder(Order order) throws FlooringMasteryDataValidationException {
        StringBuilder errors = new StringBuilder();
        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            errors.append("Customer name invalid. ");
        }
        if (order.getTax() == null) {
            errors.append("Invalid state. ");
        }
        if (order.getProduct() == null) {
            errors.append("Invalid product type. ");
        }
        if (order.getArea() == null || order.getArea().compareTo(new BigDecimal("100")) < 0) {
            errors.append("Area must be >=100. ");
        }
        if (order.getLd().isBefore(LocalDate.now())) {
            errors.append("Date must be future. ");
        }
        if (errors.length() > 0) {
            throw new FlooringMasteryDataValidationException(errors.toString());
        }
    }
}