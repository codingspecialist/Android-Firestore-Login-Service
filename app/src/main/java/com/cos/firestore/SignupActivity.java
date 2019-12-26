package com.cos.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private UserService userService;

    private TextInputEditText etEmail, etPassword, etUsername;
    private AppCompatButton btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mInit();
        mListener();
    }

    private void mInit(){
        userService = UserService.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);
        btnSignup = findViewById(R.id.btn_signup);
    }

    private void mListener(){
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String username = etUsername.getText().toString();

                // Builder 패턴 (생성자는 값 입력시 검증이 안됨)
                User user = User.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .role(Role.USER)
                        .build();

                userService.signup(user, SignupActivity.this);
            }
        });
    }
}
