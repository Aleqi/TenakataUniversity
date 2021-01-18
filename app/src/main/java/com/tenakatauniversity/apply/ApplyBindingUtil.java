package com.tenakatauniversity.apply;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class ApplyBindingUtil {

    @BindingAdapter("showView")
    public static void showView(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

}
