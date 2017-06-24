package com.example.sharangirdhani.inclass02b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText length1 ;
    EditText length2 ;
    TextView TVArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TVArea = (TextView) findViewById(R.id.areaValue);
        length1 = (EditText) findViewById(R.id.length1);
        length2 = (EditText) findViewById(R.id.length2);
    }

    @Override
    public void onClick(View v) {
        RadioGroup rb = (RadioGroup) findViewById(R.id.radioGroup);

        int checkedButtonId = rb.getCheckedRadioButtonId();

        if(checkedButtonId == R.id.radioClear) {
            length1.setText("");
            length2.setText("");
            length1.setHint(" Enter Length ");
            length2.setHint(" Enter Length ");
            TVArea.setText("");
            return;
        }

        double l1 = -1.0, l2 = -1.0;
        if(!length1.getText().toString().isEmpty()) {
            l1 = Double.parseDouble(length1.getText().toString());
        }
        else {
            Toast.makeText(MainActivity.this, "Length 1 cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!length2.getText().toString().isEmpty())
            l2 = Double.parseDouble(length2.getText().toString());

        double area = 0.0;
        switch (checkedButtonId)
        {
            case R.id.radioTriangle:
                if(l2 != -1.0) {
                    area = 0.5 * l1 * l2;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Length 2 cannot be empty here", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.radioSquare:
                area = l1*l1;
                length2.setText("");
                length2.setHint("");
                break;
            case R.id.radioCircle:
                area = 3.14 * l1* l1;
                length2.setText("");
                length2.setHint("");
                break;
            case R.id.radioRect:
                if(l2 != -1.0)
                    area = l1 * l2;
                else
                {
                    Toast.makeText(MainActivity.this, "Length 2 cannot be empty here", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
        TVArea.setText(String.valueOf(area));
    }
}
