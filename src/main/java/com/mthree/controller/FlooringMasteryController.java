package com.mthree.controller;

import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import com.mthree.service.FlooringMasteryDataValidationException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.service.FlooringMasteryService;
import com.mthree.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasteryController {

    private final FlooringMasteryView view;
    private final FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            menuSelection = view.printMenuAndGetSelection();
            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    keepGoing = false;
                    break;
                default:
                    view.displayUnknownCommandBanner();
            }
        }
        view.displayExitBanner();
    }

    private void displayOrders() {
        view.displayDisplayOrdersBanner();
        LocalDate date = view.getOrderDate();
        try {
            List<Order> orders = service.getOrdersByDate(date);
            if (orders.isEmpty()) {
                view.io.print("No orders for that date.");
            } else {
                view.displayOrderList(orders);
            }
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() {
        view.displayCreateOrderBanner();
        try {
            LocalDate date = view.getFutureDate();
            String customerName = view.io.readCustomerName("Please enter Customer Name: ");
            List<Tax> taxes = service.getAllTaxes();
            String state = view.getState(taxes);
            Tax tax = service.getTaxByState(state);
            List<Product> products = service.getAllProducts();
            String productType = view.getProductType(products);
            Product product = service.getProductByType(productType);
            BigDecimal area = view.io.readArea("Please enter area: ");
            int orderNumber = service.getNextOrderNumber();
            String dateStr = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Order order = new Order(product, tax, customerName, orderNumber, area, dateStr);
            service.validateOrder(order); // Extra check, though most validated
            view.displayOrder(order);
            if (view.getYesNo("Place order?")) {
                service.createOrder(order);
                view.displayCreateSuccessBanner();
            }
        } catch (FlooringMasteryPersistenceException | FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() {
        view.displayEditOrderBanner();
        LocalDate date = view.getOrderDate();
        int number = view.getOrderIdChoice();
        try {
            Order order = service.getOrder(number, date);
            if (order == null) {
                view.displayErrorMessage("No such order.");
                return;
            }
            view.displayOrder(order);
            String newName = view.io.readString("Enter customer name (" + order.getCustomerName() + "): ");
            if (!newName.isBlank()) {
                order.setCustomerName(newName);
            }

            List<Tax> taxes = service.getAllTaxes();
            String newState = view.getState(taxes);
            if (!newState.isBlank()) {
                Tax newTax = service.getTaxByState(newState);
                if (newTax != null) {
                    order.setTax(newTax);
                } else {
                    view.displayErrorMessage("Invalid state; keeping original.");
                }
            }
            List<Product> products = service.getAllProducts();
            String newType = view.getProductType(products);
            if (!newType.isBlank()) {
                Product newProduct = service.getProductByType(newType);
                if (newProduct != null) {
                    order.setProduct(newProduct);
                } else {
                    view.displayErrorMessage("Invalid product type; keeping original.");
                }
            }
            String newAreaStr = view.io.readString("Enter area (" + order.getArea() + "): ");
            if (!newAreaStr.isBlank()) {
                try {
                    BigDecimal newArea = new BigDecimal(newAreaStr);
                    if (newArea.compareTo(new BigDecimal("100")) >= 0) {
                        order.setArea(newArea);
                    } else {
                        view.displayErrorMessage("Area must be >=100; keeping original.");
                    }
                } catch (NumberFormatException e) {
                    view.displayErrorMessage("Invalid area; keeping original.");
                }
            }
            order.recalculate();
            view.displayOrder(order);
            if (view.getYesNo("Save edit?")) {
                service.editOrder(order);
                view.io.print("Order updated.");
            }
        } catch (FlooringMasteryPersistenceException | FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getOrderDate();
        int number = view.getOrderIdChoice();
        try {
            Order order = service.getOrder(number, date);
            if (order == null) {
                view.displayErrorMessage("No such order.");
                return;
            }
            view.displayOrder(order);
            if (view.getYesNo("Remove order?")) {
                Order removed = service.removeOrder(number, date);
                view.displayRemoveResult(removed);
            }
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
}