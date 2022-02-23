package com.example.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Calendar extends AppCompatActivity {

    private ListView cardListView;
    private CalendarView calendarView;
    private Button addDateButton;
    private DatabaseHelper databaseHelper;

    private TextView textView2;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_calendar);

        initView();
        readAllDateTask();
        buttonClick();

    }

    private void initView(){
        cardListView = findViewById(R.id.cardLisView);
        calendarView = findViewById(R.id.calendarView);
        addDateButton = findViewById(R.id.button);
        textView2 = findViewById(R.id.textView2);

        databaseHelper = new DatabaseHelper(Calendar.this);

        dialog = new Dialog(this);
    }

    private void buttonClick(){

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                textView2.setText(date);
            }
        });

        addDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addDate();
            }
        });
    }

    private void addDate() {

        dialog.setContentView(R.layout.date_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button dateConfirmButton = dialog.findViewById(R.id.dateTaskConfirmButton);
        EditText dateName = dialog.findViewById(R.id.dateName);
        ImageView closeImageButton = dialog.findViewById(R.id.imageClose2);

        dateConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dateName.getText().toString().isEmpty()){

                    if (!textView2.getText().toString().equals("Saved Dates")){

                        if (dateName.getText().length() <= 20){
                            dialog.dismiss();
                            databaseHelper.addOneTask(new DateListModel(-1, dateName.getText().toString(), textView2.getText().toString()));
                            textView2.setText("Saved Dates");
                            Toast.makeText(Calendar.this, "Date added", Toast.LENGTH_LONG).show();
                            readAllDateTask();
                        }
                        else
                            dateName.setError("Please input 20 characters only!");
                            dateName.requestFocus();

                    }else
                        Toast.makeText(Calendar.this, "Please select specific date in calendar", Toast.LENGTH_LONG).show();
                }
                else {
                    dateName.setError("Please enter date name");
                    dateName.requestFocus();
                }
            }
        });

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                textView2.setText("Saved Dates");
            }
        });


        dialog.show();
    }

    private void readAllDateTask() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(Calendar.this, android.R.layout.simple_list_item_1, databaseHelper.dateList());
        cardListView.setAdapter(arrayAdapter);
    }
}