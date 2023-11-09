package com.example.signuploginfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        // Retrieve any necessary data or initialize components
        // ...

        // Example: Set a click listener for a button in the comment activity
        Button submitButton = findViewById(R.id.save_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post=new Post();

                //todo: save the comment and update the list of comments
                // get the post_id
                // retrieve previous comments
                // add the post_id for the user id


            }
        });
    }
}
