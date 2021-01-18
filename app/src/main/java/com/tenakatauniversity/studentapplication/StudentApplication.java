package com.tenakatauniversity.studentapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StudentApplication {
    @PrimaryKey
    public int id;
    public String name;
    public int age;
    public String gender;
    public String maritalStatus;
    public double height;
    public int iqTestResult;
    public String country;
    public double admissibilityScore;

    /**
     * @param name
     * @param age
     * @param gender
     * @param maritalStatus
     * @param height
     * @param iqTestResult
     * @param country
     */
    public StudentApplication (String name, int age, String gender, String maritalStatus, double height, int iqTestResult, String country) {
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
