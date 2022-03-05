package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;

public class NameOfUser extends AppCompatActivity {
TextInputEditText editText1;
TextInputEditText editText2;
CircleImageView img1;
int TAKE_IMAGE_CODE=10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_of_user);
    editText1=findViewById(R.id.nameUser);
    editText2=findViewById(R.id.tellus);
img1=findViewById(R.id.circleImageView3);
    }

    public void backward(View view) {
        Intent intent=new Intent(NameOfUser.this,PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    public void Continue(View view) {
Intent intent=new Intent(NameOfUser.this,Home2Activity.class);
finish();
startActivity(intent);
        overridePendingTransition(0,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImage(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this).setMessage("Select the image Options").setPositiveButton("Capture", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                selectCapture();
            }
        }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                selectImage();
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    private void selectCapture() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
if(intent.resolveActivity(getPackageManager())!=null)
{
    startActivityForResult(intent,TAKE_IMAGE_CODE);

}
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void selectImage() {
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                &&
                (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED))
        {
            String[] permission=new String[]{(Manifest.permission.READ_EXTERNAL_STORAGE)};
            String[] permissionWrite=new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)};
            requestPermissions(permission,1001);
            requestPermissions(permissionWrite,1002);
        }
        else{
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(
                intent,1000
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            if (data != null) {
                img1.setImageURI(data.getData());
                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                handleUpload(bitmap);

            }
        } else if (resultCode== Activity.RESULT_OK && requestCode == TAKE_IMAGE_CODE){

                   if(data!=null)
                {
                      Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                         img1.setImageBitmap(bitmap);
                         handleUpload(bitmap);
                }

        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference= FirebaseStorage.getInstance().getReference().child("ProfileImages").child(uid+".jpeg");

                reference.putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
getDownloadUrl(reference);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(@NonNull Uri uri) {
setUserprofileUrl(uri);
        }
    });
    }

    private void setUserprofileUrl(Uri uri) {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        if(user!=null)
        {
            user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {
                    Toast.makeText(NameOfUser.this,"Update SuccessFully",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NameOfUser.this,"Profile Image failed",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}