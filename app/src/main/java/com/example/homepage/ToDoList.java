package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Objects;

public class ToDoList extends AppCompatActivity {

    private ListView listView;
    private Button addButton;

    private DatabaseHelper databaseHelper;
    private ArrayAdapter arrayAdapter;

    public static ToDoList toDoList;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_to_do_list);

        toDoList = this;

        initView();
        buttonCLick();
        readAllTasks();
        deleteTask();

    }

    public ToDoList getInstance(){
        return toDoList;
    }

    /**
     * Setting an onlicklistener to add button task
     */
    private void buttonCLick() {
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openTaskDialog();
            }
        });
    }

    private void openTaskDialog() {

        dialog.setContentView(R.layout.task_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button confirmDialog = dialog.findViewById(R.id.todoListConfirmButton);
        ImageView imageClose = dialog.findViewById(R.id.imageClose);

        EditText editText = dialog.findViewById(R.id.taskName);

        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editText.getText().toString();
                if (!text.isEmpty()){
                    dialog.dismiss();
                    applyText(text);
                    Toast.makeText(ToDoList.this, "Task added", Toast.LENGTH_LONG ).show();
                }else{
                    editText.setError("Please add task");
                    editText.requestFocus();
                }
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Reading all task from the to_do_task table and add it in list view
     */
    public void readAllTasks() {
        arrayAdapter= new ArrayAdapter(ToDoList.this, android.R.layout.simple_list_item_1, databaseHelper.taskLists());
        listView.setAdapter(arrayAdapter);
    }

    /**
     * Initializing my view components
     */
    private void initView() {
        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);

        databaseHelper = new DatabaseHelper(ToDoList.this);

        dialog = new Dialog(this);

    }

    public void applyText(String taskName){

        databaseHelper.addOneTask(new TaskListModel(-1, taskName));

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, databaseHelper.taskLists());
        listView.setAdapter(arrayAdapter);
    }

    private void deleteTask() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TaskListModel taskList = (TaskListModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOneTask(taskList);
                readAllTasks();
            }
        });
    }
}