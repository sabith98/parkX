package com.shatrend.parkx.fragments.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.customer.BookingActivity;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.Parking;
import com.shatrend.parkx.models.ParkingLocation;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private static final int LOCATION_PERMISSION_CODE = 101;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;

    private FirebaseFirestore db;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Customizing google map controls
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);

            // Changing current location button's position to bottom
            if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
                View locationButton = ((View) mapView.findViewById(Integer
                        .parseInt("1"))
                        .getParent())
                        .findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 40, 280);
            }

            // check whether gps is enabled or not and then request user to enable it
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000)
                    .setFastestInterval(5000)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

            task.addOnSuccessListener(getActivity(), locationSettingsResponse -> getDeviceLocation());

            task.addOnFailureListener(getActivity(), e -> {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Fetch parking locations from Firbase firestore db
            fetchParkings(googleMap);

        }
    };

    private void fetchParkings(GoogleMap googleMap) {
        db.collection("parkings")
                .get()
                .addOnCompleteListener(fetchTask -> {
                    if (fetchTask.isSuccessful()) {
                        for (QueryDocumentSnapshot document : fetchTask.getResult()) {
                            Parking parking = Parking.fromDocumentSnapshot(document);
                            LatLng location = new LatLng(parking.getLocation().getLatitude(), parking.getLocation().getLongitude());
                            Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(parking.getName()));
                            marker.setSnippet("Rs." + parking.getPrice());
                            marker.setTag(parking);

                            marker.showInfoWindow();
                        }
                    } else {
                        Log.d("FETCH_LOCATION_ERROR", "Error getting documents: ", fetchTask.getException());
                    }
                });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoView = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                TextView tvParkingName = infoView.findViewById(R.id.tv_parking_name);
                TextView tvParkingPrice = infoView.findViewById(R.id.tv_parking_price);

                Parking parking = (Parking) marker.getTag();

                if (parking != null) {
                    tvParkingName.setText(parking.getName());
                    tvParkingPrice.setText(marker.getSnippet());
                }

                return infoView;
            }
        });

        googleMap.setOnInfoWindowClickListener(marker -> {
            Parking parking = (Parking) marker.getTag();
            if (parking != null) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                intent.putExtra("parking", parking);
                intent.putParcelableArrayListExtra("parking_slots", new ArrayList<>(parking.getSlots()));
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        if (isLocationPermissionGranted()) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(callback);
                mapView = mapFragment.getView();
            }
        }else {
            requestLocationPermission();
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Places.initialize(getActivity(), "${MAPS_API_KEY}");
        placesClient = Places.createClient(getActivity());
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
    }

    // Checking whether location permission granted or not
    private boolean isLocationPermissionGranted(){
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.
                ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }
    // Requesting location permission
    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(getActivity(),new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
    }

    // Function to get the current location
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
            .addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = task.getResult();
                        if (mLastKnownLocation != null) {
                            double latitude = mLastKnownLocation.getLatitude();
                            double longitude = mLastKnownLocation.getLongitude();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                    latitude,
                                    longitude
                            ),18.0f));
                        }else {
                            LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setInterval(10000)
                                    .setFastestInterval(5000)
                                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                            locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    if (locationResult == null) {
                                        return;
                                    }
                                    mLastKnownLocation = locationResult.getLastLocation();
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                            mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()
                                    ),18.0f));
                                    mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                }
                            };
                            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Unable to get last location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}