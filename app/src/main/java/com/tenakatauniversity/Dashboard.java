package com.tenakatauniversity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tenakatauniversity.databinding.DashboardFragmentBinding;

import timber.log.Timber;

public class Dashboard extends Fragment {

    private DashboardViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding binding = DashboardFragmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setViewModel(mViewModel);
//        binding.btApply.setOnClickListener (view -> mViewModel.navigateToApplyFragment());
        //navigate to the apply fragment when the apply button is touched
        mViewModel.getNavigateToApplyFragmentLiveData().observe(getViewLifecycleOwner(), result -> {
            Timber.d(getClass().getSimpleName(), "navigate to apply fragment");
            if (result != null && result) {
                Toast.makeText(requireContext(), "Navigate to apply fragment", Toast.LENGTH_SHORT).show();
                mViewModel.resetNavigateToApplyFragmentLiveData();
            }
        });
       //navigate to the admissions fragment when the admission results button is touched
        mViewModel.getNavigateToAdmissionResultsFragmentLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result) {
                Timber.d(getClass().getSimpleName(), "navigate to admission results fragment");

                Toast.makeText(requireContext(), "Navigate to admission results fragment", Toast.LENGTH_SHORT).show();
                mViewModel.resetNavigateToAdmissionResultsFragmentLiveData();
            }
        });
        return binding.getRoot();
    }

}