package com.cos.firestore;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserService {

    private static final String TAG = "UserService";
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static UserService userService = new UserService();

    private UserService() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserService getInstance() {
        return userService;
    }

    public void signup(final User user, final SignupActivity signupActivity) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            save(user, signupActivity);
                        } else {
                            Toast.makeText(signupActivity, "회원가입 실패 : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void save(final User user, final SignupActivity signupActivity) {

        // uid 동기화 시켜주기
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(firebaseUser.getUid());
        user.setUid(docRef.getId());

        docRef.set(user)
                .addOnSuccessListener(signupActivity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(signupActivity, "회원가입 완료", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "save: user : " + user);
                        signupActivity.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signupActivity, "회원가입 실패 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signin(final User user, final SigninActivity signinActivity) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(signinActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signinActivity, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signinActivity, MainActivity.class);
                            signinActivity.startActivity(intent);
                        } else {
                            Toast.makeText(signinActivity, "로그인 실패 : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void findByUid(final String uid, final FirebaseCallback firebaseCallback){


        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                firebaseCallback.callback(user);
            }
        });
    }
}
