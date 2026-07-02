package com.example.ternaksmart;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface VaksinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Vaksin> vaksins);

    @Update
    void update(Vaksin data);

    @Query("SELECT * FROM vaksin WHERE type = :type")
    List<Vaksin> getVaksinByType(String type);

    @Query("SELECT COUNT(*) FROM vaksin WHERE type = 'JADWAL' AND isDone = 1")
    int getCompletedVaksinCount();

    @Query("SELECT COUNT(*) FROM vaksin WHERE type = 'JADWAL'")
    int getTotalVaksinCount();
}