package com.example.tktplproject;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "deck_card_join",
        primaryKeys = { "cardNameJoin", "deckNameJoin" },
        foreignKeys = {
                @ForeignKey(entity = Deck.class,
                        parentColumns = "deckName",
                        childColumns = "deckNameJoin"),
                @ForeignKey(entity = Card.class,
                        parentColumns = "cardName",
                        childColumns = "cardNameJoin")
        })
public class DeckCardJoin {
    public String cardNameJoin;
    public String deckNameJoin;

    public DeckCardJoin(String cardNameJoin, String deckNameJoin) {
        this.cardNameJoin = cardNameJoin;
        this.deckNameJoin = deckNameJoin;
    }
}
