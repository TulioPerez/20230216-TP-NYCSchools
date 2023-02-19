package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelSATScores extends ViewModel {
    private RepoSchool schoolRepository;
    private RepoSATScore satScoreRepository;
    private MutableLiveData<ModelSchool> schoolLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ViewModelSATScores() {
        this.schoolRepository = new RepoSchool();
        this.satScoreRepository = new RepoSATScore();
    }

    public ViewModelSATScores(String dbn) {
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
        schoolRepository.getSchoolByDbn(dbn, new Callback<List<ModelSchool>>() {
            @Override
            public void onResponse(Call<List<ModelSchool>> call, Response<List<ModelSchool>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    ModelSchool school = response.body().get(0);
                    loadSATScoresForSchool(dbn, school);
                } else {
                    errorLiveData.postValue("Error fetching school data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchool>> call, Throwable t) {
                errorLiveData.postValue("Error fetching school data: " + t.getMessage());
            }
        });
    }

    private void loadSATScoresForSchool(String dbn, ModelSchool school) {
        satScoreRepository.getScoresBySchool(dbn, new Callback<List<ModelSATScore>>() {
            @Override
            public void onResponse(Call<List<ModelSATScore>> call, Response<List<ModelSATScore>> response) {
                if (response.isSuccessful()) {
                    List<ModelSATScore> scores = response.body();
                    school.setSatScores(scores);
                    schoolLiveData.postValue(school);
                } else {
                    errorLiveData.postValue("Error fetching SAT scores data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSATScore>> call, Throwable t) {
                errorLiveData.postValue("Error fetching SAT scores data: " + t.getMessage());
            }
        });
    }

}