package com.cos.firestore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainLog";
    private UserService userService;
    private FirebaseUser firebaseUser;
    private Button btnUser;
    private TextView tvUid, tvUsername, tvEmail, tvRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInit();
        mListener();
    }

    private void mInit(){
        userService = UserService.getInstance();
        btnUser = findViewById(R.id.btn_user);
        tvUid = findViewById(R.id.tv_uid);
        tvUsername = findViewById(R.id.tv_username);
        tvEmail = findViewById(R.id.tv_email);
        tvRole = findViewById(R.id.tv_role);
    }

    private void mListener(){
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.findByUid(firebaseUser.getUid(), new FirebaseCallback(){
                    @Override
                    public void callback(Object object) {
                        User user = (User) object;
                        tvUid.setText(user.getUid());
                        tvUsername.setText(user.getUsername());
                        tvEmail.setText(user.getEmail());
                        tvRole.setText(user.getRole());
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        
        if(firebaseUser == null){
            Log.d(TAG, "onStart: 인증되지 않은 사용자입니다.");
            finish();
        }else{
            Log.d(TAG, "onStart: 인증된 사용자입니다.");
        }
    }

}
