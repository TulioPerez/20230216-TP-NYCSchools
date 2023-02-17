package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

public class SchoolDetailViewModel extends ViewModel {
    private SchoolRepository schoolRepository;
    private SATScoreRepository satScoreRepository;
    private MutableLiveData<School> schoolLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public SchoolDetailViewModel(String dbn) {
        this.schoolRepository = new SchoolRepository();
        this.satScoreRepository = new SATScoreRepository();
        loadSchoolDetails(dbn);
    }

    public LiveData<School> getSchoolLiveData() {
        return schoolLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    void loadSchoolDetails(String dbn) {
        try {
            School school = schoolRepository.getSchoolByDbn(dbn);
            List<SATScore> scores = satScoreRepository.getScoresBySchool(dbn);
            school.setSatScores(scores);
            schoolLiveData.postValue(school);
        } catch (IOException e) {
            errorLiveData.postValue("Error fetching school data: " + e.getMessage());
        }
    }
}
