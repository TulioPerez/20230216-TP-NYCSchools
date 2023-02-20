package com.tulioperezalgaba.wixsite;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;


/* model class that holds School data and holds a list of SAT scores */

public class ModelSchools implements Parcelable {
    private final String TAG = "ModelSchools";

    private String school_name;
    private String dbn;
    private List<ModelSATScores> satScores;

    protected ModelSchools(Parcel in) {
        dbn = in.readString();
        school_name = in.readString();
    }

    // Creates ModelSchools objects from parcel
    public static final Creator<ModelSchools> CREATOR = new Creator<ModelSchools>() {
        @Override
        public ModelSchools createFromParcel(Parcel in) {
            return new ModelSchools(in);
        }

        @Override
        public ModelSchools[] newArray(int size) {
            return new ModelSchools[size];
        }
    };

    // describe special objects contained in parcel (required by Parcelable)
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelSchool, int i) {
        parcelSchool.writeString(dbn);
        parcelSchool.writeString(school_name);
//        Log.d(TAG, "ModelSchools object written to parcel: " + dbn + ", " + school_name);
    }

    // getters
    public String getDbn() {
        return dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public List<ModelSATScores> getSatScores() {
        return satScores;
    }

    public void setSatScores(List<ModelSATScores> satScores) {
        this.satScores = satScores;
//        Log.d(TAG, "ModelSchools satScores list set, size: " + satScores.size());
    }

}
