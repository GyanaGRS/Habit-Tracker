package com.example.habittracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitDao {
    @Insert
    void insert(Habit habit);

    @Delete
    void delete(Habit habit);

    @Update
    void update(Habit habit);

    @Query("SELECT * FROM habit_table")
    LiveData<List<Habit>> getAllHabits();

    @Query("DELETE FROM habit_table")
    void resetAllHabits(); // Define the method to reset all habits
}

