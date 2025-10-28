package com.mthree.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    private final Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    @Override
    public String readCustomerName(String msgPrompt) {
        boolean flag = false;
        do {
            System.out.println(msgPrompt);
            String userInput = console.nextLine();
            if (userInput.matches("^[a-zA-Z0-9., ]+$") && !userInput.trim().isEmpty()) {
                flag = true;
                return userInput;
            } else {
                System.out.println("Name may not be blank and is limited to characters [a-z][0-9] as well as periods and comma characters.");
            }
        } while (!flag);
        return null;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            String userInput = console.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            try {
                LocalDate ld = LocalDate.parse(userInput, formatter);
                valid = true;
                return ld;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format (MM/dd/yyyy) or value, please try again.");
            }
        }
        return null;
    }

    @Override
    public BigDecimal readArea(String msgPrompt) {
        while (true) {
            String input = readString(msgPrompt);
            try {
                BigDecimal area = new BigDecimal(input);
                if (area.compareTo(BigDecimal.ZERO) > 0 && area.compareTo(new BigDecimal("100")) >= 0) {
                    return area;
                } else {
                    print("Area must be a positive decimal at least 100 sq ft.");
                }
            } catch (NumberFormatException e) {
                print("Invalid decimal value.");
            }
        }
    }

    @Override
    public int readInt(String msgPrompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(msgPrompt));
            } catch (NumberFormatException e) {
                print("Input error. Please try again.");
            }
        }
    }

    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(readString(msgPrompt));
            } catch (NumberFormatException e) {
                print("Input error. Please try again.");
            }
        }
    }

    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(readString(msgPrompt));
            } catch (NumberFormatException e) {
                print("Input error. Please try again.");
            }
        }
    }

    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(msgPrompt));
            } catch (NumberFormatException e) {
                print("Input error. Please try again.");
            }
        }
    }

    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }
}