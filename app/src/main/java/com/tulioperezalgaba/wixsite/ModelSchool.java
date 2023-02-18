package com.tulioperezalgaba.wixsite;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelSchool implements Parcelable {
    private String school_name;
    private String dbn;
    private List<ModelSATScore> satScores;

    public ModelSchool(String dbn, String school_name, List<ModelSATScore> satScores) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.satScores = satScores;
    }

    protected ModelSchool(Parcel in) {
        dbn = in.readString();
        school_name = in.readString();
    }

    public static final Creator<ModelSchool> CREATOR = new Creator<ModelSchool>() {
        @Override
        public ModelSchool createFromParcel(Parcel in) {
            return new ModelSchool(in);
        }

        @Override
        public ModelSchool[] newArray(int size) {
            return new ModelSchool[size];
        }
    };

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }


    public List<ModelSATScore> getSatScores() {
        return satScores;
    }

    public void setSatScores(List<ModelSATScore> satScores) {
        this.satScores = satScores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelSchool, int i) {
        parcelSchool.writeString(dbn);
        parcelSchool.writeString(school_name);
    }
}
