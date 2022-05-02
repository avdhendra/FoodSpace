package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.module.AppGlideModule;
import com.example.foodspace.Constants;
import com.example.foodspace.FirebaseModels.User;
import com.example.foodspace.Firestore.FirestoreClass;
import com.example.foodspace.GlideLoader;
import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NameOfUser extends BaseActivity {

    private TextInputEditText Name, email, description, phonenumber;
    private CircleImageView img1;
    private Constants constants;
    private User user;
    private Uri mSelectedImageFileUri = null;
    private String mUserProfileImageURL = "";
    String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_of_user);
        constants = new Constants();
        Name = findViewById(R.id.nameUser);
        description = findViewById(R.id.tellus);
        phonenumber = findViewById(R.id.PhoneNumber);
        img1 = findViewById(R.id.user_image);
        email = findViewById(R.id.email);
        dosharedPreference();
    }

    private void dosharedPreference() {
        /*SharedPreferences sharedPreferences = getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
        String phonePref = sharedPreferences.getString(constants.LOGGED_IN_PHONE, "");*/
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            activity = extras.getString("Verfication");

        }
        if (getIntent().hasExtra(constants.EXTRA_USER_DETAILS)) {

            user = getIntent().getParcelableExtra(constants.EXTRA_USER_DETAILS);
        }

        if (activity.equals("email")) {
            if (user.profileCompleted == 0) {
                Name.setEnabled(false);
                email.setEnabled(false);
                phonenumber.setEnabled(false);
                Name.setText(user.firstname);
                email.setText(user.email);
                phonenumber.setText(user.mobile);
                description.setText(user.Description);
            } else {
                Name.setEnabled(false);
                email.setEnabled(false);
                phonenumber.setEnabled(false);
                Name.setText(user.firstname);
                email.setText(user.email);
                phonenumber.setText(user.mobile);
                description.setText(user.Description);
                Glide.with(this).load(user.image).centerCrop().placeholder(R.drawable.defaultavatar).into(img1);
            }

        } else if (activity.equals("phone")) {
            if (user.profileCompleted == 0) {
                phonenumber.setEnabled(false);
                phonenumber.setText(user.mobile);
                Name.setText(user.firstname);
                email.setText(user.email);
                description.setText(user.Description);


            } else {
                phonenumber.setEnabled(false);

                phonenumber.setText(user.mobile);
                Name.setText(user.firstname);
                email.setText(user.email);
                description.setText(user.Description);
                Glide.with(this).load(user.image).centerCrop().placeholder(R.drawable.defaultavatar).into(img1);
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NameOfUser.this, PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }


    public void Continue(View view) {
        if (validateUserProfileDetails()) {
            showProgressDialog("Please Wait...");
            if (mSelectedImageFileUri != null) {
                Toast.makeText(this, "uploadImage", Toast.LENGTH_LONG).show();
                new FirestoreClass().uploadImageCloudStorage(this, mSelectedImageFileUri);
            }
            else {
                Toast.makeText(this, "UpdateUserProfileDetail", Toast.LENGTH_LONG).show();
                updateUserProfileDetails();
            }
        }

    }


    private boolean validateUserProfileDetails() {
        if (TextUtils.isEmpty(Objects.requireNonNull(Name.getText()).toString().trim())) {

            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_first_name), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(email.getText()).toString().trim())) {


            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(description.getText()).toString().trim())) {


            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_description), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(phonenumber.getText()).toString().trim())) {
            showErrorSnackBar(
                    getResources().getString(R.string.err_msg_enter_mobile_number),
                    true
            );
            return false;
        } else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Select the image Options").setPositiveButton("Capture", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                CapturePermission();
            }
        }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ImagePermission();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorSnackBarSuccess));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorSnackBarSuccess));

            }
        });
        dialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CapturePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            constants.CameraCapture(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ImagePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            constants.pickImageFromGallery(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, constants.READ_STORAGE_PERMISSION_CODE);
            ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)}, constants.WRITE_STORAGE_PERMISSION_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                constants.pickImageFromGallery(this);
            } else {
                showErrorSnackBar("Gallery access denied", true);
            }
        } else if (requestCode == constants.TAKE_IMAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                constants.CameraCapture(this);
            } else {
                showErrorSnackBar("Camera access denied", true);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    mSelectedImageFileUri = data.getData();
                    new GlideLoader(this).loadUserPicture(mSelectedImageFileUri, img1);


                }
            } else if (requestCode == constants.TAKE_IMAGE_CODE) {

                if (data != null) {
                    mSelectedImageFileUri = data.getData();
                    Glide.with(this).load(mSelectedImageFileUri).into(img1);
                }

            }
        } else if (requestCode == Activity.RESULT_CANCELED) {
            Log.e("Request Cancelled", "Image selection cancelled");
        }

    }

    public void imageUploadSuccess(String imageURL) {
        mUserProfileImageURL = imageURL;
        updateUserProfileDetails();
    }

    private void updateUserProfileDetails() {
        Map<String, Object> userHashMap = new HashMap<>();
        String name = Objects.requireNonNull(Name.getText()).toString().trim();
        if (!name.equals(user.firstname) && !name.isEmpty()) {
            userHashMap.put(constants.FIRST_NAME, name);
        }

        String userEmail = Objects.requireNonNull(email.getText()).toString().trim();
        if (!userEmail.equals(user.email) && !userEmail.isEmpty()) {
            userHashMap.put(constants.EMAIL, userEmail);
        }

        String mobileNumber = Objects.requireNonNull(phonenumber.getText()).toString().trim();
        if (mobileNumber.length() == 10 && !mobileNumber.equals(user.mobile)) {
            userHashMap.put(constants.MOBILE, mobileNumber);
        }
        String descriptionEdit = Objects.requireNonNull(description.getText()).toString().trim();
        if (!descriptionEdit.equals(user.Description) && !descriptionEdit.isEmpty()) {
            userHashMap.put(constants.DESCRIPTION, descriptionEdit);
        }
        if (!mUserProfileImageURL.isEmpty()) {
            userHashMap.put(constants.IMAGE, mUserProfileImageURL);
        }

        if (user.profileCompleted == 0 && !mUserProfileImageURL.isEmpty() && mobileNumber.length()==10 && !userEmail.isEmpty() && !name.isEmpty() && !descriptionEdit.isEmpty()) {
            userHashMap.put(constants.COMPLETE_PROFILE, 1);
        }
        new FirestoreClass().updateUserProfileData(this, userHashMap);
        new FirestoreClass().setSharedPreferenceShow(this);
    }

    public void userProfileUpdateSuccess() {
        hideProgressDialog();
        Toast.makeText(this, "Your profile Update successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Home2Activity.class));
        finish();
        overridePendingTransition(0, 0);
    }


}