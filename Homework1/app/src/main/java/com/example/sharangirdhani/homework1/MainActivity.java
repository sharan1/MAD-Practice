package com.example.sharangirdhani.homework1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    SeekBar sb;
    TextView bacLevel;
    TextView statusValue;
    TextView alcoholPercentage;
    EditText weight;
    Switch gender;
    RadioGroup rb;
    ProgressBar progressStatus;
    Button buttonSave;
    Button buttonAddDrink;
    Button buttonReset;


    boolean isFemale;
    int currentWeightValue;
    int currentDrinkSize;
    int currentAlcoholPercentage;
    double currentBACLevel;
    double currentGenderValue;
    double currentProgressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show launcher logo on the ActionBar
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon);
        getSupportActionBar().setIcon(R.drawable.icon);

        sb = (SeekBar) findViewById(R.id.sliderAlcoholValue);
        bacLevel = (TextView) findViewById(R.id.bacValue);
        statusValue = (TextView) findViewById(R.id.statusValue);
        alcoholPercentage = (TextView) findViewById(R.id.curAlcoholValue);
        weight = (EditText) findViewById(R.id.weightValue);
        gender = (Switch) findViewById(R.id.switchGender);
        rb = (RadioGroup) findViewById(R.id.radioGroup);
        progressStatus = (ProgressBar) findViewById(R.id.progressStatus);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonAddDrink = (Button) findViewById(R.id.buttonAddDrink);
        buttonReset = (Button) findViewById(R.id.buttonReset);


        sb.setMax(5);
        sb.setProgress(1);
        gender.setChecked(true);
        progressStatus.setMax(25);
        progressStatus.setProgress(0);

        currentGenderValue = 0.68;
        currentBACLevel = 0.00;
        currentAlcoholPercentage = 5;
        currentDrinkSize = 1;
        currentWeightValue = -1;
        isFemale = false;
        currentProgressStatus = 0.0;

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alcoholPercentage.setText(progress*5 + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateEntireData() {
        currentBACLevel = Math.round(currentBACLevel*100.0)/100.0;
        bacLevel.setText(String.valueOf(currentBACLevel));
        currentProgressStatus = currentBACLevel*100;

        if(currentBACLevel <= 0.08) {
            progressStatus.setProgress((int) currentProgressStatus);
            statusValue.setText("You're Safe");
            statusValue.setBackgroundColor(getResources().getColor(R.color.colorSafe));
        } else if(currentBACLevel < 0.20) {
            progressStatus.setProgress((int) currentProgressStatus);
            statusValue.setText("Be careful...");
            statusValue.setBackgroundColor(getResources().getColor(R.color.colorCareful));
        } else {
            statusValue.setText("Over the limit!");
            statusValue.setBackgroundColor(getResources().getColor(R.color.colorLimit));
            if(currentBACLevel >= 0.25) {
                progressStatus.setProgress(25);
                buttonSave.setEnabled(false);
                buttonAddDrink.setEnabled(false);

                Toast.makeText(this,getString(R.string.toast_limit_reached),Toast.LENGTH_SHORT).show();

            } else {
                progressStatus.setProgress((int) currentProgressStatus);
            }
        }
        return;
    }

    public void saveOnClick(View v) {
        //Toast.makeText(this, "This Came here", Toast.LENGTH_SHORT).show();
        if(weight.getText().toString().isEmpty())
        {
            weight.setError("Enter the weight in lbs.");
            return;
        }
        int newWeight = Integer.parseInt(weight.getText().toString());
        if(newWeight == 0) {
            weight.setError("Weight cannot be zero. Enter a positive Integer");
            return;
        }

        currentBACLevel *= (currentWeightValue*currentGenderValue);
        currentWeightValue = newWeight;

        isFemale = !gender.isChecked();
        if(isFemale) {
            currentGenderValue = 0.55;
        }
        else {
            currentGenderValue = 0.68;
        }

        currentBACLevel /= (currentWeightValue*currentGenderValue);
        updateEntireData();
        return;
    }

    public void addDrinkOnClick(View v) {
        if(weight.getText().toString().isEmpty())
        {
            weight.setError("Enter the weight in lbs.");
            return;
        } else if(currentWeightValue == -1) {
            weight.setError("You need to save weight value first");
            return;
        }

        int checkedButtonId = rb.getCheckedRadioButtonId();

        switch (checkedButtonId) {
            case R.id.radio1oz:
                currentDrinkSize = 1;
                break;
            case R.id.radio5oz:
                currentDrinkSize = 5;
                break;
            case R.id.radio12oz:
                currentDrinkSize = 12;
                break;
        }

        currentAlcoholPercentage = sb.getProgress()*5;

        currentBACLevel += (currentDrinkSize*currentAlcoholPercentage*6.24)/(100.0*currentWeightValue*currentGenderValue);
        updateEntireData();
        return;
    }

    public void resetOnClick(View v) {
        buttonAddDrink.setEnabled(true);
        buttonSave.setEnabled(true);
        weight.setText("");
        weight.setHint(R.string.weight_hint);
        gender.setChecked(true);
        rb.check(R.id.radio1oz);
        sb.setProgress(1);
        alcoholPercentage.setText("5%");
        bacLevel.setText("0.00");
        progressStatus.setProgress(0);
        statusValue.setText("You're Safe");
        statusValue.setBackgroundColor(getResources().getColor(R.color.colorSafe));

        currentGenderValue = 0.68;
        currentBACLevel = 0.00;
        currentAlcoholPercentage = 5;
        currentDrinkSize = 1;
        currentWeightValue = -1;
        isFemale = false;
        currentProgressStatus = 0.0;
    }
}
