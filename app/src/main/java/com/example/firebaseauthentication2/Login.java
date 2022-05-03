package com.example.firebaseauthentication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button btnLogin = findViewById(R.id.btnloginform);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationUser();
            }
        });

        Button jumptotregister = findViewById(R.id.btnregisterfromlogin);
        jumptotregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swtichtoregister();
            }
        });
    }

    private void authenticationUser(){
        EditText etloginemail = findViewById(R.id.etloginemail);
        EditText etloginpassword = findViewById(R.id.edirgstrpassword);

        String email = etloginemail.getText().toString();
        String password = etloginpassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "plz fill all fields !!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                showMainActivity();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showMainActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void swtichtoregister(){
        Intent i = new Intent(this,register.class);
        startActivity(i);
        finish();
    }
}