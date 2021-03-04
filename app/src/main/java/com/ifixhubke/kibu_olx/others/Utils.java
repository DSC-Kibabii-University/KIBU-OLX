package com.ifixhubke.kibu_olx.others;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    //To save the state of onboarding if it is done
    public static void onBoardingDone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Finished", true);
        editor.apply();
    }
}
