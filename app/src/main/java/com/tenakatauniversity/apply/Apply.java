package com.tenakatauniversity.apply;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenakatauniversity.R;
import com.tenakatauniversity.databinding.ApplyFragmentBinding;
import com.tenakatauniversity.utility.Utility;

public class Apply extends Fragment {

    private ApplyViewModel viewModel;
    private ApplyFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ApplyFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ApplyViewModel.class);
        Utility.updateFragmentTitle(this, R.string.apply);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

}