package com.ifixhubke.kibu_olx.others;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    //To save the state of onboarding if it is done
    public static void onBoardingDone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Finished", true);
        editor.apply();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
