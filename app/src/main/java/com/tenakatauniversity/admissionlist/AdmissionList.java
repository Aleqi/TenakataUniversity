package com.tenakatauniversity.admissionlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenakatauniversity.databinding.AdmissionListFragmentBinding;

public class AdmissionList extends Fragment {

    private AdmissionListViewModel viewModel;
    private AdmissionListFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = AdmissionListFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new AdmissionListViewModelFactory(requireActivity().getApplication())).get(AdmissionListViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}