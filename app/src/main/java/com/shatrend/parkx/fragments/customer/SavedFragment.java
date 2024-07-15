package com.shatrend.parkx.fragments.customer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.shatrend.parkx.R;
import com.shatrend.parkx.adapters.SavedParkingsAdapter;
import com.shatrend.parkx.models.Parking;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private SavedParkingsAdapter adapter;
    private List<Parking> savedParkings;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    public SavedFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_saved_parkings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedParkings = new ArrayList<>();
        adapter = new SavedParkingsAdapter(getContext(), savedParkings);
        recyclerView.setAdapter(adapter);

        fetchSavedParkingsRealTime();
    }

    private void fetchSavedParkingsRealTime() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DocumentReference userRef = db.collection("customers").document(currentUser.getUid());
            listenerRegistration = userRef.addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    List<DocumentReference> savedParkingRefs = (List<DocumentReference>) documentSnapshot.get("savedParkings");
                    if (savedParkingRefs != null) {
                        savedParkings.clear();
                        for (DocumentReference ref : savedParkingRefs) {
                            ref.get().addOnSuccessListener(parkingSnapshot -> {
                                if (parkingSnapshot.exists()) {
                                    Parking parking = Parking.fromDocumentSnapshot(parkingSnapshot);
                                    savedParkings.add(parking);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
