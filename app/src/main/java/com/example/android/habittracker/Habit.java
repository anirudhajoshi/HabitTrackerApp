package com.example.android.habittracker;

/**
 * Created by anirudha.joshi on 7/12/2016.
 */
public class Habit {

    private int ID;
    private String habit;
    private int NumberOfTimesADay;

    public Habit() {
    }

    public Habit(int id, String Habit, int NumberOfTimeADay) {
        this.ID = id;
        this.habit = Habit;
        this.NumberOfTimesADay = NumberOfTimeADay;
    }


    @Override
    public String toString() {
        return "Habit [id=" + ID + ", habit=" + habit + ", number of times a day=" + NumberOfTimesADay + "]";
    }

    public int getID() {
        return ID;
    }

    public int getID(String habit) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public int getNumberOfTimeADay() {
        return NumberOfTimesADay;
    }

    public void setNumberOfTimeADay(int numberOfTimeADay) {
        NumberOfTimesADay = numberOfTimeADay;
    }
}