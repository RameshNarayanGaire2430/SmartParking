package ca.friends.and.co.it.smartparking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegPasswordConfirm;
    TextView tvLoginHere;
    Boolean validated = false;
    Button btnRegister;

    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        etRegPasswordConfirm = findViewById(R.id.etRegPassConfirm);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void createUser(){
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String passwordConfirm = etRegPasswordConfirm.getText().toString();

        if(validated==false) {
            if (TextUtils.isEmpty(email)) {
                etRegEmail.setError("Email cannot be empty");
                etRegEmail.requestFocus();
            } else if (isValidPassword(password)) {
                etRegPassword.setError("Please enter at least !@#$% ... and at least one Capital letter");
                etRegPassword.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                etRegPassword.setError("Password cannot be empty");
                etRegPassword.requestFocus();
            } else if (TextUtils.isEmpty(passwordConfirm)) {
                etRegPasswordConfirm.setError("Confirm Your Password");
                etRegPasswordConfirm.requestFocus();
            } else if (!(passwordConfirm.matches(password))) {
                etRegPasswordConfirm.setError("Password Does not match!");
                etRegPasswordConfirm.requestFocus();
            } else if (etRegPassword.length() < 8) {
                etRegPassword.setError("Password should be more then 8 characters");
                etRegPassword.requestFocus();
            }
            validated = true;
        }else{
            registerUser(email,password,validated);
        }
    }

    private void registerUser(String email, String password,boolean validated) {
        if(validated) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

}

}