package com.shatrend.parkx.fragments.customer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.customer.CustomerLoginActivity;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView tvEmail, tvName, tvPhone;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvEmail = view.findViewById(R.id.tv_email);
        tvName = view.findViewById(R.id.tv_name);
        tvPhone = view.findViewById(R.id.tv_phone);
        Button btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        Button btnSignOut = view.findViewById(R.id.btn_sign_out);

        loadProfile();

        btnEditProfile.setOnClickListener(v -> showEditProfileDialog());
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), CustomerLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    private void loadProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            tvEmail.setText(email);

            db.collection("customers").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String phone = documentSnapshot.getString("phone");

                            tvName.setText(name != null ? name : "Name");
                            tvPhone.setText(phone != null ? phone : "Phone");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etPhone = dialogView.findViewById(R.id.et_phone);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);

        etName.setText(tvName.getText());
        etPhone.setText(tvPhone.getText());

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();

            updateProfile(name, phone);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateProfile(String name, String phone) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            Map<String, Object> profileData = new HashMap<>();
            profileData.put("name", name);
            profileData.put("phone", phone);

            db.collection("customers").document(uid)
                    .set(profileData)
                    .addOnSuccessListener(aVoid -> {
                        tvName.setText(name);
                        tvPhone.setText(phone);
                        Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
