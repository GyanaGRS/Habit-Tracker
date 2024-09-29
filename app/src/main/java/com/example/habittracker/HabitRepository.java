package com.example.habittracker;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HabitRepository {
    private HabitDao habitDao;
    private LiveData<List<Habit>> allHabits;
    private ExecutorService executorService;

    public HabitRepository(Application application) {
        HabitDatabase database = HabitDatabase.getInstance(application);
        habitDao = database.habitDao();
        allHabits = habitDao.getAllHabits();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(Habit habit) {
        executorService.execute(() -> habitDao.insert(habit));
    }

    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public void delete(Habit habit) {
        executorService.execute(() -> habitDao.delete(habit));
    }


    public void resetAllHabits() {
        executorService.execute(() -> habitDao.resetAllHabits());
    }

}
