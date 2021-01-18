package com.tenakatauniversity.apply;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApplyViewModel extends ViewModel {

    private MutableLiveData<Boolean> validateFieldsLiveData;

    public MutableLiveData<Boolean> getValidateFieldsLiveData() {
        if (validateFieldsLiveData == null)
            validateFieldsLiveData = new MutableLiveData<>();
        return validateFieldsLiveData;
    }

    public void validate() {
        validateFieldsLiveData.setValue(true);
    }

    public void resetValidateLiveData() {
        validateFieldsLiveData.setValue(null);
    }


}