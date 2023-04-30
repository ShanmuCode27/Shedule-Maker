package com.shanmu.schedulemaker.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanmu.schedulemaker.R;
import com.shanmu.schedulemaker.models.DateTimeslotAndGoal;
import com.shanmu.schedulemaker.models.GoalProgress;

import java.util.List;

public class AllProgressViewAdapter extends ArrayAdapter<GoalProgress> {

    public AllProgressViewAdapter(Context context, List<GoalProgress> goalProgressList) {
        super(context, 0, goalProgressList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoalProgress dateTimeslotAndGoal = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.goal_progress_items, parent, false);
        }
        TextView goalnameText = convertView.findViewById(R.id.goal_progress_goalname);
        TextView progressText = convertView.findViewById(R.id.goal_progress_progress_value);

        goalnameText.setText(dateTimeslotAndGoal.getGoalName());
        progressText.setText(dateTimeslotAndGoal.getProgress());

        return convertView;
    }
}
