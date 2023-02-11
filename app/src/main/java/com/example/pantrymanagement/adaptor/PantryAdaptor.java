package com.example.pantrymanagement.adaptor;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrymanagement.R;
import com.example.pantrymanagement.room.PantryEntity;


import java.util.ArrayList;
import java.util.List;

//This class fills up the recyclerView with card-views that has data from, the room database
public class PantryAdaptor extends RecyclerView.Adapter<PantryAdaptor.PantryHolder> {

    private List<PantryEntity> pantryEntities = new ArrayList<>();
    private onClickListener listener;

    //inflate view holder with the card-views
    @NonNull
    @Override
    public PantryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item, parent, false);
        return new PantryHolder(itemView);
    }

    //set the textview with the data from room database
    @Override
    public void onBindViewHolder(@NonNull PantryHolder holder, int position) {
        PantryEntity currentItem = pantryEntities.get(position);
        holder.mItemName.setText(currentItem.getName());
        holder.mDayTillExpiryView.setText(String.valueOf(currentItem.getDays_to_expiry()));
        holder.mExpiryDateView.setText(String.valueOf(currentItem.getExpiryDate()));
        holder.mCommentView.setText(String.valueOf(currentItem.getComment()));
    }

    //return number of Entities
    @Override
    public int getItemCount() {
        return pantryEntities.size();
    }

    //set the Entity and notify database
    @SuppressLint("NotifyDataSetChanged")
    public void setPantryEntities(List<PantryEntity> pantryEntities) {
        this.pantryEntities = pantryEntities;
        notifyDataSetChanged();
    }

    //return Entity at a given position
    public PantryEntity getPantryAt(int position) {
        return pantryEntities.get(position);
    }


    //Initialise the recycler view of card-views
    class PantryHolder extends RecyclerView.ViewHolder {

        private TextView mItemName;
        private TextView mDayTillExpiryView;
        private TextView mExpiryDateView;
        private TextView mCommentView;

        public PantryHolder(@NonNull View itemView) {
            super(itemView);
            widgetInit();

            //click listener for when users want to click on the card view and see details
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onClick(pantryEntities.get(position));
                }
            });
        }

        //initialise all views
        private void widgetInit() {
            mItemName = itemView.findViewById(R.id.ItemName);
            mDayTillExpiryView = itemView.findViewById(R.id.dayTIllExpiry);
            mExpiryDateView = itemView.findViewById(R.id.ExpiryDate);
            mCommentView = itemView.findViewById(R.id.commentText);
        }
    }

    public interface onClickListener {
        void onClick(PantryEntity pantryEntity);
    }

    public void setUpListener(onClickListener listener) {
        this.listener = listener;
    }
}
