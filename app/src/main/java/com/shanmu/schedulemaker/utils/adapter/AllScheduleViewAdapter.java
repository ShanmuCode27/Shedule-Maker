package com.shanmu.schedulemaker.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanmu.schedulemaker.R;
import com.shanmu.schedulemaker.models.DateTimeslotAndGoal;

import java.util.List;

public class AllScheduleViewAdapter extends ArrayAdapter<DateTimeslotAndGoal> {

    public AllScheduleViewAdapter(Context context, List<DateTimeslotAndGoal> dateTimeslotAndGoalList) {
        super(context, 0, dateTimeslotAndGoalList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DateTimeslotAndGoal dateTimeslotAndGoal = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.all_schedule_items, parent, false);
        }
        TextView dateText = convertView.findViewById(R.id.schedule_item_card_date);
        TextView timeslotText = convertView.findViewById(R.id.schedule_item_card_timeslot);
        TextView goalText = convertView.findViewById(R.id.schedule_item_card_goalname);
        dateText.setText(dateTimeslotAndGoal.getDate());
        timeslotText.setText(dateTimeslotAndGoal.getTimeslotDisplay());
        goalText.setText(dateTimeslotAndGoal.getGoalName());

        return convertView;
    }
}
