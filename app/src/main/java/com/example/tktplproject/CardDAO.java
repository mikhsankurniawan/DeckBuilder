package com.example.tktplproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDAO {
    @Query("SELECT * FROM cards")
    List<Card> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Card... cards);
}
