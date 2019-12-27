package com.example.tktplproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tktplproject.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppViewModel extends ViewModel {
    private MutableLiveData<List<HeroClass>> allClasses = new MutableLiveData<>();
    private MutableLiveData<List<Deck>> allDecks = new MutableLiveData<>();
    private MutableLiveData<List<Card>> allCards = new MutableLiveData<>();
    private MutableLiveData<List<DeckCardJoin>> allJoins = new MutableLiveData<>();
    private MutableLiveData<Integer> totalCards = new MutableLiveData<>();
    private final MutableLiveData<Card> selectedCard = new MutableLiveData<Card>();
    private final MutableLiveData<HeroClass> selectedHero = new MutableLiveData<>();

    public MutableLiveData<Integer> getTotalCards() {
        if (totalCards.getValue() == null) {
            totalCards.setValue(0);
        }
        return totalCards;
    }

    public MutableLiveData<Card> getSelectedCard() {
        return selectedCard;
    }

    public MutableLiveData<HeroClass> getSelectedHero() {
        return selectedHero;
    }

    public Integer plusTotalCards(Integer totalCards) { return totalCards + 1; }
    public Integer minusTotalCards(Integer totalCards) { return totalCards - 1; }

    public void selectCard(Card card) {
        selectedCard.setValue(card);
    }

    public void selectHero(HeroClass heroClass) { selectedHero.setValue(heroClass); }

    public MutableLiveData<List<HeroClass>> getAllClasses() {
        if (allClasses.getValue() == null) {
            try {
                allClasses.setValue(this.populateClassDatabase());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return allClasses;
    }

    public LiveData<List<Deck>> getDecks() {
        if (allDecks.getValue() == null) {
            try {
                allDecks.setValue(this.populateDeckDatabase());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return allDecks;
    }

    public MutableLiveData<List<Card>> getAllCards() {
        if (allCards.getValue() == null) {
            try {
                allCards.setValue(this.populateDatabase());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return allCards;
    }

    public void addDeck(String deckName, String className) throws ExecutionException, InterruptedException {
        new addDeckAsyncTask().execute(deckName, className).get();
    }

    private class addDeckAsyncTask extends AsyncTask<String, String, List<Deck>> {
        AppDatabase db = HearthstoneApp.getInstance().getDatabase();

        @Override
        protected List<Deck> doInBackground(String... param) {
            Deck deck = new Deck(param[0], param[1]);
            db.deckDao().insert(deck);

            return db.deckDao().getAll();
        }
    }

    public List<Card> populateDatabase() throws ExecutionException, InterruptedException {
        return new PopulateDatabaseAsyncTask().execute().get();
    }

    private class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, List<Card>> {
        private String[] card_names;
        private String[] card_image_URLs;
        AppDatabase db = HearthstoneApp.getInstance().getDatabase();

        @Override
        protected List<Card> doInBackground(Void... url) {
            if (db.cardDao().getAll().isEmpty()) {
                Context c = HearthstoneApp.getInstance().getApplicationContext();
                if (c != null) {
                    Resources resources = c.getResources();
                    card_names = resources.getStringArray(R.array.card_names);
                    card_image_URLs = resources.getStringArray(R.array.card_image_URLs);
                }
                for (int i = 0; i < card_names.length; i++) {
                    Card card = new Card(card_names[i], card_image_URLs[i]);
                    db.cardDao().insertAll(card);
                }
            }

            return db.cardDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Card> populatedCards) {
            allCards.setValue(populatedCards);
        }
    }

    public List<HeroClass> populateClassDatabase() throws ExecutionException, InterruptedException {
        return new PopulateClassDatabaseAsyncTask().execute().get();
    }

    private class PopulateClassDatabaseAsyncTask extends AsyncTask<Void, Void, List<HeroClass>> {
        private String[] hero_names;
        private String[] hero_image_URLs;
        private String[] hero_description;
        private String[] hero_power;
        private String[] hero_power_description;
        AppDatabase db = HearthstoneApp.getInstance().getDatabase();

        @Override
        protected List<HeroClass> doInBackground(Void... url) {
            if (db.heroClassDao().getAll().isEmpty()) {
                Context c = HearthstoneApp.getInstance().getApplicationContext();
                if (c != null) {
                    Resources resources = c.getResources();
                    hero_names = resources.getStringArray(R.array.hero_names);
                    hero_image_URLs = resources.getStringArray(R.array.hero_image_URLs);
                    hero_description = resources.getStringArray(R.array.hero_descriptions);
                    hero_power = resources.getStringArray(R.array.hero_powers);
                    hero_power_description = resources.getStringArray(R.array.hero_power_descriptions);
                }
                for (int i = 0; i < hero_names.length; i++) {
                    HeroClass hero = new HeroClass(hero_names[i], hero_description[i],
                            hero_power[i], hero_power_description[i], hero_image_URLs[i]);
                    db.heroClassDao().insertAll(hero);
                }
            }

            return db.heroClassDao().getAll();
        }

        @Override
        protected void onPostExecute(List<HeroClass> populatedClasses) {
            allClasses.setValue(populatedClasses);
        }
    }

    public List<Deck> populateDeckDatabase() throws ExecutionException, InterruptedException {
        return new PopulateDeckDatabaseAsyncTask().execute().get();
    }

    private class PopulateDeckDatabaseAsyncTask extends AsyncTask<Void, Void, List<Deck>> {
        private String[] deck_names;
        private String[] hero_names;
        AppDatabase db = HearthstoneApp.getInstance().getDatabase();

        @Override
        protected List<Deck> doInBackground(Void... url) {
            return db.deckDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Deck> populatedDecks) {
            allDecks.setValue(populatedDecks);
        }
    }
}
