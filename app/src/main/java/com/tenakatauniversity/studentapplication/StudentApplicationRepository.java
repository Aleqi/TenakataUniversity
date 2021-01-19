package com.tenakatauniversity.studentapplication;

import android.app.Application;
import android.appwidget.AppWidgetProvider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tenakatauniversity.database.AppDatabase;
import com.tenakatauniversity.retrofit.ApiService;
import com.tenakatauniversity.retrofit.RetrofitBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StudentApplicationRepository {
    private StudentApplicationDao studentApplicationDao;
    private final LiveData<List<StudentApplication>> studentApplicationsLiveData;
    private Application application;
    private ApiService apiService;

    public StudentApplicationRepository(Application application) {
        this.application = application;
        this.apiService = RetrofitBuilder.getApiInterface();
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        studentApplicationDao = appDatabase.studentApplicationDao();
        studentApplicationsLiveData = studentApplicationDao.getAllLiveData();
    }

    public void insert(StudentApplication studentApplications) {
        AppDatabase.databaseWriteExecutor.execute(() -> studentApplicationDao.insert(studentApplications));
    }

    public LiveData<List<StudentApplication>> getStudentApplicationsLiveData() {
        return studentApplicationsLiveData;
    }

    public void insertStudentApplicationToRemoteServer(StudentApplication studentApplication, MutableLiveData<Boolean> resultLiveData) {
        Call<List<StudentApplication>> insertStudentApplicationCall = apiService.insertStudentApplication(studentApplication.name, studentApplication.age, studentApplication.gender, studentApplication.maritalStatus, studentApplication.height, studentApplication.iqTestResult, studentApplication.country, studentApplication.pictureUrl, studentApplication.admissibilityScore);
        insertStudentApplicationCall.enqueue(new Callback<List<StudentApplication>>() {
            @Override
            public void onResponse(Call<List<StudentApplication>> call, Response<List<StudentApplication>> response) {
                if (response.isSuccessful()) {
                    List<StudentApplication> studentApplications = response.body();
                    if (studentApplications != null) {
                        insert(studentApplications);
                        resultLiveData.setValue(true);
                    }
                } else {
                    try {
                        Timber.d("insert failed: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StudentApplication>> call, Throwable t) {
                Timber.d("insert failed: " + t.getMessage());
            }
        });

    }

    private void insert(List<StudentApplication> studentApplications) {
        for (StudentApplication studentApplication :
                studentApplications) {
            AppDatabase.databaseWriteExecutor.execute(() -> {
                studentApplicationDao.deleteAll();
                studentApplicationDao.insert(studentApplication);
            });
        }
    }

    public void getRemoteStudentApplications() {
        Call<List<StudentApplication>> call = apiService.getAllStudentApplications();
        call.enqueue(new Callback<List<StudentApplication>>() {
            @Override
            public void onResponse(Call<List<StudentApplication>> call, Response<List<StudentApplication>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        insert(response.body());
                } else {
                    try {
                        Timber.d("fetch failed: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StudentApplication>> call, Throwable t) {
                Timber.d("insert failed: " + t.getMessage());
            }
        });
    }

}