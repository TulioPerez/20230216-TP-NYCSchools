package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelSchoolList extends ViewModel {
    private RepoSchool schoolRepository;
    private RepoSATScore satScoreRepository;
    private MutableLiveData<List<ModelSchool>> schoolsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<ModelSATScore> satScoreLiveData = new MutableLiveData<>();


    public ViewModelSchoolList() {
        this.schoolRepository = new RepoSchool();
        this.satScoreRepository = new RepoSATScore();
        loadSchools();
    }

    public LiveData<List<ModelSchool>> getSchoolsLiveData() {
        return schoolsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<ModelSATScore> getSATScoreLiveData() {
        return satScoreLiveData;
    }

    private void loadSchools() {
        schoolRepository.getSchools(new Callback<List<ModelSchool>>() {
            @Override
            public void onResponse(Call<List<ModelSchool>> call, Response<List<ModelSchool>> response) {
                if (response.isSuccessful()) {
                    schoolsLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error fetching schools data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchool>> call, Throwable t) {
                errorLiveData.postValue("Error fetching schools data: " + t.getMessage());
            }
        });
    }





    public void loadSATScoresForSchool(String dbn) {
        try {
            List<ModelSATScore> scores = satScoreRepository.getScoresBySchool(dbn);
            // update each school with its SAT scores
            List<ModelSchool> schools = schoolsLiveData.getValue();
            for (ModelSchool school : schools) {
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

