package ca.friends.and.co.it.SmartParking;

//Ramesh Narayan Gaire N01452430, ONA
//Roshan Shrestha N01457532, ONA
// Rushi Bhandari N01464259, ONA
// Komal Bamotra N01426087,ONA

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ca.friends.and.co.it.SmartParking.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    ImageView googleLogo;
    Button btnLogin;
    CheckBox remember;
    //Google login
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;



    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        remember = findViewById(R.id.remember);
        googleLogo = findViewById(R.id.googleID);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        SharedPreferences preferences1 = getSharedPreferences(getString(R.string.checkbox1),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences1.edit();
        String checkbox1 = preferences1.getString(getString(R.string.rememberMe), "");
        if(checkbox1.equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class );
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.please_in_sing, Toast.LENGTH_SHORT).show();
        }


        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){


                    editor.putString(getString(R.string.remember_me_2), "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, R.string.checked, Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked()){


                    editor.putString(getString(R.string.remember_me_too), "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, R.string.uncheck, Toast.LENGTH_SHORT).show();

                }

            }
        });

        //Google sign in progess
        googleLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInProcess();
            }
        });
    }
    public void googleSignInProcess(){
        Intent googleSignInIntent = gsc.getSignInIntent();
        startActivityForResult(googleSignInIntent,1000);
    }

    private void loginUser(){
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);    //used for facebook
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                changingActivity();
            } catch (ApiException e) {
                //Log.d(getString(R.string.exception), String.valueOf(e));
                Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changingActivity(){
        finish();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

}