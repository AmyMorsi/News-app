package com.example.signuploginfirebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Post {
    public String post_id;
    public List<String> comments;

    public void add_new_post(Post post){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Posts");
        usersRef.child("post_id").setValue(post_id);

    }

    public void add_new_comment(String post_id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Posts");
        usersRef.child("post_id").child("comments").setValue(post_id);

    }
}
