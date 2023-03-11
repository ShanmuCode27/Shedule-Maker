package com.shanmu.schedulemaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetGoals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetGoals extends Fragment {

    String goalName;
    EditText goal;

    public GetGoals() {
        // Required empty public constructor
    }

    public static GetGoals newInstance() {
        GetGoals fragment = new GetGoals();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_goals, container, false);

        goal = (EditText) view.findViewById(R.id.input_text_goal);

        goal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goalName = "";
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No code
            }

            @Override
            public void afterTextChanged(Editable editable) {
                goalName = goal.getText().toString();
                Log.i("goal is ", goalName);
            }
        });

        return view;
    }
}