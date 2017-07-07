package com.example.sharangirdhani.homework2;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sharangirdhani on 7/6/17.
 */

public class Expense {
    String Name;
    int categoryId;
    int amount;
    Date date;
    Uri recipt;

    public Expense(String name, int categoryId, int amount, Date date, Uri recipt) {
        this.Name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.recipt = recipt;
    }

    public String getName() {
        return Name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Uri getRecipt() {
        return recipt;
    }

    public String getCategoryNameById(int categoryId) {
        switch (categoryId) {
            case 0:
                return "Groceries";
            case 1:
                return "Invoice";
            case 2:
                return "Transportation";
            case 3:
                return "Shopping";
            case 4:
                return "Rent";
            case 5:
                return "Trips";
            case 6:
                return "Utilities";
            case 7:
                return "Other";
        }
        return "Other";
    }

    public ArrayList<String> getCategoryList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Groceries", "Invoice", "Transportation", "Shopping", "Rent", "Trips", "Utilities", "Other"));
        return list;
    }
}
