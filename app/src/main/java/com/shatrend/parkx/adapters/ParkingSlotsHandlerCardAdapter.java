package com.shatrend.parkx.adapters;

//public class ParkingSlotsHandlerCardAdapter {
//}

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import com.shatrend.parkx.fragments.parking.ParkingHomeFragment;
import com.shatrend.parkx.helpers.DatabaseHelper;

import java.util.List;

public class ParkingSlotsHandlerCardAdapter extends RecyclerView.Adapter<ParkingSlotsHandlerCardAdapter.ViewHolder> {

    private List<Card> cards;
//    private List<Integer> vehicleSlots;
    private Context context;
    private int bikeFreeSlots, threeWheelerFreeSlots, fourWheelerFreeSlots;

    private TextView bikeFreeSlotsCount;

    private OnDataChangeListener listener;

//    private OnTextChangeListener

    public ParkingSlotsHandlerCardAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
//        this.vehicleSlots = vehicleSlots;
//        this.bikeFreeSlots = bikeFreeSlots;
    }

    public interface OnDataChangeListener {
        void onDataChanged(int bikeFreeSlots);
    }

    public ParkingSlotsHandlerCardAdapter(OnDataChangeListener listener) {
        this.listener = listener;
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

            bikeFreeSlotsCount = itemView.findViewById(R.id.fragment_parking_home_tv_bike_free_slots);
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
//                    bikeFreeSlots--;
//                    boolean updateSuccess = updateVehicleCount(pos);
//                    listener.onDataChanged(bikeFreeSlots);

                    ParkingHomeFragment parkingHomeFragment = new ParkingHomeFragment();
                    parkingHomeFragment.updateVehicleSlots();

//                    if (updateSuccess){
////                        bikeFreeSlotsCount.setText(String.valueOf(bikeFreeSlots));
//                        bikeFreeSlotsCount.setText("Hi");
//                    } else {
//                        Toast.makeText(context, "Filling bike slot failed", Toast.LENGTH_SHORT).show();
//                    }

                    Log.d("FILL0", String.valueOf(bikeFreeSlots));
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

    private boolean updateVehicleCount(int pos) {
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (pos == 0) {
            values.put("BIKE_FREE_SLOTS", bikeFreeSlots);
            long success = db.update("PARKING_SLOTS", values, null, null);
            db.close();
            return success != -1;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public int getBikeFreeSlots() {
        return bikeFreeSlots;
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
