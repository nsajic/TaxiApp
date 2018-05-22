package com.example.nsajic.testapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    EditText emailText;
    EditText passwordText;
    Button loginButton;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            SwitchToMainActivity();
        }

        emailText = (EditText)findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            userLogin();
        }else {
            // Toast.makeText();
        }
    }

    private void userLogin(){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please eneter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            SwitchToMainActivity();
                        }else{
                            Toast.makeText(LoginActivity.this, "Bad password!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void SwitchToMainActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
