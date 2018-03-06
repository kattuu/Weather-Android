package com.example.shourya.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class LoginActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set Dynamic Background
        setBackground();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //TODO: Redirect to Home Screen
            showToast("User already signedIn !");


            Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
           startActivity(intent);
            finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showToast(getString(R.string.loginfailure_google));
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_with_google_button:
                signIn();
                break;
            case R.id.login_button:
                EditText usernameEditext, passwordEdittext;
                usernameEditext = (EditText)findViewById(R.id.email_edittext);
                passwordEdittext = (EditText)findViewById(R.id.password_edittext);
                loginWithEmail(usernameEditext.getText().toString(),passwordEdittext.getText().toString());
                Log.d("email:",usernameEditext.getText().toString());
                Log.d("pass:",passwordEdittext.getText().toString());
                break;

            case R.id.signup_button:
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;


        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    showToast(getString(R.string.loginsuccess_google));
                    FirebaseUser user = mAuth.getCurrentUser();
                    // Go to Main Screen
                    startActivity(new Intent(getApplicationContext(),LaunchActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    showToast(getString(R.string.loginfailure_google));
                    Log.d("vishal",task.getException().toString());
                    //startActivity(new Intent(getApplicationContext(),Settings.class));
                }
            }
        });
    }

    // Dynamic Background
    private void setBackground(){
        if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            getWindow().setBackgroundDrawableResource(R.drawable.blur_beach_background) ;
        } else {
            getWindow().setBackgroundDrawableResource(R.drawable.blur_river_background) ;
        }
    }

    // Toast message
    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }



    private void loginWithEmail(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                   Intent email =new Intent(getApplicationContext(),LaunchActivity.class);
                    startActivity(email);

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}