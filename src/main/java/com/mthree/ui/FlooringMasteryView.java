package com.mthree.ui;

import com.mthree.dto.Order;

import java.util.List;

public class FlooringMasteryView {

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    private UserIO io;

    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove a Order");
        io.print("* 5. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices.", 1, 5);
    }

    //Change to get newOrder
    public Student getNewOrderInfo() {
        String date = io.readString("Please enter Order Date: "); //Add checkers
        String customerName = io.readString("PLease enter Customer Name: ");
        String state = io.readString("Please enter State: ");
        String productType = io.readString("Please enter Product Type");
        String area = io.readString("Please enter area: ");


        Order currentOrder = new Order();
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setCohort(cohort);
        return currentStudent;
    }


    public void displayCreateStudentBanner() {
        io.print("*** Create Order ***");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "Order successfully Made.  Please hit enter to continue");
    }

    //Change to Display Orders
    public void displayStudentList(List<Student> studentList) {
        for (Student currentStudent : studentList) {
            String studentInfo = String.format("#%s : %s %s",
                    currentStudent.getStudentId(),
                    currentStudent.getFirstName(),
                    currentStudent.getLastName());
            io.print(studentInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayAllBanner() {
        io.print("*** Displaying Orders ***");
    }

    public void displayDisplayStudentBanner () {
        io.print("*** Display Student ***");
    }

    public String getOrderIdChoice() {
        return io.readInt("Please enter the Order Number: ");
    }
    public String getOrderDate() {return io.readString("Please enter the Order Date: ");
    }

    //Change to Display Order
    public void displayStudent(Student student) {
        if (student != null) {
            io.print(student.getStudentId());
            io.print(student.getFirstName() + " " + student.getLastName());
            io.print(student.getCohort());
            io.print("");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveStudentBanner () {
        io.print("*** Remove Order ***");
    }


    public void displayRemoveResult(Order orderRecord) {
        if(orderRecord != null){
            io.print("Order successfully removed.");
        }else{
            io.print("No such Order.");
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
