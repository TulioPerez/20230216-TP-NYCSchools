package com.tulioperezalgaba.wixsite;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class School implements Parcelable {
    private String dbn; // school code
    private String schoolName;
//    private String overviewParagraph;
//    private String location;
//    private String phoneNumber;
//    private double latitude;
//    private double longitude;
    private List<SATScore> satScores;

    public School(String dbn, String schoolName, String overviewParagraph, String location, String phoneNumber, double latitude, double longitude) {
        this.dbn = dbn;
        this.schoolName = schoolName;
//        this.overviewParagraph = overviewParagraph;
//        this.location = location;
//        this.phoneNumber = phoneNumber;
//        this.latitude = latitude;
//        this.longitude = longitude;
    }

    protected School(Parcel in) {
        dbn = in.readString();
        schoolName = in.readString();
//        overviewParagraph = in.readString();
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

//    public String getOverviewParagraph() {
//        return overviewParagraph;
//    }
//
//    public void setOverviewParagraph(String overviewParagraph) {
//        this.overviewParagraph = overviewParagraph;
//    }

//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }

    public List<SATScore> getSatScores() {
        return satScores;
    }

    public void setSatScores(List<SATScore> satScores) {
        this.satScores = satScores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dbn);
        parcel.writeString(schoolName);
//        parcel.writeString(overviewParagraph);
    }
}
