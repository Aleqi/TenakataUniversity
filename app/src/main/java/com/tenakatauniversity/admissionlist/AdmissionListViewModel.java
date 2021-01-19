package com.tenakatauniversity.admissionlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tenakatauniversity.studentapplication.StudentApplication;
import com.tenakatauniversity.studentapplication.StudentApplicationRepository;

import java.util.List;

public class AdmissionListViewModel extends AndroidViewModel {

    private final StudentApplicationRepository repository;
    LiveData<List<StudentApplication>> studentApplicationsLiveData;

    public AdmissionListViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentApplicationRepository(application);
        repository.getRemoteStudentApplications();
        studentApplicationsLiveData = repository.getStudentApplicationsLiveData();
    }

    public void refresh() {
        repository.getRemoteStudentApplications();
    }
}