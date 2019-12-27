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

public class AppAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    static {
        System.loadLibrary("native-lib");
    }

    private List<Card> cardList;
    Context context;
    int totalCards = 0;

    public native int increment(int x);
    public native int decrement(int x);

    public AppAdapter(Context context, List<Card> cardList){
        this.cardList = cardList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.create_deck_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if (cardList != null && cardList.size() > 0) {
            return cardList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.black_space)
        ImageView coverImageView;

        @BindView(R.id.card_button)
        Button cardButton;

        @BindView(R.id.minus_button)
        Button minusButton;

        @BindView(R.id.card_quantity)
        TextView cardQuantity;

        @BindView(R.id.plus_button)
        Button plusButton;

        private Card card;
        private AppViewModel appViewModel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appViewModel = ViewModelProviders.of((CreateDeckActivity) itemView.getContext()).get(AppViewModel.class);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            cardButton.setText("");
            cardQuantity.setText("0");
        }

        public void onBind(int position) {
            super.onBind(position);

            if (cardList.size() > 0) {
                card = cardList.get(position);
                Context c = coverImageView.getContext();
                int nameResourceId = c.getResources().getIdentifier(card.cardName, null, c.getPackageName());
                cardButton.setText(card.cardName);
                coverImageView.setImageResource(nameResourceId);

                cardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateDeckActivity createDeckActivity = (CreateDeckActivity) view.getContext();
                        appViewModel.selectCard(card);
                        createDeckActivity.onCardSelected();
                    }
                });

                minusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity = Integer.parseInt(cardQuantity.getText().toString());
                        if (quantity <= 2 && quantity > 0) {
                            cardQuantity.setText(Integer.toString(decrement(quantity)));
                            totalCards = appViewModel.minusTotalCards(totalCards);
                            ViewGroup parent = (ViewGroup) plusButton.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
                            TextView temporaryCards = (TextView) parent.getChildAt(3);
                            temporaryCards.setText(Integer.toString(totalCards));
                        }
                    }
                });

                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int quantity = Integer.parseInt(cardQuantity.getText().toString());
                        if (quantity >= 0 && quantity <2) {
                            cardQuantity.setText(Integer.toString(increment(quantity)));
                            totalCards = appViewModel.plusTotalCards(totalCards);
                            ViewGroup parent = (ViewGroup) plusButton.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
                            TextView temporaryCards = (TextView) parent.getChildAt(3);
                            temporaryCards.setText(Integer.toString(totalCards));
                        }
                    }
                });
            }
        }
    }
}
