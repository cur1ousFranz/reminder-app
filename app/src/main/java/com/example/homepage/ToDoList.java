package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ToDoList extends AppCompatActivity {

    private ArrayList<TodoListModel> todoListModels;

    public ListView listView;
    private Button addButton;
    private DatabaseHelper databaseHelper;
    private Dialog dialog;
    private TextView noTaskTextView;

    @SuppressLint("StaticFieldLeak")
    public static ToDoList toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        //Calling all the methods below
        initView();
        buttonCLick();
        readAllTasks();

    }

    public ToDoList getInstance() {
        return toDoList;
    }

    /**
     * Setting an onlicklistener to all buttons
     */
    private void buttonCLick() {
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openTaskDialog();
            }
        });
    }

    /**
     * Creating a method of Dialog, when the user wants to
     * add task, this dialog must showed up for the fill up process.
     * <p>
     * Called from the buttonClick method.
     */
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
                if (!text.isEmpty()) {
                    dialog.dismiss();
                    applyText(text); //calling the apply text method below
                    Toast.makeText(ToDoList.this, "Task added", Toast.LENGTH_LONG).show();
                } else {
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

        todoListModels = new ArrayList<>(databaseHelper.taskLists());
        TodoListAdapter adapter = new TodoListAdapter(getApplicationContext(),
                R.layout.task_list_button, todoListModels);
        listView.setAdapter(adapter);

        //Showing a message "Currently No Task" if there is no task in the list
        if (listView.getAdapter().getCount() == 0){
            noTaskTextView.setVisibility(View.VISIBLE);
        }else
            noTaskTextView.setVisibility(View.GONE);
    }

    /**
     * Initializing objects
     */
    private void initView() {
        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        databaseHelper = new DatabaseHelper(ToDoList.this);
        dialog = new Dialog(this);
        todoListModels = new ArrayList<>();
        toDoList = this;
        noTaskTextView = findViewById(R.id.noTaskTextView);

    }

    /**
     * Adding another task to database be calling the addOneTask method
     * from the DatabaseHelper.
     *
     * @param taskName
     */
    public void applyText(String taskName) {

        databaseHelper.addOneTask(new TodoListModel(-1, taskName));
        readAllTasks();
    }

    public void deleteDialog(TodoListModel todoListModel) {

        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button confirmDialog = dialog.findViewById(R.id.confirmDialogButton);
        Button cancelDialog = dialog.findViewById(R.id.cancelDialogButton);

        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());

                databaseHelper.deleteOneTask(todoListModel);
                readAllTasks();
                dialog.dismiss();
                Toast.makeText(ToDoList.this, "Task deleted", Toast.LENGTH_LONG).show();

            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}