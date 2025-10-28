package com.mthree.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void print(String msg);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    BigDecimal readArea(String msgPrompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);


    String readCustomerName(String msgPrompt);

    LocalDate readDate(String prompt);

}
