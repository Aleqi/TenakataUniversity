package com.tenakatauniversity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Boolean> _navigateToApplyFragmentLiveData;

    private MutableLiveData<Boolean> _navigateToAdmissionResultsFragmentLiveData;

    public LiveData<Boolean> getNavigateToAdmissionResultsFragmentLiveData() {
        if (_navigateToAdmissionResultsFragmentLiveData == null)
            _navigateToAdmissionResultsFragmentLiveData = new MutableLiveData<>();
        return _navigateToAdmissionResultsFragmentLiveData;
    }

    public LiveData<Boolean> getNavigateToApplyFragmentLiveData() {
        if (_navigateToApplyFragmentLiveData == null)
            _navigateToApplyFragmentLiveData = new MutableLiveData<>();
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