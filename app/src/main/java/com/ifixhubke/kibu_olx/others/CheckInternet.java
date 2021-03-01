package com.ifixhubke.kibu_olx.others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import timber.log.Timber;

public class CheckInternet {
        public static boolean isConnected(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) || (mobileNetworkInfo != null && mobileNetworkInfo.isConnected())){
                Timber.d("IsConnected: success");
                return true;
            }
            else{
                return false;
            }
        }


    /*private void showCustomDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("It seems you are not connect to the Internet, please turn on WIFI or Mobile Data");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> finish());
        alertDialogBuilder.show();
    }*/


}
