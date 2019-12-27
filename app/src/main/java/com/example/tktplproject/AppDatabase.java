package com.example.tktplproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HeroClass.class, Deck.class, Card.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HeroClassDAO heroClassDao();
    public abstract DeckDAO deckDao();
    public abstract CardDAO cardDao();
}
