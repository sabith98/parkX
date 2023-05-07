package com.shatrend.parkx.adapters;

//public class ParkingSlotsHandlerCardAdapter {
//}

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.parking.ParkingHomeActivity;

import java.util.List;

public class ParkingSlotsHandlerCardAdapter extends RecyclerView.Adapter<ParkingSlotsHandlerCardAdapter.ViewHolder> {

    private List<Card> cards;

    public ParkingSlotsHandlerCardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView icon;
        TextView name;
        Button btnFill;
        Button btnFree;

        public ViewHolder(View itemView) {
            super(itemView);
//            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            btnFill = itemView.findViewById(R.id.card_parking_slots_handler_btn_fill);
            btnFree = itemView.findViewById(R.id.card_parking_slots_handler_btn_free);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_parking_slots_handler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = cards.get(position);
//        holder.icon.setImageResource(card.getIcon());
        holder.name.setText(card.getName());
        holder.btnFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle fill button click here
                int pos = holder.getAdapterPosition();
                if (pos == 0) {
                    Log.d("FILL", "0: ");
                } else if (pos == 1) {
                    Log.d("FILL", "1: ");
                } else if (pos == 2) {
                    Log.d("FILL", "2: ");
                }
            }
        });

        holder.btnFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button2 click here
                Log.d("FREE", "Freed: ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class Card {
//        private int icon;
        private String name;

//        public Card(int icon, String name) {
//            this.icon = icon;
//            this.name = name;
//        }

        public Card(String name) {
            this.name = name;
        }

//        public int getIcon() {
//            return icon;
//        }

        public String getName() {
            return name;
        }
    }
}
