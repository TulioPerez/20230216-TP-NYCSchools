package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

public class ViewModelSchoolDetail extends ViewModel {
    private RepoSchool schoolRepository;
    private RepoSATScore satScoreRepository;
    private MutableLiveData<ModelSchool> schoolLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ViewModelSchoolDetail() {
        this.schoolRepository = new RepoSchool();
        this.satScoreRepository = new RepoSATScore();
    }

    public ViewModelSchoolDetail(String dbn) {
        this.schoolRepository = new RepoSchool();
        this.satScoreRepository = new RepoSATScore();
        loadSchoolDetails(dbn);
    }

    public LiveData<ModelSchool> getSchoolLiveData() {
        return schoolLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    void loadSchoolDetails(String dbn) {
        try {
            ModelSchool school = schoolRepository.getSchoolByDbn(dbn);
            List<ModelSATScore> scores = satScoreRepository.getScoresBySchool(dbn);
            school.setSatScores(scores);
            schoolLiveData.postValue(school);
        } catch (IOException e) {
            errorLiveData.postValue("Error fetching school data: " + e.getMessage());
        }
    }
}
