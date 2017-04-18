package com.kovapss.transaltenow.View.SavedList;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kovapss.transaltenow.Model.Entities.TranslateModel;
import com.kovapss.transaltenow.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

class RealmAdapter extends RealmRecyclerViewAdapter<TranslateModel, RealmAdapter.ViewHolder> {



    RealmAdapter(@Nullable OrderedRealmCollection data) {
        super(data, true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.translate_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TranslateModel realmObject = getItem(position);
         holder.model = realmObject;
         holder.originalText.setText(realmObject.getOriginalText());
         holder.translatedText.setText(realmObject.getTranslatedText());
         holder.translateStatus.setText(realmObject.getLanguages());
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.original_text) TextView originalText;

        @BindView(R.id.translated_text) TextView translatedText;

        @BindView(R.id.translate_status) TextView translateStatus;

        public TranslateModel model;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

