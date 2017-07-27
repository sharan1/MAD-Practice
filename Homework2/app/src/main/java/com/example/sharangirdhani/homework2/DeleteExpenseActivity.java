package com.example.sharangirdhani.homework2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteExpenseActivity extends AppCompatActivity {
    private ArrayList<Expense> expenses;

    // Setup UI components
    private EditText expenseName;
    private Spinner category;
    private EditText amount;
    private EditText date;
    private ImageView receiptView;
    private Button btnDelete;

    private int selectedExpense = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);

        // Get the Expenses array from intent
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.EXPENSE_ARRAY_KEY)) {
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);
        }
        else {
            // Error handling
            Log.e("hw2","Delete Activity called without any intent. This should not happen");
            finish();
        }

        expenseName = (EditText) findViewById(R.id.etExpenseName);
        amount = (EditText) findViewById(R.id.etAmount);
        date = (EditText) findViewById(R.id.etDate);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        category.setEnabled(false);

        receiptView = (ImageView) findViewById(R.id.imageReciept);
        receiptView.setEnabled(false);

        btnDelete = (Button)findViewById(R.id.btnDelete);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,Expense.categories);
        category.setAdapter(spinnerAdapter);
    }

    public void selectExpense(View view) {
        // Show and Alert dialog to choose expense if there are any
        if(expenses != null && expenses.size() > 0) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            CharSequence expenseNames[] = new CharSequence[expenses.size()];
            for (Expense expense : expenses) {
                expenseNames[expenses.indexOf(expense)] = expense.getName();
            }

            alertDialog.setTitle(getString(R.string.alert_title_Pick_Expense));
            alertDialog.setItems(expenseNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedExpense = which;
                    displayExpense(expenses.get(which));
                }
            });
            // Show alert dialog
            alertDialog.create().show();
        } else {
            Toast.makeText(this,getString(R.string.no_expense_lable),Toast.LENGTH_SHORT).show();
        }
    }

    private void displayExpense(Expense expense){

        // Update UI with expense details
        expenseName.setText(expense.getName());
        date.setText(Expense.dateFormat.format(expense.getDate()));

        amount.setText(String.valueOf(expense.getAmount()));
        category.setSelection(expense.getCategoryId());

        if (expense.getReceipt().length() > 0 ) {
            receiptView.setImageURI(Uri.parse(expense.getReceipt()));
        }

    }

    public void deleteExpense(View view) {
        // Delete the selected Expense object
        if (selectedExpense >= 0) {
            expenses.remove(selectedExpense);
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXPENSE_ARRAY_KEY, expenses);
            setResult(1, intent);
            finish();
        } else {
            Toast.makeText(this,getString(R.string.no_expense_lable),Toast.LENGTH_SHORT).show();
        }
    }

    public void finishActivity(View view){
        // End Activity
        finish();
    }
}
