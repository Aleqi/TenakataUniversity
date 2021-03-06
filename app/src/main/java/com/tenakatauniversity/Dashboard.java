package com.tenakatauniversity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tenakatauniversity.databinding.DashboardFragmentBinding;
import com.tenakatauniversity.utility.Utility;

public class Dashboard extends Fragment {

    private DashboardViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding binding = DashboardFragmentBinding.inflate(inflater, container, false);
        Utility.updateFragmentTitle(this, R.string.app_name);
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setViewModel(mViewModel);
        //navigate to the apply fragment when the apply button is touched
        mViewModel.getNavigateToApplyFragmentLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_to_apply);
                mViewModel.resetNavigateToApplyFragmentLiveData();
            }
        });
       //navigate to the admissions fragment when the admission results button is touched
        mViewModel.getNavigateToAdmissionResultsFragmentLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_to_admissionList);
                mViewModel.resetNavigateToAdmissionResultsFragmentLiveData();
            }
        });
        return binding.getRoot();
    }

}