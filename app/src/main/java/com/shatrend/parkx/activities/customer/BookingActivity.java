package com.shatrend.parkx.activities.customer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.shatrend.parkx.R;
import com.shatrend.parkx.models.Parking;
import com.shatrend.parkx.models.Slot;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private Parking parking;
    private ArrayList<Slot> parkingSlots;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        parking = getIntent().getParcelableExtra("parking");
        parkingSlots = getIntent().getParcelableArrayListExtra("parking_slots");

        if (parkingSlots != null) {
            setupRealtimeUpdates();
        }
    }

    private void setupRealtimeUpdates() {
        listenerRegistration = db.collection("parkings")
                .document(parking.getId())
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Log.w("Firestore", "Listen failed.", e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Parking updatedParking = Parking.fromDocumentSnapshot(documentSnapshot);
                        parkingSlots = (ArrayList<Slot>) updatedParking.getSlots();
                        displaySlots();
                    } else {
                        Log.d("Firestore", "No such document");
                    }
                });
    }

    private void displaySlots() {
        GridLayout layoutSlots = findViewById(R.id.layout_slots);
        layoutSlots.removeAllViews();

        for (Slot slot : parkingSlots) {
            View slotView = LayoutInflater.from(this).inflate(R.layout.slot_item, layoutSlots, false);

            FirebaseUser currentUser = mAuth.getCurrentUser();

            TextView tvSlotId = slotView.findViewById(R.id.tv_slot_id);
            Button btnBook = slotView.findViewById(R.id.btn_book);

            tvSlotId.setText(slot.getId());
            btnBook.setBackgroundColor(slot.getAvailable() ? Color.GREEN : Color.RED);

            if(slot.getAvailable()) {
                btnBook.setText("Book");
                btnBook.setBackgroundColor(Color.GREEN);
            } else {
                if(currentUser.getUid().equals(slot.getBookedBy())) {
                    btnBook.setText("Cancel");
                    btnBook.setBackgroundColor(Color.RED);
                } else {
                    btnBook.setText("Booked");
                    btnBook.setBackgroundColor(Color.LTGRAY);
                    btnBook.setEnabled(false);
                }
            }

            btnBook.setOnClickListener(v -> {
                if (currentUser != null) {
                    if(slot.getAvailable()) {
                        slot.setAvailable(false);
                        slot.setBookedBy(currentUser.getUid());
                    } else {
                        slot.setAvailable(true);
                        slot.setBookedBy("");
                    }
                    updateSlotInFirestore("book", slot, slotView);
                } else {
                    showLoginDialog();
                }
            });

            layoutSlots.addView(slotView);
        }
    }

    private void updateSlotInFirestore(String bookOrCancel,Slot updatedSlot, View slotView) {
        if (parking == null || parking.getId() == null) {
            Log.d("Firestore", "Parking object or Parking ID is null");
            return;
        }

        // Retrieve the current slots array from Firestore
        db.collection("parkings")
                .document(parking.getId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Parking parking = Parking.fromDocumentSnapshot(documentSnapshot);
                        List<Slot> slots = parking.getSlots();

                        for (int i = 0; i < slots.size(); i++) {
                            Slot slot = slots.get(i);
                            if (slot.getId().equals(updatedSlot.getId())) {
                                slot.setAvailable(updatedSlot.getAvailable());
                                slot.setBookedBy(updatedSlot.getBookedBy());
                                slots.set(i, slot);
                                break;
                            }
                        }

                        // Update the slots array in Firestore
                        db.collection("parkings")
                                .document(parking.getId())
                                .update("slots", slots)
                                .addOnSuccessListener(aVoid -> {
                                    // No need to refresh UI since we have a listener
//                                    refreshSlotDisplay(slotView, updatedSlot);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Error updating slot", e);
                                });
                    } else {
                        Log.d("Firestore", "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error retrieving document", e);
                });
    }


    private void refreshSlotDisplay(View slotView, Slot slot) {
        Button btnBook = slotView.findViewById(R.id.btn_book);
        btnBook.setBackgroundColor(slot.getAvailable() ? Color.GREEN : Color.RED);
        btnBook.setText(slot.getAvailable() ? "Book" : "Cancel");
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to log in to book a slot.")
                .setPositiveButton("Log In", (dialog, id) -> {
                    Intent loginIntent = new Intent(BookingActivity.this, CustomerLoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener to avoid memory leaks
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

}