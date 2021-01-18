package com.tenakatauniversity.utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Utility {

    public static void updateFragmentTitle(Fragment fragment, int titleResource) {
        if (((AppCompatActivity) fragment.requireActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) fragment.requireActivity()).getSupportActionBar().setTitle(titleResource);
    }

}
