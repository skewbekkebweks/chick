package com.example.chick.helpers;

import android.util.Pair;

import com.example.chick.R;

import java.util.Objects;

public class Validator {

    public static Pair<Boolean, Integer> isSignUpDataCorrect(String email, String password1, String password2) {
        if (!Objects.equals(password1, password2)) return new Pair<>(false, R.string.password_same_error);
//        корректный ли email
        Pair<Boolean, Integer> emailCheck = isEmailCorrect(email);
//        корректный ли пароль
        Pair<Boolean, Integer> passwordCheck = isPasswordCorrect(password1);
//        ошибка
        if (!emailCheck.first) return emailCheck;
        if (!passwordCheck.first) return passwordCheck;
//        всё правильно
        return new Pair<>(true, 0);
    }

    public static Pair<Boolean, Integer> isEmailCorrect(String email) {
        if (email.contains("@")) {
            email = email.split("@")[1];
            if (email.contains(".")) {
                return new Pair<>(true, 0);
            } else {
                return new Pair<>(false, R.string.dot_required);
            }
        } else {
            return new Pair<>(false, R.string.dog_required);
        }
    }

    public static Pair<Boolean, Integer> isPasswordCorrect(String password) {
        int min = 8;
        int digit = 0;
        int upCount = 0;
        int lowCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                upCount++;
            }
            if (Character.isLowerCase(c)) {
                lowCount++;
            }
            if (Character.isDigit(c)) {
                digit++;
            }
        }
        if (password.length() < min) return new Pair<>(false, R.string.leatest_8);
        if (digit == 0) return new Pair<>(false, R.string.no_numbers);
        if (upCount == 0) return new Pair<>(false, R.string.no_upper);
        if (lowCount == 0) return new Pair<>(false, R.string.no_lower);
        return new Pair<>(true, 0);
    }
}
