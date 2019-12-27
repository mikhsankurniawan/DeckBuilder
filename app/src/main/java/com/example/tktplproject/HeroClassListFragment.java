package com.example.tktplproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HeroClassListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroClassListFragment extends Fragment {
    List<HeroClass> hero;
    AppViewModel appViewModel;

    public HeroClassListFragment() {
        // Required empty public constructor
    }

    public static HeroClassListFragment newInstance() {
        HeroClassListFragment fragment = new HeroClassListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        hero = appViewModel.getAllClasses().getValue();
        View view = inflater.inflate(R.layout.fragment_hero_class_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        int grid = this.getScreenResolution(getContext()) > 1600 ? 5 : 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), grid));
        recyclerView.setAdapter(new HeroClassAdapter(hero));
        return view;
    }

    private static int getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;

        return width;
    }
}
