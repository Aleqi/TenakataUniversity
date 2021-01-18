package com.tenakatauniversity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    final MutableLiveData<Boolean> _navigateToApplyFragmentLiveData = new MutableLiveData<>();

    final MutableLiveData<Boolean> _navigateToAdmissionResultsFragmentLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getNavigateToAdmissionResultsFragmentLiveData() {
        return _navigateToAdmissionResultsFragmentLiveData;
    }

    public LiveData<Boolean> getNavigateToApplyFragmentLiveData() {
        return _navigateToApplyFragmentLiveData;
    }

    public void navigateToApplyFragment() {
        _navigateToApplyFragmentLiveData.setValue(true);
    }

    public void navigateToAdmissionResultsFragment() {
        _navigateToAdmissionResultsFragmentLiveData.setValue(true);
    }

    public void resetNavigateToApplyFragmentLiveData() {
        _navigateToApplyFragmentLiveData.setValue(null);
    }

    public void resetNavigateToAdmissionResultsFragmentLiveData() {
        _navigateToAdmissionResultsFragmentLiveData.setValue(null);
    }

}