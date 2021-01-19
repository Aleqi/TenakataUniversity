package com.tenakatauniversity.retrofit;

import com.tenakatauniversity.studentapplication.StudentApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("insert-student-application")
    @FormUrlEncoded
    Call<List<StudentApplication>> insertStudentApplication(
            @Field("name") String name,
            @Field("age") int age,
            @Field("gender") String gender,
            @Field("marital_status") String maritalStatus,
            @Field("height") double height,
            @Field("iq_test_results") int iqTestResults,
            @Field("country") String country,
            @Field("picture_url") String pictureUrl,
            @Field("admissibility_score") double admissibilityScore
    );

    @GET("get-all-student-applications")
    Call<List<StudentApplication>> getAllStudentApplications();

}
