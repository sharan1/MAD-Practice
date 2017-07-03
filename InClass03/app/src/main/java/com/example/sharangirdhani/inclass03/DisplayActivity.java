package com.example.sharangirdhani.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public static final int REQ_CODE1 = 100;
    public static final int REQ_CODE2 = 200;
    public static final int REQ_CODE3 = 300;
    public static final int REQ_CODE4 = 400;

    final static String VAR = "VARIABLE";
    final static String NAMEID = "Name";
    final static String EMAILID = "Email";
    final static String DEPID = "Dept";
    final static String MOODID = "Mood";

    TextView text_name;
    TextView text_email;
    TextView text_department;
    TextView text_mood;

    int departmentId;
    int moodId;
    String nameId;
    String emailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        text_name = (TextView) findViewById(R.id.text_name);
        text_email = (TextView) findViewById(R.id.text_email);
        text_department = (TextView) findViewById(R.id.text_department);
        text_mood = (TextView) findViewById(R.id.text_mood);

        departmentId = -1;
        moodId = -1;
        nameId = "";
        emailId = "";

        if(getIntent().getExtras() != null) {
            Student student = (Student) getIntent().getExtras().getSerializable(MainActivity.KEY_STUDENT);
            text_name.setText(student.name);
            text_email.setText(student.email);
            text_department.setText(student.department);
            text_mood.setText(student.mood);

            departmentId = getIntent().getExtras().getInt(MainActivity.KEY_DEPARTMENT);
            moodId = getIntent().getExtras().getInt(MainActivity.KEY_MOOD);
            nameId = student.name;
            emailId = student.email;
        }

        findViewById(R.id.imageEdit_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.sharangirdhani.inclass03.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(VAR, "NAME");
                intent.putExtra(NAMEID, nameId);
                startActivityForResult(intent, REQ_CODE1);
            }
        });

        findViewById(R.id.imageEdit_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.sharangirdhani.inclass03.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(VAR, "EMAIL");
                intent.putExtra(EMAILID, emailId);
                startActivityForResult(intent, REQ_CODE2);
            }
        });

        findViewById(R.id.imageEdit_department).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.sharangirdhani.inclass03.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(VAR, "DEPARTMENT");
                intent.putExtra(DEPID, departmentId);
                startActivityForResult(intent, REQ_CODE3);
            }
        });

        findViewById(R.id.imageEdit_mood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.sharangirdhani.inclass03.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(VAR, "MOOD");
                intent.putExtra(MOODID, moodId);

                startActivityForResult(intent, REQ_CODE4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String value = data.getExtras().getString(MainActivity.VALUE_KEY);

            if(requestCode == REQ_CODE1) {
                nameId = value;
                text_name.setText(value);
            }
            else if(requestCode == REQ_CODE2) {
                emailId = value;
                text_email.setText(value);
            }
            else if(requestCode == REQ_CODE3) {
                departmentId = data.getExtras().getInt(EditActivity.NEW_DEPTID);
                text_department.setText(value);
            }
            else if(requestCode == REQ_CODE4) {
                moodId = data.getExtras().getInt(EditActivity.NEW_MOODID);
                text_mood.setText(value);
            }
        }
        else {
            Log.d("Demo", "No Value Recieved");
        }
    }
}
