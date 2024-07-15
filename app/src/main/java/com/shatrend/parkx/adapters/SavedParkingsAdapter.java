package com.shatrend.parkx.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.customer.BookingActivity;
import com.shatrend.parkx.models.Parking;
import com.shatrend.parkx.models.Slot;

import java.util.List;

public class SavedParkingsAdapter extends RecyclerView.Adapter<SavedParkingsAdapter.ViewHolder> {

    private Context context;
    private List<Parking> savedParkings;

    public SavedParkingsAdapter(Context context, List<Parking> savedParkings) {
        this.context = context;
        this.savedParkings = savedParkings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_parking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Parking parking = savedParkings.get(position);
        holder.tvParkingName.setText(parking.getName());
        holder.tvParkingPrice.setText("Price: Rs" + parking.getPrice());
        holder.tvAvailableSlots.setText("Available Slots: " + calculateAvailableSlots(parking.getSlots()));

        holder.btnBook.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(context, BookingActivity.class);
            bookingIntent.putExtra("parking", parking);
            context.startActivity(bookingIntent);
        });
    }

    @Override
    public int getItemCount() {
        return savedParkings.size();
    }

    private int calculateAvailableSlots(List<Slot> slots) {
        int availableSlots = 0;
        for (Slot slot : slots) {
            if (slot.getAvailable()) {
                availableSlots++;
            }
        }
        return availableSlots;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvParkingName, tvParkingPrice, tvAvailableSlots;
        Button btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvParkingName = itemView.findViewById(R.id.tv_parking_name);
            tvParkingPrice = itemView.findViewById(R.id.tv_parking_price);
            tvAvailableSlots = itemView.findViewById(R.id.tv_available_slots);
            btnBook = itemView.findViewById(R.id.btn_book);
        }
    }
}
