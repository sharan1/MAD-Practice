package com.example.sharangirdhani.homework2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {

    private TextView textExpenseName;
    private TextView textExpenseAmount;
    private TextView textExpenseDate;
    private TextView textExpenseCategory;

    private ImageView imageExpenseReceipt;

    private int currentExpense;

    private ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);

        textExpenseName = (TextView) findViewById(R.id.textExpenseName);
        textExpenseAmount = (TextView) findViewById(R.id.textExpenseAmount);
        textExpenseCategory = (TextView) findViewById(R.id.textExpenseCategory);
        textExpenseDate = (TextView) findViewById(R.id.textExpenseDate);
        imageExpenseReceipt = (ImageView) findViewById(R.id.imageExpenseReceipt);

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Get the expenses array from intent
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.EXPENSE_ARRAY_KEY)){
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);
            if (expenses.size() > 0) {
                displayExpense(0);
            }
            else {
                Toast.makeText(this,getString(R.string.no_expense_lable),Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Implement First, Last, Next, Previous Buttons
    public void nextExpense(View v){
        if ( currentExpense < expenses.size() - 1 ) {
            currentExpense += 1;
            displayExpense(currentExpense);
        }
    }

    public void previousExpense(View v){
        if ( currentExpense > 0 ) {
            currentExpense -= 1;
            displayExpense(currentExpense);
        }
    }

    public void firstExpense(View v){
        currentExpense = 0;
        displayExpense(currentExpense);
    }

    public void lastExpense(View v){
        currentExpense = expenses.size() - 1;
        displayExpense(currentExpense);
    }

    void displayExpense(int expenseNumber) {
        // Display the selected expense by updating UI components
        Expense currentExpense = expenses.get(expenseNumber);

        textExpenseName.setText(currentExpense.getName());
        textExpenseCategory.setText(Expense.categories[currentExpense.getCategoryId()]);
        textExpenseDate.setText(Expense.dateFormat.format(currentExpense.getDate()));
        textExpenseAmount.setText(String.valueOf(currentExpense.getAmount()));
        imageExpenseReceipt.setImageURI(Uri.parse(currentExpense.getReceipt()));
    }
}
