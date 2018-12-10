package com.musicapp.utils;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog pd;
//    protected MutableLiveData<Status> apiStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        /*apiStatus = new MutableLiveData<>();
        apiStatus.observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
//                    setProgressDialogMessage("Please wait...");
//                    showProgressDialog();
                } else {
//                    hideProgressDialog();
                }
            }
        });*/
    }

    public void setProgressDialogMessage(String message){
        pd.setMessage(message);
    }

    public void showProgressDialog(){
        pd.show();
    }

    public void hideProgressDialog(){
        pd.dismiss();
    }

    public AlertDialog createAlertDialog(String title,
                                         String message,
                                         String posButtonTitle,
                                         String negButtonTitle,
                                         DialogInterface.OnClickListener positiveButtonClick,
                                         DialogInterface.OnClickListener negativeButtonClick) {

        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posButtonTitle, positiveButtonClick)
                .setNegativeButton(negButtonTitle, negativeButtonClick)
                .create();
    }

}
