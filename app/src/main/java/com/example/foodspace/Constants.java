package com.example.foodspace;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

public final class Constants {
    public final String user = "users";
    public final String FoodSpace_Preference = "FoodSpace_Prefs";
    public final String LOGGED_IN_USERNAME = "logged_in_username";
    public final String LOGGED_IN_EMAIL = "logged_in_email";
    public final String LOGGED_IN_PHONE = "logged_in_phone_number";
    public final String EXTRA_USER_DETAILS = "extra_user_details";
    public final String USER_PROFILE_IMAGE = "User_Profile_Image";
    public final String PROFILE_IMAGE_UPLOAD = "ProfileImages";
    public final String CATGORIES="DeliveryCategories";

    public final String FIRST_NAME = "firstname";
    public final String EMAIL = "email";
    public final String MOBILE = "mobile";
    public final String IMAGE = "image";
    public final String DESCRIPTION = "Description";
    public final String COMPLETE_PROFILE = "profileCompleted";

    public final Integer READ_STORAGE_PERMISSION_CODE = 1001;
    public final Integer WRITE_STORAGE_PERMISSION_CODE = 1002;
    public final Integer TAKE_IMAGE_CODE = 10001;
    public final Integer PICK_IMAGE_REQUEST_CODE = 1000;

    public void pickImageFromGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(
                intent, PICK_IMAGE_REQUEST_CODE
        );
    }

    public void CameraCapture(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        activity.startActivityForResult(intent, TAKE_IMAGE_CODE);


    }


    public String getFileExtension(Activity activity, Uri mSelectedImageFileUri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.getContentResolver().getType(mSelectedImageFileUri));
    }
}
