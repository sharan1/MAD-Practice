package com.example.sharangirdhani.homework2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText expenseName;
    private Spinner category;
    private EditText amount;
    private EditText date;
    private ImageButton imageDatePicker;
    private ImageView reciept;

    String currentExpenseName;
    int currentCategoryId;
    int currentAmount;
    Date currentDate;
    Uri currentReciept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseName = (EditText) findViewById(R.id.etExpenseName);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        amount = (EditText) findViewById(R.id.etAmount);
        date = (EditText) findViewById(R.id.etDate);
        imageDatePicker = (ImageButton) findViewById(R.id.imageCalender);
        reciept = (ImageView) findViewById(R.id.imageReciept);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Expense.categories);
        category.setAdapter(spinnerAdapter);

        Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        imageDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        Date chosenDate = calendar.getTime();

        date.setText(Expense.dateFormat.format(chosenDate));

        date.setError(null);
    }

    boolean allGood(){
        boolean isGood = true;

        // Input validitation
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

    public void addExpense(View view) throws ParseException {
        if (allGood()) {

            currentDate = new Date(0);

            // Get user input from UI elements
            currentExpenseName = expenseName.getText().toString();
            currentAmount = Integer.parseInt(amount.getText().toString());
            // Try to get the date from the text field
            try{
                currentDate = Expense.dateFormat.parse(date.getText().toString());
            }
            catch (ParseException exception) {
                Log.e("demo","Date Could not be Parsed");
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            currentCategoryId = category.getSelectedItemPosition();

            Expense newExpense;

            if (currentReciept != null) {
                newExpense = new Expense(currentExpenseName, currentCategoryId, currentAmount, currentDate, currentReciept.toString());
            } else {
                newExpense = new Expense(currentExpenseName, currentCategoryId, currentAmount, currentDate, "");
            }

            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXPENSE_KEY, newExpense);
            setResult(1, intent);
            finish();
        }
    }

    public void getImage(View view){
        // Use an SDK dependent code to handle Image access from gallery
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
        // Check if user chose an image
        if (resultCode == RESULT_OK) {
            // If yes store the imageURI
            currentReciept = data.getData();
            reciept.setImageURI(currentReciept);
        }
    }
}
