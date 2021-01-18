package com.tenakatauniversity.apply;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApplyViewModel extends ViewModel {

    private MutableLiveData<Boolean> validateFieldsLiveData;

    public LiveData<Boolean> getValidateFieldsLiveData() {
        if (validateFieldsLiveData == null)
            validateFieldsLiveData = new MutableLiveData<>();
        return validateFieldsLiveData;
    }

    private MutableLiveData<Boolean> takePictureLiveData = new MutableLiveData<>();

    public void validate() {
        validateFieldsLiveData.setValue(true);
    }

    public void resetValidateLiveData() {
        validateFieldsLiveData.setValue(null);
    }

    public LiveData<Boolean> getTakePictureLiveData() {
        if (takePictureLiveData == null)
            takePictureLiveData = new MutableLiveData<>();
        return takePictureLiveData;
    }

    public MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();

    public void takePicture() {
        takePictureLiveData.setValue(true);
    }

    public void resetTakePictureLiveData() {
        takePictureLiveData.setValue(null);
    }

}