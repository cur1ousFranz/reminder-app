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

public class CalendarListAdapter extends ArrayAdapter<CalendarListModel> {
    private ArrayList<CalendarListModel> calendarListModels;

    public CalendarListAdapter(@NonNull Context context, int resource,
                               ArrayList<CalendarListModel> calendarListModels) {

        super(context, resource, calendarListModels);
        this.calendarListModels = calendarListModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.date_list_button,
                    parent, false);
        }

        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        TextView dateText = convertView.findViewById(R.id.dateText);
        ImageView deleteDateButton = convertView.findViewById(R.id.deleteDateButton);

        textViewDate.setText(calendarListModels.get(position).getDate_name());
        dateText.setText(calendarListModels.get(position).getDate_text());

        deleteDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = new Calendar();
                CalendarListModel calendarListModel = (CalendarListModel) calendar.getInstance().cardListView.getItemAtPosition(position);
                calendar.getInstance().deleteCalendarDialog(calendarListModel);


            }
        });



    return convertView;
    }
}
