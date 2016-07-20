package com.example.android.habittracker;

import android.provider.BaseColumns;

/**
 * Created by anirudha.joshi on 7/12/2016.
 */

// Contract class that stores all database, column, key names centrally
public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public HabitContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class HabitEntry implements BaseColumns {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "habittracker";
        public static final String TABLE_NAME = "habit";
        public static final String COLUMN_NAME_HABIT_ID = "_id";
        public static final String COLUMN_NAME_HABIT_TIMESADAY = "timesaday";
        public static final String COLUMN_NAME_HABIT = "habit";
    }
}
