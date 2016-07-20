package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudha.joshi on 7/12/2016.
 */

// Subclass of SQLiteOpenHelper that overrides onCreate & onUpgrade
public class HabitDBHelper extends SQLiteOpenHelper {

    //SQLiteDatabase db;

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_HABIT_ENTRIES =
            "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " (" +
                    HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + " " +
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT + TEXT_TYPE + COMMA_SEP + " " +
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY + INTEGER_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;

    public HabitDBHelper(Context context) {
        super(context, HabitContract.HabitEntry.DATABASE_NAME, null, HabitContract.HabitEntry.DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HABIT_ENTRIES);
        Log.d("Habit", SQL_CREATE_HABIT_ENTRIES);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Adding new habit - adds a habit row to database. The id column is set to autoincrement. The habit column is text & the numberoftimesaday is an int
    public long insert(String habit, int numOfTimesADay) {

        long newRowId = -1;

        try {
            // Gets the data repository in write mode
            SQLiteDatabase db = this.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(HabitContract.HabitEntry.COLUMN_NAME_HABIT, habit);
            values.put(HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY, numOfTimesADay);

            // Insert the new row, returning the primary key value of the new row
            newRowId = db.insert(
                    HabitContract.HabitEntry.TABLE_NAME,
                    null,
                    values);
            db.close();
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }

        return newRowId;
    }

    // Get a single habit. This method the habit object with specified id
    public Habit getHabit(int id) {

        Habit habit = null;

        try {


            SQLiteDatabase db = this.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY
            };


            String selection = HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID + "=?";
            String[] selectionWhereArgs = new String[]{String.valueOf(id)};

            Cursor cursor = db.query(
                    // Table name
                    HabitContract.HabitEntry.TABLE_NAME,
                    // select query column names - these are the columns to return
                    new String[]{HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY},
                    // where clause - which columns to select on
                    selection,
                    // selection arguments - where clause column values to select on
                    selectionWhereArgs,
                    null,       // group by
                    null,       // having
                    null,       // order by
                    null);      // limit

            // If we got results get the first one
            if (cursor != null) {
                cursor.moveToFirst();

                habit = new Habit(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            }
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }

        return habit;
    }

    // Get a single habit. This method returns the cursor for the specified row id. It is up to the calling class to call cursor.movefirst
    public Cursor getCursor(int id) {

        Cursor cursor = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY
            };

            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT + " DESC";

            cursor = db.query(
                    // Table name
                    HabitContract.HabitEntry.TABLE_NAME,
                    // select query column names - these are the columns to return
                    new String[]{HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY},
                    // where clause - which columns to select on
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID + "=?",
                    // selection arguments - where clause column values to select on
                    new String[]{String.valueOf(id)},
                    null,       // group by
                    null,       // having
                    sortOrder,  // order by
                    null);      // limit

        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }

        return cursor;
    }

    // Get All Habits
    public List<Habit> getAllHabits() {

        Habit habit = null;
        List<Habit> habitList = new ArrayList<Habit>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY
            };

            Cursor cursor = db.query(
                    // Table name
                    HabitContract.HabitEntry.TABLE_NAME,
                    // select query column names - these are the columns to return
                    new String[]{HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID, HabitContract.HabitEntry.COLUMN_NAME_HABIT, HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY},
                    null,   // no where clause - return all rows
                    null,   // no selection arguments to where clause - return all rows
                    null,   // group by
                    null,   // having
                    null,   // order by
                    null);  // limit

            if (cursor.moveToFirst()) {
                do {
                    Habit h = new Habit();
                    h.setID(Integer.parseInt(cursor.getString(0)));
                    h.setHabit(cursor.getString(1));

                    // Adding habit to list
                    habitList.add(h);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }

        // Return all habits in db
        return habitList;
    }

    // Updating single HabitContract
    public int update(Habit habit) {
        int count = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(HabitContract.HabitEntry.COLUMN_NAME_HABIT, habit.getHabit());
            values.put(HabitContract.HabitEntry.COLUMN_NAME_HABIT_TIMESADAY, habit.getNumberOfTimeADay());

            // Which row to update, based on the ID
            String selection = HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID + " = ?";
            String[] selectionArgs = {String.valueOf(habit.getID())};

            count = db.update(
                    HabitContract.HabitEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }
        return count;
    }

    // Deleting single HabitContract
    public void delete(Habit h) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();

            // Define 'where' part of query.
            String selection = HabitContract.HabitEntry.COLUMN_NAME_HABIT_ID + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {String.valueOf(h.getID())};

            // Issue SQL statement.
            db.delete(HabitContract.HabitEntry.TABLE_NAME, selection, selectionArgs);

            db.close();
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }
    }

    // Get number of habits in db
    public long getHabitsCount() {

        long cnt = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cnt = DatabaseUtils.queryNumEntries(db, HabitContract.HabitEntry.TABLE_NAME);
            db.close();
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }

        return cnt;
    }

    // Delete all Habits
    public void delete() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(HabitContract.HabitEntry.TABLE_NAME, null, null);
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }
    }

    // Drop Habits table
    public void drop() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(SQL_DELETE_ENTRIES);
        } catch (Exception e) {
            Log.d("HabitDBHelper", e.toString());
        }
    }
}


