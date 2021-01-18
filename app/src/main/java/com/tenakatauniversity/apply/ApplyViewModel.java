package com.tenakatauniversity.apply;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tenakatauniversity.studentapplication.StudentApplication;
import com.tenakatauniversity.studentapplication.StudentApplicationRepository;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ApplyViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> validateFieldsLiveData;
    private MutableLiveData<Boolean> submitDataLiveData;
    private StudentApplicationRepository studentApplicationRepository;

    public ApplyViewModel(Application application) {
        super(application);
        studentApplicationRepository = new StudentApplicationRepository(application);
    }

    public LiveData<Boolean> getSubmitDataLiveData() {
        if (submitDataLiveData == null) {
            submitDataLiveData = new MutableLiveData<>();
        }
        return submitDataLiveData;
    }

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

    public void insertStudentApplicationIntoViewModel(StudentApplication studentApplication) {

    }

    public void submitData() {
        submitDataLiveData.setValue(true);
    }

    public String getCountry(Context context) {
        if (locationMutableLiveData.getValue() == null)
            return null;
        else {
            try {
                Location location = locationMutableLiveData.getValue();
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                return addressList.get(0).getCountryName();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    public void insertStudentApplication(StudentApplication studentApplication) {
        studentApplicationRepository.insert(studentApplication);
    }

}

