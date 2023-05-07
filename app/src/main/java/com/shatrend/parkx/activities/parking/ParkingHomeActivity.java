package com.shatrend.parkx.activities.parking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.shatrend.parkx.R;
import com.shatrend.parkx.databinding.ActivityParkingHomeBinding;
import com.shatrend.parkx.fragments.parking.ParkingHomeFragment;
import com.shatrend.parkx.fragments.parking.ParkingProfileFragment;

public class ParkingHomeActivity extends AppCompatActivity {

    ActivityParkingHomeBinding binding;

    int parkingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityParkingHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

//        parkingId = getIntent().getIntExtra("parkingId", -1);
//
//        ParkingHomeFragment phFragment = new ParkingHomeFragment();
//        Bundle args = new Bundle();
//        args.putInt("myData", parkingId);
//        phFragment.setArguments(args);

        replaceFragment(new ParkingHomeFragment());

        binding.bottomNavParking.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.bottom_nav_parking_home:
                    replaceFragment(new ParkingHomeFragment());
                    break;
                case R.id.bottom_nav_parking_profile:
                    replaceFragment(new ParkingProfileFragment());
                    break;
            }
            return true;
        });
    }

    // Replace current fragment with a new fragment according to selection
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_parking, fragment);
        fragmentTransaction.commit();
    }
}