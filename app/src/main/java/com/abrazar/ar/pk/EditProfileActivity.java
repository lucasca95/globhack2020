package com.abrazar.ar.pk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {


    private EditText profileBio;
    private EditText profileAddress;
    private EditText profileWebsite;
    private EditText profileTelephone;
    private EditText profileEmail;

    private Button editProfile;

    private FirebaseAuth auth;
    private DatabaseReference workhub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        workhub = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        profileBio = (EditText) findViewById(R.id.editprofilebioET);
        profileAddress = (EditText) findViewById(R.id.editprofileaddressET);
        profileWebsite = (EditText) findViewById(R.id.editprofileWebsiteET);
        profileTelephone = (EditText) findViewById(R.id.editprofileTelephoneET);
        profileEmail = (EditText) findViewById(R.id.editprofileEmailET);

        editProfile = (Button) findViewById(R.id.editProfileBTN);

        workhub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileBio.setText((String) dataSnapshot.child("userBio").getValue());
                profileAddress.setText((String) dataSnapshot.child("userAddress").getValue());
                profileWebsite.setText((String) dataSnapshot.child("userWebsite").getValue());
                profileTelephone.setText((String) dataSnapshot.child("userTelephone").getValue());
                profileEmail.setText((String) dataSnapshot.child("userEmail").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio = profileBio.getText().toString();
                String address = profileAddress.getText().toString();
                String website = profileWebsite.getText().toString();
                String telephone = profileTelephone.getText().toString();
                String email = profileEmail.getText().toString();

                workhub.child("userBio").setValue(bio);
                workhub.child("userAddress").setValue(address);
                workhub.child("userWebsite").setValue(website);
                workhub.child("userTelephone").setValue(telephone);
                workhub.child("userEmail").setValue(email);

                Toast.makeText(EditProfileActivity.this, "Cambios guardados!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
