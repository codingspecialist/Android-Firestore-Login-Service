package com.cos.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = "SigninActivity";

    private UserService userService;

    private TextInputEditText etEmail, etPassword;
    private AppCompatButton btnSignin;
    private TextView linkSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mInit();
        mListener();
    }

    private void mInit(){
        userService = UserService.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignin = findViewById(R.id.btn_signin);
        linkSignup = findViewById(R.id.link_signup);
    }

    private void mListener(){
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                User user = User.builder()
                        .email(email)
                        .password(password)
                        .build();
                userService.signin(user, SigninActivity.this);
            }
        });

        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }


}
