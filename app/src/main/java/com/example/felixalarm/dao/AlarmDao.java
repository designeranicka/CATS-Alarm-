package com.example.felixalarm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.felixalarm.entities.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {


    @Query("SELECT * FROM alarms ORDER BY id ASC")
    List<Alarm> getAllAlarms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(Alarm alarm);

//    @Delete
//    void deleteAlarm(Alarm alarm);


}
