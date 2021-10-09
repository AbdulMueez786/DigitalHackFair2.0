package com.example.digitalhackfair20.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalhackfair20.R;

import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    NumberPicker numPickerMin;
    NumberPicker numPickerHrs;
    NumberPicker numPickerAMPM;

    EditText name;
    EditText description;
    Button createEvent;
    ImageButton date_picker;

    int year;
    int month;
    int day;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        name = findViewById(R.id.new_event_name);
        description = findViewById(R.id.new_event_description);
        createEvent = findViewById(R.id.create_event);
        date_picker = (ImageButton) findViewById(R.id.date_picker);

        numPickerMin = findViewById(R.id.numPickerMin);
        numPickerHrs = findViewById(R.id.numPickerHrs);
        numPickerAMPM = (NumberPicker) findViewById(R.id.numPickerAMPM);

        //setting up number picker
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);
        numPickerHrs.setMinValue(1);
        numPickerHrs.setMaxValue(12);

        //for am and pm
        final String[] values = {"AM", "PM"};
        numPickerAMPM.setMinValue(0);
        numPickerAMPM.setMaxValue(values.length - 1);
        numPickerAMPM.setWrapSelectorWheel(true);
        numPickerAMPM.setDisplayedValues(values);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(CreateEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                month = m;
                year = y;
                day = d;
            }
        };

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                String s = name.getText().toString();
                i.putExtra("Name", s);
                s = description.getText().toString();
                i.putExtra("Description", s);
                int d = numPickerMin.getValue();
                i.putExtra("Minutes", d);
                d = numPickerHrs.getValue();
                i.putExtra("Hours", d);
                s = values[numPickerAMPM.getValue()];
                i.putExtra("AMPM", s);

                //put the date in
                d = day;
                i.putExtra("Day", d);
                d = month;
                i.putExtra("Month", d);
                d = year;
                i.putExtra("Year", d);

                setResult(RESULT_OK, i);

                finish();

            }
        });
        
    }
}