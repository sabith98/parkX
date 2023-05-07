package com.shatrend.parkx.fragments.parking;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shatrend.parkx.R;
import com.shatrend.parkx.adapters.ParkingSlotsHandlerCardAdapter;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.ParkingSlots;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParkingHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingHomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseHelper mDatabaseHelper;
    private int parkingId;

    private TextView tvFreeBikeSlots, tvFree3wheelerSlots, tvFree4wheelerSlots;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParkingHomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParkingHomeFragment newInstance(String param1, String param2) {
        ParkingHomeFragment fragment = new ParkingHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseHelper = new DatabaseHelper(getActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            parkingId = getArguments().getInt("parkingId");
//            Log.d("PARKING_ID", String.valueOf(parkingId));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parking_home, container, false);

        recyclerView = view.findViewById(R.id.fragment_parking_home_rv_parking);
        tvFreeBikeSlots = view.findViewById(R.id.fragment_parking_home_tv_bike_free_slots);
        tvFree3wheelerSlots = view.findViewById(R.id.fragment_parking_home_tv_3wheeler_free_slots);
        tvFree4wheelerSlots = view.findViewById(R.id.fragment_parking_home_tv_4wheeler_free_slots);

//        int parkingId = getArguments().getInt("parkingId");
        parkingId = getActivity().getIntent().getIntExtra("parkingId", -1);
//
        // Set available vehicle slots
        ParkingSlots parkingSlots = mDatabaseHelper.getParkingSlots(parkingId);

        tvFreeBikeSlots.setText(String.valueOf(parkingSlots.getBikeSlots()));
        tvFree3wheelerSlots.setText(String.valueOf(parkingSlots.getThreeWheelerSlots()));
        tvFree4wheelerSlots.setText(String.valueOf(parkingSlots.getFourWheelerSlots()));

        // Draw the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ParkingSlotsHandlerCardAdapter.Card> cards = new ArrayList<>();
        cards.add(new ParkingSlotsHandlerCardAdapter.Card("Motor bicycle"));
        cards.add(new ParkingSlotsHandlerCardAdapter.Card("Three wheeler"));
        cards.add(new ParkingSlotsHandlerCardAdapter.Card("Four wheeler"));

        ParkingSlotsHandlerCardAdapter adapter = new ParkingSlotsHandlerCardAdapter(cards);
        recyclerView.setAdapter(adapter);

        return view;
    }
}