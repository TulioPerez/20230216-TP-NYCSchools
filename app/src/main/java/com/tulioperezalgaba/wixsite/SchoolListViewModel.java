package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

public class SchoolListViewModel extends ViewModel {
    private SchoolRepository schoolRepository;
    private SATScoreRepository satScoreRepository;
    private MutableLiveData<List<School>> schoolsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<SATScore> satScoreLiveData = new MutableLiveData<>();


    public SchoolListViewModel() {
        this.schoolRepository = new SchoolRepository();
        this.satScoreRepository = new SATScoreRepository();
        loadSchools();
    }

    public LiveData<List<School>> getSchoolsLiveData() {
        return schoolsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<SATScore> getSATScoreLiveData() {
        return satScoreLiveData;
    }

    private void loadSchools() {
        try {
            List<School> schools = schoolRepository.getSchools();
            schoolsLiveData.postValue(schools);
        } catch (IOException e) {
            errorLiveData.postValue("Error fetching schools data: " + e.getMessage());
        }
    }

    public void loadSATScoresForSchool(String dbn) {
        try {
            List<SATScore> scores = satScoreRepository.getScoresBySchool(dbn);
            // update each school with its SAT scores
            List<School> schools = schoolsLiveData.getValue();
            for (School school : schools) {
                if (school.getDbn().equals(dbn)) {
                    school.setSatScores(scores);
                    schoolsLiveData.postValue(schools);
                    return;
                }
            }
            errorLiveData.postValue("School not found with DBN: " + dbn);
        } catch (IOException e) {
            errorLiveData.postValue("Error fetching SAT scores data: " + e.getMessage());
        }
    }
}

