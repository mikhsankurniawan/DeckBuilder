package com.example.tktplproject;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeckAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Deck> deckList;
    Context context;
    int totalCards = 0;

    public DeckAdapter(Context context, List<Deck> deckList){
        this.deckList = deckList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeckAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if (deckList != null && deckList.size() > 0) {
            return deckList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.black_space)
        ImageView coverImageView;

        @BindView(R.id.deck_name)
        Button deckName;

        private Deck deck;
        private AppViewModel appViewModel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appViewModel = ViewModelProviders.of((MainActivity) itemView.getContext()).get(AppViewModel.class);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            deckName.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            if (deckList.size() > 0) {
                deck = deckList.get(position);
                Context c = coverImageView.getContext();
                int nameResourceId = c.getResources().getIdentifier(deck.deckName, null, c.getPackageName());
                deckName.setText(deck.deckName);
                coverImageView.setImageResource(nameResourceId);
            }
        }
    }
}
