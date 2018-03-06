package com.example.shourya.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.annotation.NonNull;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{

    //The view objects
    private EditText mEditTextName, mEditTextEmail, mEditTextMobile, mEditTextPassword;

    //defining AwesomeValidation object
    private AwesomeValidation mAwesomeValidation;

    // Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //initializing awesomevalidation object
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //initializing view objects
        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditTextMobile = (EditText) findViewById(R.id.editTextMobile);

        //adding validation to edittexts
        //TODO: Add extra validation of Email
        mAwesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        mAwesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        //mAwesomeValidation.addValidation(this,R.id.editTextPassword,"/^.{6,}$/",R.string.passworderror);
        mAwesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
    }

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successful
        if (mAwesomeValidation.validate()) {
            //show toast if successful submit
            //Toast.makeText(this, "Validation Successful", Toast.LENGTH_SHORT).show();
            createAccount(mEditTextEmail.getText().toString(),mEditTextPassword.getText().toString());
        }
    }

    private void createAccount(final String email, final String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getApplicationContext(),"Registration Success !",Toast.LENGTH_SHORT).show();
                    //TODO: Add profile information in DB
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    System.out.println(task.getException());
                }
            }
        });
    }

    public void onClick(View view) {
        if(view.getId() == R.id.text_login) {
            //go to login activity
            finish();
        }
        if (view.getId() == R.id.buttonRegister) {
            submitForm();
        }
    }
}
