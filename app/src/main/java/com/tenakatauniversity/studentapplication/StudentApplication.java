package com.tenakatauniversity.studentapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class StudentApplication {
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("age")
    public int age;
    @SerializedName("gender")
    public String gender;
    @SerializedName("marital_status")
    public String maritalStatus;
    @SerializedName("height")
    public double height;
    @SerializedName("iq_test_results")
    public int iqTestResult;
    @SerializedName("country")
    public String country;
    @SerializedName("admissibilityScore")
    public double admissibilityScore;
    @SerializedName("picture_url")
    public String pictureUrl;

    /**
     * @param name
     * @param age
     * @param gender
     * @param maritalStatus
     * @param height
     * @param iqTestResult
     * @param country
     */
    public StudentApplication(String name, int age, String gender, String maritalStatus, double height, int iqTestResult, String country) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.height = height;
        this.iqTestResult = iqTestResult;
        this.country = country;
        this.admissibilityScore = this.getAdmissibilityScore();
    }

    public double getAdmissibilityScore() {
        int score = 1;
        if (gender.toLowerCase().equals("female"))
            score += 56.5;
        if (maritalStatus.toLowerCase().equals("married"))
            score += 1;
        if (age > 43)
            score *= 2;
        score += iqTestResult - 100;
        return score;
    }

}
