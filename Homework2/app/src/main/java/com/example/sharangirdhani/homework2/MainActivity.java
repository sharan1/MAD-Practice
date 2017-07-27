package com.example.sharangirdhani.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXPENSE_ARRAY_KEY = "EXPENSEARRAY";
    public static final String EXPENSE_KEY = "EXPENSE";
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DELETE = 2;
    public static final int REQUEST_EDIT = 3;

    public ArrayList<Expense> AllExpenses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show launcher logo on the ActionBar
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);


        findViewById(R.id.btnAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddExpenseActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        findViewById(R.id.btnEditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditExpenseActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,AllExpenses);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });

        findViewById(R.id.btnDeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DeleteExpenseActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,AllExpenses);
                startActivityForResult(intent,REQUEST_DELETE);
            }
        });

        findViewById(R.id.btnShowExpenses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowExpensesActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,AllExpenses);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle Activity Results
        if (data != null && requestCode == REQUEST_ADD){
            Toast.makeText(this,getResources().getString(R.string.toast_expense_added_label),Toast.LENGTH_SHORT).show();
            AllExpenses.add((Expense) data.getSerializableExtra(EXPENSE_KEY));
        } else if (data != null && requestCode == REQUEST_DELETE){
            Toast.makeText(this,getResources().getString(R.string.toast_expense_deleted_label),Toast.LENGTH_SHORT).show();
            AllExpenses = (ArrayList<Expense>) data.getSerializableExtra(EXPENSE_ARRAY_KEY);
        } else if (data != null && requestCode == REQUEST_EDIT){
            Toast.makeText(this,getResources().getString(R.string.toast_expense_updated_label),Toast.LENGTH_SHORT).show();
            AllExpenses = (ArrayList<Expense>) data.getSerializableExtra(EXPENSE_ARRAY_KEY);
        }
    }
}
