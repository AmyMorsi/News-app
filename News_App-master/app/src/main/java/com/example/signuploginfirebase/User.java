package com.example.signuploginfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User {
    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public void setSavedPostIds(List<String> savedPostIds) {
        this.savedPostIds = savedPostIds;
    }

    private List<String> savedPostIds;

    public User() {
        // Default constructor required for Firebase database operations
    }

    public User(String userId, List<String> savedPostIds) {
        this.userId = userId;
        this.savedPostIds = savedPostIds;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getSavedPostIds() {
        return savedPostIds;
    }

    public void add_to_firebase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        usersRef.child(userId).setValue(user.userId);

    }
    public void add_to_saved_posts(String userId ,String post_id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        DatabaseReference userRef = usersRef.child(userId).child("savedPostIds");
        userRef.setValue(post_id);
    }
    public void get_from_firebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        DatabaseReference userRef = usersRef.child(userId);

// Read the user data from the database
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    // Access the user object and perform further operations
                } else {
                    // User data does not exist
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });

    }
}
