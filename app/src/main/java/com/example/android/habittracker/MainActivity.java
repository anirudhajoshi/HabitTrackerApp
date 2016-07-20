package com.example.android.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDBHelper dbHabit = new HabitDBHelper(this);

        // Test database insertion
        int h1_id = (int) dbHabit.insert("Walk the dog", 2);
        int h2_id = (int) dbHabit.insert("Run...", 0);
        int h3_id = (int) dbHabit.insert("Gym", 1);
        Log.d("MainActivity", "Count: " + Long.toString(dbHabit.getHabitsCount()));

        // Test database read
        Habit h2 = dbHabit.getHabit(h1_id);
        Log.d("MainActivity", "ID: " + Integer.toString(h2.getID()) + "; Habit: " + h2.getHabit() + "; Number of Times A Day: " + h2.getNumberOfTimeADay());

        // Test database update
        h2.setHabit("Swim");
        h2.setNumberOfTimeADay(1);
        dbHabit.update(h2);

        // Get all remaining habits and print to log
        List<Habit> habitList = dbHabit.getAllHabits();
        for (Habit h : habitList) {
            String log = "Id: " + h.getID() + " , Habit: " + h.getHabit() + " , Number of times a day: " + h.getNumberOfTimeADay();
            // Writing habits to log
            Log.d("MainActivity: ", log);
        }

        // Test deletion of all table entries
        dbHabit.delete();
        Log.d("MainActivity", "Count: " + Long.toString(dbHabit.getHabitsCount()));
    }
}
