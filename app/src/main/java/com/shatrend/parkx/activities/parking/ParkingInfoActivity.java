package com.shatrend.parkx.activities.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.shatrend.parkx.R;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.ParkingInfo;
import com.shatrend.parkx.models.ParkingLocation;
import com.shatrend.parkx.models.ParkingRate;
import com.shatrend.parkx.models.ParkingSlots;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ParkingInfoActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private LocationManager locationManager;
    private Location myLocation = new Location("provider");

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView ivImage;
    private EditText etName, etPhone, et4wheeler, et3wheeler, etBike;
    private EditText etBikeSlots, et3wheelerSlots, et4wheelerSlots;
    private Button btnGetLocation, btnUpdate;
    private ImageButton ibCamera, ibGallery;
    private TextView tvLocationInfo;

    DatabaseHelper databaseHelper;
    Map<String, Double> locationValues = new HashMap<String, Double>();
    byte[] parkingImageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_info);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ivImage = findViewById(R.id.activity_parking_info_iv_image);
        etName = findViewById(R.id.activity_parking_info_et_name);
        etPhone = findViewById(R.id.activity_parking_info_et_phone);
        et4wheeler = findViewById(R.id.activity_parking_info_et_4wheeler);
        et3wheeler = findViewById(R.id.activity_parking_info_et_3wheeler);
        etBike = findViewById(R.id.activity_parking_info_et_bike);
        etBikeSlots = findViewById(R.id.activity_parking_info_et_bike_slots);
        et3wheelerSlots = findViewById(R.id.activity_parking_info_et_3wheeler_slots);
        et4wheelerSlots = findViewById(R.id.activity_parking_info_et_4wheeler_slots);
        tvLocationInfo = findViewById(R.id.activity_parking_info_tv_location_info);

        databaseHelper = new DatabaseHelper(this);

        ibCamera = findViewById(R.id.activity_parking_info_ib_camera);
        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        ibGallery = findViewById(R.id.activity_parking_info_ib_gallery);
        ibGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPickImageIntent();
            }
        });

        btnGetLocation = findViewById(R.id.activity_parking_info_btn_get_location);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myLocation = getLocation();
//                ***
                requestLocationUpdates();
//                if (myLocation != null) {
//                    tvLocationInfo.setText(
//                            "Latitude: " + myLocation.getLatitude() + "\n"
//                                    + "Longitude: " + myLocation.getLongitude()
//                    );
//                }
//                else {
//                    tvLocationInfo.setText("Try again");
//                    tvLocationInfo.setError("Try again");
//                }

            }
        });

        //        ***
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Location location = locationResult.getLastLocation();
                // Handle the received location
//                Toast.makeText(ParkingInfoActivity.this, "Latitude: " + location.getLatitude() +
//                        "\nLongitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();

                myLocation.setLatitude(location.getLatitude());
                myLocation.setLongitude(location.getLongitude());

                tvLocationInfo.setText(
                        "Latitude: " + location.getLatitude() + "\n"
                        + "Longitude: " + location.getLongitude()
                );
            }
        };
//      ***

        btnUpdate = findViewById(R.id.activity_parking_info_btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                int parkingId = getIntent().getIntExtra("parkingId", -1);

//                double latitude = locationValues.get("latitude");
//                double longitude = locationValues.get("longitude");

                double latitude = myLocation.getLatitude();
                double longitude = myLocation.getLongitude();

                // Inserting dummy location data for 3rd progress review only
//                double latitude = 8.720183;
//                double longitude = 81.174187;

                String bikeRateStr = etBike.getText().toString();
                String threeWheelerRateStr = et3wheeler.getText().toString();
                String fourWheelerRateStr = et3wheeler.getText().toString();

                String bikeSlotsStr = etBikeSlots.getText().toString();
                String threeWheelerSlotsStr = etBikeSlots.getText().toString();
                String fourWheelerSlotsStr = etBikeSlots.getText().toString();

                // Assigning 0 if there was no input given
                int bikeRate = 0;
                if (!bikeRateStr.isEmpty()) {
                    bikeRate = Integer.parseInt(etBike.getText().toString());

//                    etBike.requestFocus();
//                    etBike.setText("0");
//                    return;
                }
                int bikeSlots = 0;
                if (!bikeSlotsStr.isEmpty()) {
                    bikeSlots = Integer.parseInt(etBikeSlots.getText().toString());
//                    etBikeSlots.requestFocus();
//                    etBikeSlots.setText("0");
//                    return;
                }
                int threeWheelerRate = 0;
                if (!threeWheelerRateStr.isEmpty()) {
                    threeWheelerRate = Integer.parseInt(et3wheeler.getText().toString());
//                    et3wheeler.requestFocus();
//                    et3wheeler.setText("0");
//                    return;
                }
                int threeWheelerSlots = 0;
                if (!threeWheelerSlotsStr.isEmpty()) {
                    threeWheelerSlots = Integer.parseInt(et3wheelerSlots.getText().toString());
//                    et3wheelerSlots.requestFocus();
//                    et3wheelerSlots.setText("0");
//                    return;
                }
                int fourWheelerRate = 0;
                if (!fourWheelerRateStr.isEmpty()) {
                    fourWheelerRate = Integer.parseInt(et4wheeler.getText().toString());
//                    et4wheeler.requestFocus();
//                    et4wheeler.setText("0");
//                    return;
                }
                int fourWheelerSlots = 0;
                if (!fourWheelerSlotsStr.isEmpty()) {
                    fourWheelerSlots = Integer.parseInt(et4wheelerSlots.getText().toString());
//                    et4wheelerSlots.requestFocus();
//                    et4wheelerSlots.setText("0");
//                    return;
                }

                ParkingInfo parkingInfo = new ParkingInfo(parkingId,name,phone,parkingImageBytes);
                ParkingLocation parkingLocation = new ParkingLocation(parkingId,latitude,longitude);
                ParkingRate parkingRate = new ParkingRate(parkingId,bikeRate,threeWheelerRate,fourWheelerRate);
                ParkingSlots parkingSlots = new ParkingSlots(parkingId,bikeSlots,threeWheelerSlots,fourWheelerSlots);

                boolean success = databaseHelper.addParkingInfo(parkingId,parkingInfo,parkingLocation,parkingRate,parkingSlots);

                if (success) {
                    Toast.makeText(ParkingInfoActivity.this, "Parking info updated", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(ParkingInfoActivity.this, ParkingHomeActivity.class);
                    homeIntent.putExtra("parkingId", parkingId);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(ParkingInfoActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to Dispatch picture taking intent
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            start
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to dispatch Image picking intent
    private void dispatchPickImageIntent() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickImageIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK);
        } else {
            Toast.makeText(this, "No gallery app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImage.setImageBitmap(imageBitmap);

            parkingImageBytes = imageToBytes(imageBitmap);

        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(projection[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(this, "Error retrieving image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Error retrieving image", Toast.LENGTH_SHORT).show();
        }
    }

    // Convert a image to bytes
    private byte[] imageToBytes(Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        return imageBytes;
    }

    // Function to Get the current location of the parking
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation() {
//        // Check for location permissions
//        if (ContextCompat.checkSelfPermission(ParkingInfoActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            // Get the last known location
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            Log.d("LOCATION", String.valueOf(location));
////                            Log.d("LATITUDE", String.valueOf(location.getLatitude()));
////                            Log.d("LONGITUDE", String.valueOf(location.getLongitude()));
//                            if (location != null) {
//                                double latitude = location.getLatitude();
//                                double longitude = location.getLongitude();
//                                String locationString = "Latitude: " + latitude +
//                                        "\nLongitude: " + longitude;
//                                tvLocationInfo.setText(locationString);
//
//                                locationValues.put("latitude", latitude);
//                                locationValues.put("longitude", longitude);
//
//                            } else {
//                                tvLocationInfo.setText("Location is null");
//                            }
//                        }
//                    });
//        } else {
//            // Request location permissions
//            ActivityCompat.requestPermissions(ParkingInfoActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    1);
//        }
//    }

    public Location getLocation() {
        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return null;
        }

        // Get last known location
        @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Log.d("LAST_KNOWN_LOCATION", String.valueOf(lastKnownLocation));
        // Update UI with current location
        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            Location currentLocation = new Location("");
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);
            return currentLocation;
        } else {
            Toast.makeText(this, "Could not get current location", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

//    ***
    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop receiving location updates
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}