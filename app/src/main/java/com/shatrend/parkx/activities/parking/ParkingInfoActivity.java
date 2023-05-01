package com.shatrend.parkx.activities.parking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.driver.HomeActivity;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.Parking;
import com.shatrend.parkx.models.ParkingInfo;
import com.shatrend.parkx.models.ParkingLocation;
import com.shatrend.parkx.models.ParkingRate;
import com.shatrend.parkx.models.ParkingSlots;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ParkingInfoActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

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
                    getCurrentLocation();
            }
        });

        btnUpdate = findViewById(R.id.activity_parking_info_btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                int parkingId = getIntent().getIntExtra("parking_id", -1);
//                int parkingId = (int) parkingIdLong;

//                double latitude = locationValues.get("latitude");
//                double longitude = locationValues.get("longitude");

                double latitude = 8.720167;
                double longitude = 81.173362;

                String bikeRateStr = etBike.getText().toString();
                int bikeRate = Integer.parseInt(etBike.getText().toString());
                int threeWheelerRate = Integer.parseInt(et3wheeler.getText().toString());
                int fourWheelerRate = Integer.parseInt(et4wheeler.getText().toString());

                int bikeSlots = Integer.parseInt(etBikeSlots.getText().toString());
                int threeWheelerSlots = Integer.parseInt(et3wheelerSlots.getText().toString());
                int fourWheelerSlots = Integer.parseInt(et4wheelerSlots.getText().toString());

                ParkingInfo parkingInfo = new ParkingInfo(parkingId,name,phone,parkingImageBytes);
                ParkingLocation parkingLocation = new ParkingLocation(parkingId,latitude,longitude);
                ParkingRate parkingRate = new ParkingRate(parkingId,bikeRate,threeWheelerRate,fourWheelerRate);
                ParkingSlots parkingSlots = new ParkingSlots(parkingId,bikeSlots,threeWheelerSlots,fourWheelerSlots);

                boolean success = databaseHelper.addParkingInfo(parkingId,parkingInfo,parkingLocation,parkingRate,parkingSlots);

                if (success) {
                    Toast.makeText(ParkingInfoActivity.this, "Parking info updated", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(ParkingInfoActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(ParkingInfoActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
//                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(phone)) {
//                    Parking parking = new Parking(0, email, password);
//                    long parkingIdLong = mDatabaseHelper.addParking(parking);
//                    int parkingId = (int) parkingIdLong;
//
//                    if (parkingId != -1) {
//                        Toast.makeText(ParkingRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
//                        Intent parkingInfoIntent = new Intent(ParkingRegisterActivity.this, ParkingInfoActivity.class);
//                        parkingInfoIntent.putExtra("parking_id", parkingId);
//                        startActivity(parkingInfoIntent);
//                    } else {
//                        Toast.makeText(ParkingRegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(ParkingRegisterActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
//                }

//                if (TextUtils.isEmpty(name)) {
//                    etName.requestFocus();
//                    etName.setError("Parking name cannot be empty");
//
//                } else if (TextUtils.isEmpty(phone)) {
//                    etPhone.requestFocus();
//                    etPhone.setError("Parking phone no cannot be empty");
//
//                }
//                else if (!TextUtils.isEmpty(bikeRate)) {
//
//                }
            }
        });
    }

//    private boolean validateInfo(String name,String phone,int bikeRate,int threeWheelerRate,int fourWheelerRate) {
//        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone))
//    }

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

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] imageBytes = stream.toByteArray();

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
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(ParkingInfoActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Get the last known location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                String locationString = "Latitude: " + latitude +
                                        "\nLongitude: " + longitude;
                                tvLocationInfo.setText(locationString);

                                locationValues.put("latitude", latitude);
                                locationValues.put("longitude", longitude);

                            } else {
                                tvLocationInfo.setText("Location is null");
                            }
                        }
                    });
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(ParkingInfoActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
//        return locationValues;
    }

}