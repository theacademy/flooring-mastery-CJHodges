package com.mthree.ui;

import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryView {

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    public UserIO io;


    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public void displayCreateOrderBanner() {
        io.print("*** Create Order ***");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Order successfully created. Please hit enter to continue.");
    }

    public void displayDisplayOrdersBanner() {
        io.print("*** Displaying Orders ***");
    }

    public void displayEditOrderBanner() {
        io.print("*** Edit Order ***");
    }

    public void displayRemoveOrderBanner() {
        io.print("*** Remove Order ***");
    }

    public void displayOrderList(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            io.print(currentOrder.toString());
            io.print("---");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayOrder(Order order) {
        if (order != null) {
            io.print(order.toString());
        } else {
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue.");
    }

    public int getOrderIdChoice() {
        return io.readInt("Please enter the Order Number: ");
    }

    public LocalDate getOrderDate() {
        return io.readDate("Please enter the Order Date (MM/dd/yyyy): ");
    }

    public LocalDate getFutureDate() {
        while (true) {
            LocalDate date = io.readDate("Please enter the Order Date (MM/dd/yyyy): ");
            if (date.isAfter(LocalDate.now())) {
                return date;
            }
            io.print("Order date must be in the future.");
        }
    }

    public String getState(List<Tax> taxes) {
        io.print("Available states:");
        for (Tax t : taxes) {
            io.print(t.getState() + " - " + t.getStateName() + " (" + t.getTaxRate() + "%)");
        }
        while (true) {
            String state = io.readString("Please enter State: ").toUpperCase();
            if (taxes.stream().anyMatch(t -> t.getState().equals(state))) {
                return state;
            }
            io.print("Invalid state; we cannot sell there.");
        }
    }

    public String getProductType(List<Product> products) {
        io.print("Available products:");
        for (Product p : products) {
            io.print(p.getProductType() + " - Cost/sqft: " + p.getCostPerSquareFoot() + ", Labor/sqft: " + p.getLaborCostPerSquareFoot());
        }
        while (true) {
            String type = io.readString("Please enter Product Type: ");
            if (products.stream().anyMatch(p -> p.getProductType().equalsIgnoreCase(type))) {
                return type;
            }
            io.print("Invalid product type.");
        }
    }

    public boolean getYesNo(String prompt) {
        while (true) {
            String input = io.readString(prompt + " (Y/N): ").toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            io.print("Please enter Y or N.");
        }
    }

    public void displayRemoveResult(Order orderRecord) {
        if (orderRecord != null) {
            io.print("Order successfully removed.");
        } else {
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayExitBanner() {
        io.print("*** Good Bye ***");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}