package com.example.habittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HabitViewModel habitViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextHabit = findViewById(R.id.edit_text_habit);
        Button buttonAdd = findViewById(R.id.button_add);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HabitAdapter adapter = new HabitAdapter(habitViewModel);
        recyclerView.setAdapter(adapter);

        habitViewModel = new ViewModelProvider(this).get(HabitViewModel.class);
        habitViewModel.getAllHabits().observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                adapter.setHabits(habits);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = editTextHabit.getText().toString();
                if (!habitName.isEmpty()) {
                    Habit habit = new Habit(habitName, false);
                    habitViewModel.insert(habit);
                }
            }
        });

        scheduleHabitReset();
    }

    private void scheduleHabitReset() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at 12:00 AM
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

    }
}
