package com.tenakatauniversity.admissionlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tenakatauniversity.studentapplication.StudentApplication;
import com.tenakatauniversity.studentapplication.StudentApplicationRepository;

import java.util.List;

public class AdmissionListViewModel extends AndroidViewModel {

    private StudentApplicationRepository repository;
    LiveData<List<StudentApplication>> studentApplicationsLiveData;

    public AdmissionListViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentApplicationRepository(application);
        studentApplicationsLiveData = repository.getStudentApplicationsLiveData();
        repository.getRemoteStudentApplications();
    }

}