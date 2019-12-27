package com.example.tktplproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HeroClassDAO {
    @Query("SELECT * FROM classes")
    List<HeroClass> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(HeroClass... classes);
}
