package com.tenakatauniversity.studentapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * A Data Access Object for StudentApplication class
 */
@Dao
public interface StudentApplicationDao {
    @Query("SELECT * FROM StudentApplication")
    List<StudentApplication> getAll();

    @Query("SELECT * FROM StudentApplication")
    LiveData<List<StudentApplication>> getAllLiveData();

    @Insert
    void insert(StudentApplication... studentApplications);

    @Delete
    void delete(StudentApplication studentApplication);

}
