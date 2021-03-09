package com.ifixhubke.kibu_olx.others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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

    public static void updateUserStatus(String status) {
        FirebaseAuth mAuth;
        DatabaseReference rootReF;

        String currentUserId;
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        mAuth = FirebaseAuth.getInstance();
        rootReF = FirebaseDatabase.getInstance().getReference();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", status);

        currentUserId = mAuth.getCurrentUser().getUid();
        rootReF.child("users").child(currentUserId).child("userState").updateChildren(onlineStateMap);
    }
}
