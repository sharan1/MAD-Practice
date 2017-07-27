package com.example.sharangirdhani.homework2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ArrayList<Expense> expenses;

    // Setup UI components
    private EditText expenseName;
    private Spinner category;
    private EditText amount;
    private EditText date;
    private ImageView receiptView;
    private ImageButton imageButtonDatePicker;
    private Button buttonSave ;
    private DatePickerDialog datePickerDialog;
    // Internal variables
    private int selectedExpense = -1;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        // Setup UI components and disable until an Expense is selected
        expenseName = (EditText) findViewById(R.id.etExpenseName);
        amount = (EditText) findViewById(R.id.etAmount);
        date = (EditText) findViewById(R.id.etDate);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        category.setEnabled(false);

        receiptView = (ImageView) findViewById(R.id.imageReciept);
        receiptView.setEnabled(false);

        imageButtonDatePicker = (ImageButton) findViewById(R.id.imageCalender);
        imageButtonDatePicker.setEnabled(false);

        buttonSave = (Button)findViewById(R.id.btnSave);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,Expense.categories);
        category.setAdapter(spinnerAdapter);


        // Get the Expenses array from intent
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.EXPENSE_ARRAY_KEY)) {
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);
        }
        else {
            // Error handling
            Log.e("hw2","Edit Activity called without any intent. This should not happen");
            finish();
        }
    }

    public void selectExpense(View view) {
        // Show and Alert dialog to choose expense if there are any
        if(expenses.size() > 0) {

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

        // Enable All Controls
        expenseName.setEnabled(true);
        amount.setEnabled(true);
        date.setEnabled(true);
        category.setEnabled(true);
        receiptView.setEnabled(true);
        imageButtonDatePicker.setEnabled(true);
        buttonSave.setEnabled(true);

        // Update UI with expense details
        expenseName.setText(expense.getName());
        date.setText(Expense.dateFormat.format(expense.getDate()));

        amount.setText(String.valueOf(expense.getAmount()));
        category.setSelection(expense.getCategoryId());

        if (expense.getReceipt().length() > 0 ) {
            receiptView.setImageURI(Uri.parse(expense.getReceipt()));
            selectedImage = Uri.parse(expense.getReceipt());
        }

    }

    public void saveExpense(View v) {
        // Save the selected expense with new values
        if (selectedExpense >= 0){
            if (allGood()) {

                Date curdate = new Date(1);
                int curamount;
                String curname;
                int categoryId;

                curname = expenseName.getText().toString();
                curamount = Integer.parseInt(amount.getText().toString());
                try {
                    curdate = Expense.dateFormat.parse(date.getText().toString());

                } catch (ParseException exception) {
                    Log.e("demo", "Date Could not be Parsed");
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                categoryId = category.getSelectedItemPosition();

                Expense newExpense;

                if (selectedImage != null) {
                    newExpense = new Expense(curname, categoryId, curamount, curdate, selectedImage.toString());
                } else {
                    newExpense = new Expense(curname, categoryId, curamount, curdate, " ");
                }

                Intent intent = new Intent();
                expenses.set(selectedExpense, newExpense);

                intent.putExtra(MainActivity.EXPENSE_ARRAY_KEY, expenses);
                setResult(1, intent);
                finish();
            }
        } else {
            Toast.makeText(this, getString(R.string.toast_no_expense_selected),Toast.LENGTH_SHORT).show();
        }
    }

    boolean allGood(){
        // Input validation and error generation
        boolean isGood = true;

        if (expenseName.getText().length() <= 0){
            expenseName.setError("Please Enter a Name for this Expense");
            isGood = false;
        }

        if (amount.getText().length() <= 0){
            amount.setError("Please enter an Amount");
            isGood = false;
        }

        if (date.getText().length() <= 0){
            date.setError("Please select a date");
            isGood = false;
        }

        if (category.getSelectedItemPosition() == 0){
            Toast.makeText(this,"Please select a category",Toast.LENGTH_SHORT).show();
            isGood = false;
        }

        return  isGood;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Update the edit Text Field with the date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        Date chosenDate = calendar.getTime();

        date.setText(Expense.dateFormat.format(chosenDate));
    }

    public void getImage(View view){
        // SDK dependent access to the gallery to ensure we get the correct URI for the SDK
        if(Build.VERSION.SDK_INT > 19) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 100);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When the user picks an image store the URI
        if (resultCode == RESULT_OK) {
            selectedImage = data.getData();
            receiptView.setImageURI(selectedImage);
        }
    }

    public void finishActivity(View view){
        // End Activity
        finish();
    }
}
