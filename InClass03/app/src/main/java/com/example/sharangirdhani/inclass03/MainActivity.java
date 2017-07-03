package com.example.sharangirdhani.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final static String KEY_STUDENT = "STUDENT";
    final static String VALUE_KEY = "value";
    public static String KEY_DEPARTMENT = "DEPARTMENT";
    public static String KEY_MOOD = "MOOD";

    EditText nameValue;
    EditText emailValue;
    RadioGroup rg;
    SeekBar sb;

    String currentName;
    String currentEmail;
    String currentDepartment;
    String currentMood;

    public String getDepartmentNameById(int id) {
        switch(id) {
            case R.id.radio_dep_1:
                return "SIS";
            case R.id.radio_dep_2:
                return "CS";
            case R.id.radio_dep_3:
                return "BIO";
            case R.id.radio_dep_4:
                return "Others";
        }
        return null;
    }

    public String getMoodStringByValue(int value) {
        return (value + "% Positive");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameValue = (EditText) findViewById(R.id.value_name);
        currentName = "";

        emailValue =(EditText) findViewById(R.id.value_email);
        currentEmail = "";

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        currentDepartment = getResources().getText(R.string.dep_1).toString();

        sb = (SeekBar) findViewById(R.id.value_mood);
        sb.setProgress(0);
        currentMood = "0 % Positive";

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = nameValue.getText().toString();
                String curEmail = emailValue.getText().toString();

                if(curName == null || curName.length() == 0 || curEmail == null || curEmail.length() == 0) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.toast_error_1).toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                currentName = curName;
                currentEmail = curEmail;

                int checkedId = rg.getCheckedRadioButtonId();
                int progress = sb.getProgress();
                currentDepartment = getDepartmentNameById(checkedId);
                currentMood = getMoodStringByValue(progress);

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                Student student = new Student(currentName, currentEmail, currentDepartment, currentMood);

                intent.putExtra(KEY_STUDENT, student);
                intent.putExtra(KEY_DEPARTMENT, checkedId);
                intent.putExtra(KEY_MOOD, progress);
                startActivity(intent);
            }
        });


    }
}
