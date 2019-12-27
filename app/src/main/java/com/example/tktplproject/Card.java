package com.example.tktplproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "card_name")
    public String cardName;

    @ColumnInfo(name = "card_image_URL")
    public String cardImageURL;

    public Card(String cardName, String cardImageURL) {
        this.cardName = cardName;
        this.cardImageURL = cardImageURL;
    }
}
