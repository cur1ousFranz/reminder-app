package com.example.homepage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmClock extends AppCompatActivity {

    private Dialog dialog;
    private ImageView addAlarmButton, alarmIcon;
    private ArrayList<AlarmClockModel> alarmClockModels;
    private DatabaseHelper databaseHelper;
    private ListView alarmListView;
    private ArrayAdapter adapter;
    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        initView();
        buttonClick();
        readAllAlarms();

    }

    private void initView() {

        addAlarmButton = findViewById(R.id.addAlarmButton);
        dialog = new Dialog(this);
        databaseHelper = new DatabaseHelper(AlarmClock.this);
        alarmListView = findViewById(R.id.alarmListView);
        alarmIcon = findViewById(R.id.alarmIconShow);
        alarmTextView = findViewById(R.id.alarmTextView);


    }

    private void buttonClick() {

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                addAlarmDialog();


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addAlarmDialog() {

        dialog.setContentView(R.layout.add_alarm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button confirmButton = dialog.findViewById(R.id.addAlarmConfirmButton);
        Button cancelButton = dialog.findViewById(R.id.addAlarmCancelButton);
        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hour, minute;
                if (Build.VERSION.SDK_INT >= 23){
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                if (hour != 0 && minute != 0){
                    Intent intent = new Intent(android.provider.AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(android.provider.AlarmClock.EXTRA_HOUR, hour);
                    intent.putExtra(android.provider.AlarmClock.EXTRA_MINUTES, minute);
                    intent.putExtra(android.provider.AlarmClock.EXTRA_MESSAGE, "Set alarm for morning walk");

                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        dialog.dismiss();
                        addAlarm(hour, minute);
                        readAllAlarms();

                    }else
                        Toast.makeText(AlarmClock.this, "There is no app that support this action", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AlarmClock.this, "Please input a time you want", Toast.LENGTH_LONG).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addAlarm(int hour, int minute) {

        databaseHelper.addOneAlarm(new AlarmClockModel(-1,hour, minute));
        readAllAlarms();
    }

    private void readAllAlarms() {

        alarmClockModels = new ArrayList<>(databaseHelper.alarmList());
        adapter = new ArrayAdapter<AlarmClockModel>(AlarmClock.this, android.R.layout.simple_list_item_1, alarmClockModels);
        if (adapter.getCount() != 0){
            alarmTextView.setVisibility(View.GONE);
            alarmIcon.setVisibility(View.GONE);
            alarmListView.setAdapter(adapter);
        }
        else{
            alarmTextView.setVisibility(View.VISIBLE);
            alarmIcon.setVisibility(View.VISIBLE);
        }


    }
}