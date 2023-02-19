package com.tulioperezalgaba.wixsite;

import android.os.Parcel;
import android.os.Parcelable;


/* model class that holds SAT score data */

public class ModelSATScores implements Parcelable {
    private int sat_critical_reading_avg_score;
    private int sat_math_avg_score;
    private int sat_writing_avg_score;

    public ModelSATScores(int sat_critical_reading_avg_score, int sat_math_avg_score, int sat_writing_avg_score) {
        this.sat_critical_reading_avg_score = sat_critical_reading_avg_score;
        this.sat_math_avg_score = sat_math_avg_score;
        this.sat_writing_avg_score = sat_writing_avg_score;
    }

    protected ModelSATScores(Parcel in) {
        sat_critical_reading_avg_score = in.readInt();
        sat_math_avg_score = in.readInt();
        sat_writing_avg_score = in.readInt();
    }

    // Creates ModelSATScores objects from parcel & ModelSATScores object array
    public static final Creator<ModelSATScores> CREATOR = new Creator<ModelSATScores>() {
        @Override
        public ModelSATScores createFromParcel(Parcel in) {
            return new ModelSATScores(in);
        }

        @Override
        public ModelSATScores[] newArray(int size) {
            return new ModelSATScores[size];
        }
    };

    // describe special objects contained in parcel (required by Parcelable)
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelSATscores, int flags) {
        parcelSATscores.writeInt(sat_critical_reading_avg_score);
        parcelSATscores.writeInt(sat_math_avg_score);
        parcelSATscores.writeInt(sat_writing_avg_score);
    }


    // getters
    public int getSat_critical_reading_avg_score() {
        return sat_critical_reading_avg_score;
    }

    public int getSat_math_avg_score() {
        return sat_math_avg_score;
    }

    public int getSat_writing_avg_score() {
        return sat_writing_avg_score;
    }

    // setters
    public void setSat_critical_reading_avg_score(int sat_critical_reading_avg_score) {
        this.sat_critical_reading_avg_score = sat_critical_reading_avg_score;
    }

    public void setSat_math_avg_score(int sat_math_avg_score) {
        this.sat_math_avg_score = sat_math_avg_score;
    }

    public void setSat_writing_avg_score(int sat_writing_avg_score) {
        this.sat_writing_avg_score = sat_writing_avg_score;
    }

}