package com.example.felixalarm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.felixalarm.dao.AlarmDao;
import com.example.felixalarm.entities.Alarm;

@Database(entities = Alarm.class, version = 1,exportSchema = false)
public abstract class AlarmsDatabase extends RoomDatabase {
    private static AlarmsDatabase alarmsDatabase;
    public static synchronized AlarmsDatabase getDatabase(Context context){
        if (alarmsDatabase==null){
            alarmsDatabase= Room.databaseBuilder(
                    context,
                    AlarmsDatabase.class,
                    "alarms_db"
            ).build();
        }
        return alarmsDatabase;
    }

    public abstract AlarmDao alarmDao();
}
