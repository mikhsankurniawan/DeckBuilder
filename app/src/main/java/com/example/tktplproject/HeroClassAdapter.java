package com.example.tktplproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroClassAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<HeroClass> heroList;

    public HeroClassAdapter(List<HeroClass> heroList){
        this.heroList = heroList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_class_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        if (heroList != null && heroList.size() > 0) {
            return heroList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {

        @BindView(R.id.hero_image)
        ImageView coverImageView;

        @BindView(R.id.hero_name)
        TextView heroName;

        private HeroClass hero;
        private AppViewModel appViewModel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            appViewModel = ViewModelProviders.of((MainActivity) itemView.getContext()).get(AppViewModel.class);
        }

        @Override
        public void onClick(View view) {
            MainActivity mainActivity = (MainActivity) view.getContext();
            appViewModel.selectHero(hero);
            mainActivity.onHeroSelected();
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            heroName.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            hero = heroList.get(position);
            Context c = coverImageView.getContext();
            int imageResourceId = c.getResources().getIdentifier(hero.classImageURL, null, c.getPackageName());
            coverImageView.setImageResource(imageResourceId);

            if (hero.className != null) {
                heroName.setText(hero.className);
            }
        }
    }
}
