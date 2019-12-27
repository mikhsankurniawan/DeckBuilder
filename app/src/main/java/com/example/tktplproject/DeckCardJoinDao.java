package com.example.tktplproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DeckCardJoinDao {
    @Insert
    void insert(DeckCardJoin deckCardJoin);

    @Query("SELECT * FROM cards " +
            "INNER JOIN deck_card_join " +
            "ON cards.card_name=deck_card_join.cardNameJoin " +
            "WHERE deck_card_join.deckNameJoin=:deckNameJoin")
    List<Card> getCardsForDeck(final String deckNameJoin);
}
