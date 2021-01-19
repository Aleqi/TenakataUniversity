package com.tenakatauniversity.admissionlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenakatauniversity.R;
import com.tenakatauniversity.databinding.AdmissionListFragmentBinding;
import com.tenakatauniversity.utility.Utility;

public class AdmissionList extends Fragment {

    private AdmissionListViewModel viewModel;
    private AdmissionListFragmentBinding binding;
    private AdmissionListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = AdmissionListFragmentBinding.inflate(inflater, container, false);
        Utility.updateFragmentTitle(this, R.string.admission_list);
        viewModel = new ViewModelProvider(this, new AdmissionListViewModelFactory(requireActivity().getApplication())).get(AdmissionListViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setupRecyclerViewAdapter();
        viewModel.studentApplicationsLiveData.observe(getViewLifecycleOwner(), studentApplications -> {
            if (studentApplications != null) {
                adapter.submitList(studentApplications);
            }
        });
        return binding.getRoot();
    }

    public void setupRecyclerViewAdapter() {
        adapter = new AdmissionListAdapter(null);
        binding.recyclerView.setAdapter(adapter);
    }

}