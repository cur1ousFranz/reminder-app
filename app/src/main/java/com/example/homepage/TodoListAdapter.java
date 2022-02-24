package com.example.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoListAdapter extends ArrayAdapter<TodoListModel> {

    private ArrayList<TodoListModel> todoListModels;

    public TodoListAdapter(@NonNull Context context, int resource, ArrayList<TodoListModel> todoListModels) {
        super(context, resource, todoListModels);
        this.todoListModels = todoListModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_button,
                    parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewList);
        ImageView deleteButton = convertView.findViewById(R.id.deleteButton);

        textView.setText(todoListModels.get(position).getTaskName());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToDoList toDoList = new ToDoList();
                TodoListModel todoListModel = (TodoListModel) toDoList.getInstance().listView.getItemAtPosition(position);
                toDoList.getInstance().deleteDialog(todoListModel);

            }
        });
        return convertView;
    }

}
