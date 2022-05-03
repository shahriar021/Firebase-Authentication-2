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
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            finish();
            return;
        }

        Button btnregister = findViewById(R.id.rgstrbtn);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        Button switchtologin = findViewById(R.id.jmploginbtn);
        switchtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTologin();
            }
        });

    }
    private void registerUser(){
        EditText etfirstname=findViewById(R.id.edirgstrname);
        EditText etlastname=findViewById(R.id.edirgstrlastname);
        EditText etregisteremail = findViewById(R.id.edirgstremail);
        EditText etregisterpassword = findViewById(R.id.edirgstrpassword);

        String firtstName = etfirstname.getText().toString();
        String lastName = etlastname.getText().toString();
        String email = etregisteremail.getText().toString();
        String password = etregisterpassword.getText().toString();

        if (firtstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "plz fill up all !", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(firtstName,lastName,email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showMainActivity();
                                }
                            });
                        } else {
                            Toast.makeText(register.this, "Authentication error!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showMainActivity(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void switchTologin(){
        Intent i = new Intent(this,Login.class);
        startActivity(i);
        finish();
    }

}