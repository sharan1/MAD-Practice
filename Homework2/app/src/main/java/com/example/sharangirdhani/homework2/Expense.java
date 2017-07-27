package com.example.sharangirdhani.homework2;

import android.net.Uri;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sharangirdhani on 7/6/17.
 */

public class Expense implements Serializable{
    public static DateFormat dateFormat = java.text.DateFormat.getDateInstance(DateFormat.DATE_FIELD);

    private String Name;
    private int categoryId;
    private int amount;
    private Date date;
    private String receipt;

    public Expense(String name, int categoryId, int amount, Date date, String receipt) {
        this.Name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.receipt = receipt;
    }

    public String getName() {
        return this.Name;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public int getAmount() {
        return this.amount;
    }

    public Date getDate() {
        return this.date;
    }

    public String getReceipt() {
        return this.receipt;
    }

    public String getCategoryNameById(int categoryId) {
        switch (categoryId) {
            case 1:
                return "Groceries";
            case 2:
                return "Invoice";
            case 3:
                return "Transportation";
            case 4:
                return "Shopping";
            case 5:
                return "Rent";
            case 6:
                return "Trips";
            case 7:
                return "Utilities";
            case 8:
                return "Other";
        }
        return null;
    }

    // Static categories
    public static String[] categories = {
            "Select a category",
            "Groceries",
            "Invoice",
            "Transportation",
            "Shopping",
            "Rent",
            "Trip",
            "Utilities",
            "Others"
    };
}
