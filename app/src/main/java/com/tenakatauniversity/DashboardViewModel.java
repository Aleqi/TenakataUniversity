package com.tenakatauniversity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {
    private MutableLiveData<Boolean> _navigateToApplyFragmentLiveData = new MutableLiveData<>();

    LiveData<Boolean> navigateToApplyFragmentLiveData;


    public LiveData<Boolean> getNavigateToApplyFragmentLiveData() {
        return _navigateToApplyFragmentLiveData;
    }



}