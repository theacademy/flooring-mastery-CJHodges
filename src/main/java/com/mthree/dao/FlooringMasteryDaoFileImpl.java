package com.mthree.dao;

import com.mthree.dto.Order;
import com.mthree.dto.Product;
import com.mthree.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {
    private static final String TAXES_FILE = "Data/Taxes.txt";
    private static final String PRODUCTS_FILE = "Data/Products.txt";
    private static final String ORDERS_DIR = "Orders/";
    private static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    private final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");
    private int nextOrderNumber = 1;

    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Tax> taxes = new HashMap<>();

    public FlooringMasteryDaoFileImpl() {
        new File(ORDERS_DIR).mkdirs();
        try {
            loadProducts();
            loadTaxes();
            loadNextOrderNumber();
        } catch (FlooringMasteryPersistenceException e) {
            // Handle or log
        }
    }

    private void loadProducts() throws FlooringMasteryPersistenceException {
        try (Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // Skip header
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] productDetails = line.split(",");
                if (productDetails.length == 3) {
                    Product p = new Product(productDetails[0], new BigDecimal(productDetails[1]), new BigDecimal(productDetails[2]));
                    products.put(p.getProductType().toUpperCase(), p);
                }
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load products.", e);
        }
    }

    private void loadTaxes() throws FlooringMasteryPersistenceException {
        try (Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // Skip header
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] taxDetails = line.split(",");
                if (taxDetails.length == 3) {
                    Tax t = new Tax(taxDetails[0], taxDetails[1], new BigDecimal(taxDetails[2]));
                    taxes.put(t.getState().toUpperCase(), t);
                }
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load taxes.", e);
        }
    }

    private void loadNextOrderNumber() throws FlooringMasteryPersistenceException {
        int max = 0;
        File dir = new File(ORDERS_DIR);
        File[] files = dir.listFiles((d, name) -> name.startsWith("Orders_") && name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                // Extract date from filename, e.g., Orders_10282025.txt -> 10/28/2025
                String mmddyyyy = file.getName().substring(7, 15);
                LocalDate date = LocalDate.parse(mmddyyyy, FILE_DATE_FORMAT);
                List<Order> orders = loadOrdersFromFile(date, file.getAbsolutePath());
                for (Order o : orders) {
                    if (o.getOrderNumber() > max) max = o.getOrderNumber();
                }
            }
        }
        nextOrderNumber = max + 1;
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Order addOrder(Order order) throws FlooringMasteryPersistenceException {
        order.setOrderNumber(nextOrderNumber++);
        List<Order> orders = getOrdersByDate(order.getLd());
        orders.add(order);
        writeOrdersToFile(orders, order.getLd());
        return order;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        String filePath = ORDERS_DIR + "Orders_" + date.format(FILE_DATE_FORMAT) + ".txt";
        return loadOrdersFromFile(date, filePath);
    }

    private List<Order> loadOrdersFromFile(LocalDate date, String filePath) throws FlooringMasteryPersistenceException {
        List<Order> orders = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return orders;
        try (Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // Skip header
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] orderDetails = line.split(",");
                if (orderDetails.length == 12) {
                    Product p = new Product(orderDetails[4], new BigDecimal(orderDetails[6]), new BigDecimal(orderDetails[7]));
                    Tax t = new Tax(orderDetails[2], "", new BigDecimal(orderDetails[3]));
                    Order o = new Order();
                    o.setOrderNumber(Integer.parseInt(orderDetails[0]));
                    o.setCustomerName(orderDetails[1]);
                    o.setTax(t);
                    o.setProduct(p);
                    o.setArea(new BigDecimal(orderDetails[5]));
                    o.setMaterialCost(new BigDecimal(orderDetails[8]));
                    o.setLabourCost(new BigDecimal(orderDetails[9]));
                    o.setTaxCost(new BigDecimal(orderDetails[10]));
                    o.setTotal(new BigDecimal(orderDetails[11]));
                    o.setLd(date);
                    orders.add(o);
                }
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not load orders.", e);
        }
        return orders;
    }

    private void writeOrdersToFile(List<Order> orders, LocalDate date) throws FlooringMasteryPersistenceException {
        String filePath = ORDERS_DIR + "Orders_" + date.format(FILE_DATE_FORMAT) + ".txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println(HEADER);
            for (Order o : orders) {
                out.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", //%d placeholder for int, %s placeholder for string. BigDecimal Values  converted to strings automatically
                        o.getOrderNumber(), o.getCustomerName(), o.getTax().getState(), o.getTax().getTaxRate(),
                        o.getProduct().getProductType(), o.getArea(), o.getProduct().getCostPerSquareFoot(),
                        o.getProduct().getLaborCostPerSquareFoot(), o.getMaterialCost(), o.getLabourCost(),
                        o.getTaxCost(), o.getTotal());
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save orders.", e);
        }
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        return getOrdersByDate(date).stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order editOrder(Order updated) throws FlooringMasteryPersistenceException {
        LocalDate date = updated.getLd();
        List<Order> orders = getOrdersByDate(date);
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderNumber() == updated.getOrderNumber()) {
                orders.set(i, updated);
                writeOrdersToFile(orders, date);
                return updated;
            }
        }
        return null;
    }



    @Override
    public Order removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryPersistenceException {
        List<Order> orders = getOrdersByDate(date);
        Order removed = null;
        for (Iterator<Order> it = orders.iterator(); it.hasNext();) {
            Order o = it.next();
            if (o.getOrderNumber() == orderNumber) {
                removed = o;
                it.remove();
                break;
            }
        }
        if (removed != null) {
            if (orders.isEmpty()) {
                new File(ORDERS_DIR + "Orders_" + date.format(FILE_DATE_FORMAT) + ".txt").delete();
            } else {
                writeOrdersToFile(orders, date);
            }
        }
        return removed;
    }

    @Override
    public int getNextOrderNumber() throws FlooringMasteryPersistenceException {
        return nextOrderNumber;
    }
}