package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailTemp, locationLink;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailTemp = findViewById(R.id.detailTemp);
        detailImage = findViewById(R.id.detailImage);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        locationLink = findViewById(R.id.locationLink);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve data from the bundle
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailTemp.setText(bundle.getString("Temperature"));
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

            key = bundle.getString("Key");


            if (key == null) {
                Toast.makeText(this, "Invalid key", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set up the database reference
        reference = FirebaseDatabase.getInstance().getReference("Posts").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    if (dataClass != null) {
                        String updatedLocation = dataClass.getCurrentLocation();
                        if (updatedLocation != null) {
                            locationLink.setText(updatedLocation);
                        } else {
                            String originalLocation = dataClass.getCurrentLocation();
                            locationLink.setText(originalLocation);
                        }
                        // Set the updated title, description, and temperature
                        detailTitle.setText(dataClass.getDatatitle());
                        detailDesc.setText(dataClass.getDatadesc());
                        detailTemp.setText(dataClass.getTemp());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle database error
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (key != null) {
                            reference.child(key).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                    if (error == null) {
                                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(DetailActivity.this, "Delete failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(DetailActivity.this, "Invalid key", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
                intent.putExtra("Key", key);
                intent.putExtra("Title", detailTitle.getText().toString());
                intent.putExtra("Description", detailDesc.getText().toString());
                intent.putExtra("Temperature", detailTemp.getText().toString());
                intent.putExtra("Image", imageUrl);
                startActivity(intent);
            }
        });




    }
}