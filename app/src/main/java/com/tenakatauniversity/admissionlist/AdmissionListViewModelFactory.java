package com.tenakatauniversity.admissionlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tenakatauniversity.apply.ApplyViewModel;

public class AdmissionListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;

    public AdmissionListViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == AdmissionListViewModel.class) {
            return (T) new AdmissionListViewModel(application);
        }
        return null;
    }
}
