package com.example.foodspace;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import kotlin.Suppress;

public class BaseActivity extends AppCompatActivity {

    private Dialog mProgressDialog;
TextView progressText;
    void showErrorSnackBar(String message,Boolean errorMessage) {
        Snackbar snackBar =
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackBar.getView();

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this,
            R.color.colorSnackBarError
                )
            );
        }else{
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this,
            R.color.colorSnackBarSuccess
                )
            );
        }
        snackBar.show();
    }
    void showProgressDialog(String text){
        mProgressDialog=new Dialog(this);

        mProgressDialog.setContentView(R.layout.dialog_progress);
progressText=mProgressDialog.findViewById(R.id.tv_progress_text);
progressText.setText(text);
mProgressDialog.setCancelable(false);
mProgressDialog.setCanceledOnTouchOutside(false);
mProgressDialog.show();


    }
    void hideProgressDialog(){
        mProgressDialog.dismiss();
    }

    }

