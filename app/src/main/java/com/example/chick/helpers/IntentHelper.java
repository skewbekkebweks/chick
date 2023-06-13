package com.example.chick.helpers;

import android.content.Context;
import android.content.Intent;

import com.example.chick.R;
import com.example.chick.activities.account.LoginActivity;

public class IntentHelper {
    public static Intent toLogin(Context context, int theme) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(context.getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        return intent;
    }
}
