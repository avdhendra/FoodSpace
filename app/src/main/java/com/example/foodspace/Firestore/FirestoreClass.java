package com.example.foodspace.Firestore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.example.foodspace.Constants;
import com.example.foodspace.FirebaseModels.User;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.example.foodspace.ui.Activities.LoginSignUp.MainActivity;
import com.example.foodspace.ui.Activities.LoginSignUp.NameOfUser;
import com.example.foodspace.ui.Activities.LoginSignUp.OtpNumber;
import com.example.foodspace.ui.Activities.LoginSignUp.PhoneAuth;
import com.example.foodspace.ui.Activities.LoginSignUp.SignUp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;

public class FirestoreClass {
    public static final String TAG = "MyActivity";
    public Constants constants = new Constants();
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();


    //function to create userInfo inside the firebasefirstore cloud
    public void registerUser(Activity activity, User userInfo) {
        //if the activity is of Signup
        if (activity instanceof SignUp) {
            mFireStore.collection(constants.user).document(userInfo.id).set(userInfo, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {
                    //on successfully created the collection

                    ((SignUp) activity).userRegistrationSuccess();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //if the task is failed to save the data
                    //hide the progress Dialog
                    ((SignUp) activity).hideProgressDialog();
                    //show the error message
                    Log.e(
                            TAG,
                            "Error while registering the user.",
                            e
                    );
                }
            });

        } else if (activity instanceof OtpNumber) {
            mFireStore.collection(constants.user).document(userInfo.id).set(userInfo, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {
                    ((OtpNumber) activity).newPhoneUser();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, e.getMessage());
                    ((OtpNumber) activity).hideProgressDialog();
                }
            });
        }


    }

    public String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = "";
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }
        return currentUserId;
    }

    /**
     * A function to get the logged user details from from FireStore Database.
     */

    public void getUserDetails(Activity activity) {
        //Here we pass the collection name from which we want the data
        mFireStore.collection(constants.user).
                //The document id to get the fields of user
                        document(getCurrentUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Here we have received the document snapshot which is converted into User data model object
                User user = documentSnapshot.toObject(User.class);
                if (activity instanceof MainActivity) {
                    if (user != null) {
                        ((MainActivity) activity).userLoggedInSuccess(user);
                    }

                } else if (activity instanceof OtpNumber) {
                    if (user != null) {
                        ((OtpNumber) activity).showHomeActivity(user);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).hideProgressDialog();
                }
            }
        });
    }

    public void uploadImageCloudStorage(Activity activity, Uri mSelectedImageFileUri) {

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            if (activity instanceof NameOfUser) {
                StorageReference sRef = FirebaseStorage.getInstance().getReference().child(constants.PROFILE_IMAGE_UPLOAD + "/" + uid);//+"."+constants.getFileExtension(activity,mSelectedImageFileUri)
                sRef.putFile(mSelectedImageFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("Firebase Image URL", Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString());

                        Objects.requireNonNull(taskSnapshot.getMetadata().getReference()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("Download Image URL", uri.toString());
                                /*activity.imageUploadSuccess(uri.toString());*/
                                ((NameOfUser) activity).imageUploadSuccess(uri.toString());
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ((NameOfUser) activity).hideProgressDialog();
                        Log.e("Error to upload", e.getMessage(), e);
                    }
                });
            }


        }



    public void updateUserProfileData(Activity activity, Map<String, Object> userHashMap) {
        mFireStore.collection(constants.user).document(getCurrentUserId()).update(userHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (activity instanceof NameOfUser) {
                    ((NameOfUser) activity).userProfileUpdateSuccess();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (activity instanceof NameOfUser) {
                    ((NameOfUser) activity).hideProgressDialog();
                }
                Log.e("Error updating User", e.getMessage(), e);
            }
        });
    }

    public void setSharedPreferenceShow(Activity activity) {
        mFireStore.collection(constants.user).document(getCurrentUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    Log.d("profile image", user.image);
                    SharedPreferences sharedPreferences = activity.getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putString(constants.LOGGED_IN_USERNAME, user.firstname);
                    editor.putString(constants.LOGGED_IN_EMAIL, user.email);
                    editor.putString(constants.LOGGED_IN_PHONE, user.mobile);
                    editor.putString(constants.USER_PROFILE_IMAGE, user.image);
                    editor.apply();


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Profile Not Upload", e.getMessage());
            }
        });
    }




}
