package com.example.tktplproject;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardDetailsFragment extends Fragment {

    static Card card;
    static AppViewModel appViewModel;

    public CardDetailsFragment() {
        // Required empty public constructor
    }

    public static CardDetailsFragment newInstance() {
        CardDetailsFragment fragment = new CardDetailsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
        card = appViewModel.getSelectedCard().getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView cardImage = getView().findViewById(R.id.card_image);

        Context c = getContext();
        int imageResourceId = c.getResources().getIdentifier(card.cardImageURL, null, c.getPackageName());
        cardImage.setImageResource(imageResourceId);
    }


}
