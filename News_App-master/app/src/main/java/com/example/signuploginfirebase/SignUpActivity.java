package com.example.signuploginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText SignupEmail,Signuppassword;
    private Button Signupbutton;
    private TextView LoginRedirecttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth=FirebaseAuth.getInstance();
        SignupEmail=findViewById(R.id.signup_Email);
        Signuppassword=findViewById(R.id.signup_Password);
        Signupbutton=findViewById(R.id.signup_button);
        LoginRedirecttext=findViewById(R.id.loginRedirect_text);

        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=SignupEmail.getText().toString().trim();
                String pass=Signuppassword.getText().toString().trim();
                if(user.isEmpty())
                {
                    SignupEmail.setError("Email cann't be empty");
                }
                if(pass.isEmpty())
                {

                    Signuppassword.setError("Password cann't be empty");
                }
                else{

                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUpActivity.this,"signup successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                User user=new User();
                                user.setUserId(auth.getUid());
                                user.add_to_firebase(user);

                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this,"signup Faild"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }
            }
        });
        LoginRedirecttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}