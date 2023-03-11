package com.shanmu.schedulemaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartScreen extends Fragment {

    public StartScreen() {
        // Required empty public constructor
    }

    public static StartScreen newInstance() {
        StartScreen fragment = new StartScreen();
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

        View view = inflater.inflate(R.layout.fragment_start_screen, container, false);

        Button startBtn = (Button) view.findViewById(R.id.move_to_get_goals_btn);
        startBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                moveToGetGoalsScreen();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void moveToGetGoalsScreen() {

          NavHostFragment.findNavController(StartScreen.this).navigate(R.id.action_startScreen_to_getGoals2);

    }

}