package com.example.tktplproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HeroDetailsFragment extends Fragment {
    static HeroClass hero;
    static AppViewModel appViewModel;
    AppViewModel viewModel;
    List<Deck> deckLists;

    public HeroDetailsFragment() {
        // Required empty public constructor
    }

    public static HeroDetailsFragment newInstance() {
        HeroDetailsFragment fragment = new HeroDetailsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
        hero = appViewModel.getSelectedHero().getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        deckLists = viewModel.getDecks().getValue();
        View rootView = inflater.inflate(R.layout.fragment_hero_details, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.deck_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DeckAdapter(this.getContext(), deckLists));
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        deckLists = viewModel.getDecks().getValue();
        ImageView heroImage = getView().findViewById(R.id.class_image);
        TextView heroName = getView().findViewById(R.id.class_name);
        TextView heroDescription = getView().findViewById(R.id.class_description);
        TextView heroPower = getView().findViewById(R.id.hero_power);
        TextView heroPowerDescription = getView().findViewById(R.id.hero_power_description);
        Button addDeck = getView().findViewById(R.id.add_deck_button);

        Context c = getContext();
        int imageResourceId;
        try {
            imageResourceId = c.getResources().getIdentifier(hero.classImageURL, null, c.getPackageName());
        } catch (NullPointerException e) {
            imageResourceId = getArguments().getInt("identifier");
        }

        final int resId = imageResourceId;
        heroImage.setImageResource(imageResourceId);

        addDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateDeckActivity.class);
                intent.putExtra("class_name", heroName.getText());
                intent.putExtra("identifier", resId);
                startActivity(intent);
            }
        });

        if (hero.className != null) {
            heroName.setText(hero.className);
        }

        if (hero.classDescription != null) {
            heroDescription.setText(getString(R.string.class_description) + hero.classDescription);
        }

        if (hero.classPower != null) {
            heroPower.setText(getString(R.string.hero_power) + hero.classPower);
        }

        if (hero.classPowerDescription != null) {
            heroPowerDescription.setText(getString(R.string.hero_power_description) + hero.classPowerDescription);
        }
    }
}
