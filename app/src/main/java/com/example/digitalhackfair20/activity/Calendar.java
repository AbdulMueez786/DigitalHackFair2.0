package com.example.digitalhackfair20.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.adapter.CalendarEventRvAdapter;
import com.example.digitalhackfair20.model.CalendarEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Calendar extends AppCompatActivity {

    private ImageButton previous;
    private ImageButton next;
    private TextView month;
    private Button newEvent;

    private Date currentDate;

    private RecyclerView rv;
    private CalendarEventRvAdapter adapter;

    private List<CalendarEvent> ls;
    private List<CalendarEvent> ls2;

    private CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat(" MMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        rv = findViewById(R.id.rv);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        month = findViewById(R.id.month);
        newEvent = findViewById(R.id.new_event);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        month.setText(dateFormatMonth.format(cal.getTime()));

        final ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);

        ls = new ArrayList<>();
        int colorCode = randomColorCode();


        java.util.Calendar calen = new GregorianCalendar(2021, java.util.Calendar.OCTOBER, 4, 5, 30);
        calen.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);

        ls.add(new CalendarEvent("", "Event1", "This Event1", calen.getTime(), colorCode));
        AddEventToCalendar(calen, colorCode);

        colorCode = randomColorCode();
        calen = new GregorianCalendar(2021, java.util.Calendar.OCTOBER, 10, 2, 20);
        calen.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);
        ls.add(new CalendarEvent("", "Event2", "Hackathon Submission", calen.getTime(), colorCode));
        AddEventToCalendar(calen, colorCode);

        colorCode = randomColorCode();
        calen = new GregorianCalendar(2021, java.util.Calendar.NOVEMBER, 8, 8, 0);
        calen.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
        ls.add(new CalendarEvent("", "Event3", "This Event3", calen.getTime(), colorCode));
        AddEventToCalendar(calen, colorCode);

        ls2 = new ArrayList<CalendarEvent>();

        currentDate = java.util.Calendar.getInstance().getTime();

        ShowOnlySelectedDayEvents();

        // compactCalendar.shouldScrollMonth(false);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                currentDate = dateClicked;

                ShowOnlySelectedDayEvents();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                compactCalendar.scrollLeft();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendar.scrollRight();
            }
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            String name = data.getStringExtra("Name");
                            String description = data.getStringExtra("Description");

                            String AMPM = data.getStringExtra("AMPM");
                            int hr = data.getIntExtra("Hours", 0);
                            int mint = data.getIntExtra("Minutes", 0);
                            int year = data.getIntExtra("Year", 0);
                            int month = data.getIntExtra("Month", 0);
                            int day = data.getIntExtra("Day", 0);

                            //add to event list
                            java.util.Calendar calen = new GregorianCalendar(year, month, day, hr, mint);

                            if (AMPM.equals("AM"))
                                calen.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
                            else
                                calen.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);


                            int cCode = randomColorCode();
                            ls.add(new CalendarEvent("", name, description, calen.getTime(), cCode));
                            String key = FirebaseDatabase.getInstance().getReference("CalendarEvent").push().getKey();

                            add(new CalendarEvent(key, name, description, calen.getTime(), cCode));

                            adapter.notifyDataSetChanged();

                            AddEventToCalendar(calen, cCode);


                        }

                    }
                });

        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Calendar.this, CreateEvent.class);
                someActivityResultLauncher.launch(i);
            }
        });
    }

    public void openActivityForResult() {
        Intent i = new Intent(Calendar.this, CreateEvent.class);
    }

    public void AddEventToCalendar(java.util.Calendar calen, int colorCode) {

        //convert date to milliseconds
        long millis = calen.getTimeInMillis();
        Log.d("Milli", String.valueOf(millis));

        //add an event showing dot on calendar
        Event ev1 = new Event(colorCode, millis, "Eventt");
        compactCalendar.addEvent(ev1);

    }

    public int randomColorCode() {
        //add colors to an array
        List<Integer> colorList = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLACK);
        Random rand = new Random();
        int index = rand.nextInt(6);
        return colorList.get(index);
    }

    private void add(CalendarEvent e) {
        FirebaseDatabase.getInstance().getReference("CalendarEvent")
                .child(e.getId()).setValue(e).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                // exception handled
            }
        });

    }

    private void read() {
        ls.clear();
        FirebaseDatabase.getInstance().getReference("CalendarEvent")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        CalendarEvent c = snapshot.getValue(CalendarEvent.class);
                        ls.add(c);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }


    private void ShowOnlySelectedDayEvents() {
        ls2 = new ArrayList<CalendarEvent>();
//        have only current day events in list
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM");
        SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
        for (int i = 0; i < ls.size(); i++) {
            java.util.Calendar cal2 = new GregorianCalendar();
            cal2.setTime(currentDate);
            int date = Integer.parseInt(formatter.format(cal2.getTime()));
            int month = Integer.parseInt(formatter2.format(cal2.getTime()));
            int year = Integer.parseInt(formatter3.format(cal2.getTime()));

            java.util.Calendar cal3 = new GregorianCalendar();
            cal3.setTime(ls.get(i).getTimeAndDate());
            int date2 = Integer.parseInt(formatter.format(cal3.getTime()));
            int month2 = Integer.parseInt(formatter2.format(cal3.getTime()));
            int year2 = Integer.parseInt(formatter3.format(cal3.getTime()));

            if (date == date2) {
                if (month == month2) {
                    if (year == year2) {
                        ls2.add(ls.get(i));
                        Toast.makeText(this, "Working", Toast.LENGTH_LONG);
                    } else
                        Log.d("Year", String.valueOf(year + " - " + year2));
                } else
                    Log.d("Month", String.valueOf(month + " - " + month2));
            } else
                Log.d("Date", String.valueOf(date + " - " + date2));
        }
        adapter = new CalendarEventRvAdapter(ls2, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}