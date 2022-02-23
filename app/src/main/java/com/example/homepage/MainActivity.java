package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private CardView todolistCardView, calendarCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);


        todolistCardView = findViewById(R.id.todoTaskCardView);
        calendarCardView = findViewById(R.id.calendarCardView);

        todolistCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ToDoList.class));
            }
        });

        calendarCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
            }
        });
    }
}