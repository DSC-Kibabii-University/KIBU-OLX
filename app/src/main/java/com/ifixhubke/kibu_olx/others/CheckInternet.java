package com.ifixhubke.kibu_olx.others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import timber.log.Timber;

public class CheckInternet {
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) || (mobileNetworkInfo != null && mobileNetworkInfo.isConnected())) {
            Timber.d("IsConnected: success");
            return true;
        } else {
            return false;
        }
    }
}
