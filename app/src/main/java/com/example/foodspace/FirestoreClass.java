package com.example.foodspace;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;

public class FirestoreClass {
    public static final String TAG = "MyActivity";
      String USERS = "users";
    FirebaseFirestore mFireStore=FirebaseFirestore.getInstance();


    void registerUser(SignUp activity, User userInfo){
        mFireStore.collection(USERS).document(userInfo.id).set(userInfo,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                activity.userRegistrationSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                activity.hideProgressDialog();
                Log.e(
                       TAG,
                        "Error while registering the user.",
                        e
                );
            }
        });



    }

}
