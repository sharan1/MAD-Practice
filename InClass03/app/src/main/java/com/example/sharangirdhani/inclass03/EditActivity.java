package com.example.sharangirdhani.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    public static final String NEW_DEPTID = "NEW_DEPT";
    public static final String NEW_MOODID = "NEW_MOOD";

    EditText nameValue;
    EditText emailValue;
    TextView dep_text;
    RadioGroup rg;
    TextView mood_text;
    SeekBar sb;

    String Var;

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
        setContentView(R.layout.activity_edit);

        nameValue = (EditText) findViewById(R.id.value_name);
        nameValue.setVisibility(View.INVISIBLE);
        emailValue =(EditText) findViewById(R.id.value_email);
        emailValue.setVisibility(View.INVISIBLE);

        dep_text = (TextView) findViewById(R.id.text_department);
        dep_text.setVisibility(View.INVISIBLE);

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setVisibility(View.INVISIBLE);

        mood_text = (TextView) findViewById(R.id.text_mood);
        mood_text.setVisibility(View.INVISIBLE);

        sb = (SeekBar) findViewById(R.id.value_mood);
        sb.setVisibility(View.INVISIBLE);

        if(getIntent().getExtras() != null) {
            Var = getIntent().getExtras().getString(DisplayActivity.VAR);

            switch(Var) {
                case "NAME":
                    String value = getIntent().getExtras().getString(DisplayActivity.NAMEID);
                    nameValue.setVisibility(View.VISIBLE);
                    nameValue.setText(value);
                    break;
                case "EMAIL":
                    String val2 = getIntent().getExtras().getString(DisplayActivity.EMAILID);
                    emailValue.setVisibility(View.VISIBLE);
                    emailValue.setText(val2);
                    break;
                case "DEPARTMENT":
                    int val = getIntent().getExtras().getInt(DisplayActivity.DEPID);
                    dep_text.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.VISIBLE);
                    rg.check(val);
                    break;
                case "MOOD":
                    int progress = getIntent().getExtras().getInt(DisplayActivity.MOODID);
                    mood_text.setVisibility(View.VISIBLE);
                    sb.setVisibility(View.VISIBLE);
                    sb.setProgress(progress);
                    break;
            }
        }

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras() != null) {
                    //String Var = getIntent().getExtras().getString(DisplayActivity.VAR);
                    String value = null;
                    Intent intent = new Intent();

                    int checkedId = -1;
                    int progress = -1;
                    switch(Var) {
                        case "NAME":
                            value = nameValue.getText().toString();
                            break;
                        case "EMAIL":
                            value = emailValue.getText().toString();
                            break;
                        case "DEPARTMENT":
                            checkedId = rg.getCheckedRadioButtonId();
                            value = getDepartmentNameById(checkedId);
                            break;
                        case "MOOD":
                            progress = sb.getProgress();
                            value = getMoodStringByValue(progress);
                            break;
                    }

                    if(value == null || value.length() == 0) {
                        setResult(RESULT_CANCELED);
                    }
                    else {
                        intent.putExtra(MainActivity.VALUE_KEY, value);
                        if(checkedId != -1) {
                            intent.putExtra(NEW_DEPTID,checkedId);
                        }

                        if(progress != -1) {
                            intent.putExtra(NEW_MOODID,progress);
                        }
                        setResult(RESULT_OK, intent);
                    }
                }
                finish();
            }
        });


    }
}
