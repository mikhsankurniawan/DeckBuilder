package com.example.tktplproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "decks", foreignKeys = @ForeignKey(entity = HeroClass.class,
        parentColumns = "class_name",
        childColumns = "hero_name"))
public class Deck {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "deck_name")
    public String deckName;

    @ColumnInfo(name = "hero_name")
    public String heroName;

    public Deck(String deckName, String heroName) {
        this.deckName = deckName;
        this.heroName = heroName;
    }
}
