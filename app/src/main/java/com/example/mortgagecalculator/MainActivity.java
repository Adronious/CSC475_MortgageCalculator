package com.example.mortgagecalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // field to hold values entered by user for calculation
    EditText purchasePrice, downPayment, interestRate;

    // field to hold monthly payment value
    TextView monthlyPaymentResult;
    SeekBar seekBar;
    TextView customMonthSlider;

    // button to initiate calculation
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link each value entered by the user to the xml
        purchasePrice = (EditText) findViewById(R.id.purchasePriceField);
        downPayment = (EditText) findViewById(R.id.downPaymentField);
        interestRate = (EditText) findViewById(R.id.interestPaymentField);

        // link variables to references in xml
        monthlyPaymentResult = findViewById(R.id.monthlyPaymentDisplay);

        // link seek bar and corresponding text display for progress
        seekBar = findViewById(R.id.seekBar);
        customMonthSlider = findViewById(R.id.customMonthSlider);

        // change text box to display number selected by slider
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customMonthSlider.setText(progress+" Years");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void calculateMonthlyPayment(View v) {

        // format decimal to round to hundreds
        DecimalFormat df = new DecimalFormat(".##");

        // read each integer input as a string and parse to integer and float
        int price = Integer.parseInt(purchasePrice.getText().toString());
        int payment = Integer.parseInt(downPayment.getText().toString());
        float rate = Float.parseFloat(interestRate.getText().toString());

        // algorithm to calculate interest and monthly price
        int subTotalPrice = price - payment;
        float interest = subTotalPrice * (rate/100);
        float totalPrice = subTotalPrice + interest;
        float years10 = totalPrice / 120;
        float years20 = totalPrice / 240;
        float years30 = totalPrice / 360;

        // algorithm for custom year count on seek bar
        int seekBarYears = seekBar.getProgress();
        float customYears = totalPrice / (seekBarYears*12);

        // print results to the screen
        monthlyPaymentResult.setText("Your total cost is: " + df.format(totalPrice) + "\n" +
                                    "Your monthly rate for 10 years: " + df.format(years10) + "\n" +
                                    "Your monthly rate for 20 years: " + df.format(years20) + "\n" +
                                    "Your monthly rate for 30 years: " + df.format(years30) + "\n" +
                                    "Your monthly rate for "+ seekBarYears +" years: "+ df.format(customYears));

        // automatically close they keyboard onClick so results are visible
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }

}