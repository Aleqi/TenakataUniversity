package com.tenakatauniversity.studentapplication;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tenakatauniversity.database.AppDatabase;

import java.util.List;

public class StudentApplicationRepository {
    private StudentApplicationDao studentApplicationDao;
    private LiveData<List<StudentApplication>> studentApplicationsLiveData;

    public StudentApplicationRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        studentApplicationDao = appDatabase.studentApplicationDao;
        studentApplicationsLiveData = studentApplicationDao.getAllLiveData();
    }

    public void insert(StudentApplication studentApplication) {
        AppDatabase.databaseWriteExecutor.execute(()-> studentApplicationDao.insert(studentApplication));
    }

    public LiveData<List<StudentApplication>> getStudentApplicationsLiveData() {
        return studentApplicationsLiveData;
    }
}