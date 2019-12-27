package com.example.tktplproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeckDAO {
    @Query("SELECT * FROM decks")
    List<Deck> getAll();

    @Query("SELECT * FROM decks WHERE deck_name LIKE :name LIMIT 1")
    Deck findByName(String name);

    @Insert
    void insertAll(Deck... decks);

    @Insert
    void insert(Deck deck);

    @Delete
    void delete(Deck deck);
}
