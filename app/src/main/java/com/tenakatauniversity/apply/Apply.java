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

import com.google.android.material.snackbar.Snackbar;
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
        Utility.updateFragmentTitle(this, R.string.enter_application_details);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        viewModel.getValidateFieldsLiveData().observe(getViewLifecycleOwner(), validate->{
            if (validate != null && validate) {
                this.validateFields();
                viewModel.resetValidateLiveData();
            }
        });

        return binding.getRoot();
    }

    /**
     * Validate the application input fields
     * */
    public boolean validateFields() {

        //validate name
        String name = binding.nameTextInputLayout.getEditText().getText().toString().trim();
        if (name == null || name.isEmpty()) {
            binding.nameTextInputLayout.setError(getString(R.string.name_error));
            return false;
        }

        //validate age
        String ageString = binding.ageTextInputLayout.getEditText().getText().toString().trim();
        if (ageString == null || ageString.isEmpty()) {
            binding.ageTextInputLayout.setError(getString(R.string.age_error));
            return false;
        }
        try {
            int age = Integer.parseInt(ageString);
            if (age <= 0) {
                binding.ageTextInputLayout.setError(getString(R.string.age_invalid_error));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.ageTextInputLayout.setError(getString(R.string.age_invalid_error));
            return false;}

        // validate gender
        if (!binding.maleRadioButton.isChecked() && !binding.femaleRadioButton.isChecked()) {
            Snackbar.make(binding.coordinatorLayout, R.string.gender_error, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        // validate marital status
        if (!binding.singleRadioButton.isChecked() && !binding.marriedRadioButton.isChecked()) {
            Snackbar.make(binding.coordinatorLayout, R.string.marital_status_error, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        //validate height
        String heightString = binding.heightTextInputLayout.getEditText().getText().toString().trim();
        if (heightString == null || heightString.isEmpty()) {
            binding.heightTextInputLayout.setError(getString(R.string.height_error));
            return false;
        }
        try {
            int height = Integer.parseInt(heightString);
            if (height <= 0) {
                binding.heightTextInputLayout.setError(getString(R.string.height_invalid_error));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.heightTextInputLayout.setError(getString(R.string.height_invalid_error));
            return false;
        }

        //validate IQ test results
        String iqString = binding.iqTestResultsTextInputLayout.getEditText().getText().toString().trim();
        if (iqString == null || iqString.isEmpty()) {
            binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_error));
            return false;
        }
        try {
            int iqTestResults = Integer.parseInt(iqString);
            if (iqTestResults <= 0) {
                binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_invalid_error));
                return false;
            } } catch (NumberFormatException e) {
            e.printStackTrace();
            binding.iqTestResultsTextInputLayout.setError(getString(R.string.iq_test_results_invalid_error));
            return false;
        }

        return true;
    }

}