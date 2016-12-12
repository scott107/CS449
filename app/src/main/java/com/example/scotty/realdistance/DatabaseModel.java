package com.example.scotty.realdistance;

/**
 * Created by scotty on 11/6/2016.
 */

import java.util.ArrayList;
import android.content.Context;

public class DatabaseModel {

    // Singleton design pattern
    private static DatabaseModel instance = null;

    private Workout workoutGateway;
    private Daily dailyGateway;

    private DatabaseModel(Context context) {
        workoutGateway = new Workout(context);
        dailyGateway = new Daily(context);
    }

    public synchronized static DatabaseModel instance(Context context) {
        if( instance == null ) {
            instance = new DatabaseModel(context);
        }
        return instance;
    }

    public double insertDaily(double Daily) {
        return dailyGateway.insert(Daily);
    }
    //throws exception
    //      in the event that yesterday's daily total is not in the database.
    public double YesterdayDaily() throws Exception{
        return dailyGateway.YesterdaysDistance();
    }

    public long insertWorkout(Integer Workout) {
        return workoutGateway.insert(Workout);
    }

    public Integer getLastWorkout(int id) {
        return workoutGateway.LastWorkout(id);
    }
}