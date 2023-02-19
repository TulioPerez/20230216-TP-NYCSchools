package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* detail activity's view model class that is responsible for fetching and exposing the SAT score's live data in the UI */

public class ViewModelSATScores extends ViewModel {
    private final String TAG = "ViewModelSATScores";

    private RepoSchools schoolRepository;
    private RepoSATScores satScoreRepository;
    private MutableLiveData<ModelSchools> schoolLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    // initialize repository & loads scores
    public ViewModelSATScores() {
        this.schoolRepository = new RepoSchools();
        this.satScoreRepository = new RepoSATScores();
    }

    public ViewModelSATScores(String dbn) {
        this.schoolRepository = new RepoSchools();
        this.satScoreRepository = new RepoSATScores();
        loadSchoolDetails(dbn);
    }

    // live data getters for scores & errors
    public LiveData<ModelSchools> getSchoolLiveData() {
        return schoolLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // load the school data
    void loadSchoolDetails(String dbn) {
        schoolRepository.getSchoolByDbn(dbn, new Callback<List<ModelSchools>>() {
            @Override
            public void onResponse(Call<List<ModelSchools>> call, Response<List<ModelSchools>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    ModelSchools school = response.body().get(0);
                    loadSATScoresForSchool(dbn, school);
                } else {
                    errorLiveData.postValue("Error fetching school data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchools>> call, Throwable t) {
                errorLiveData.postValue("Error fetching school data: " + t.getMessage());
            }
        });
    }

    // load the scores data
    private void loadSATScoresForSchool(String dbn, ModelSchools school) {
        satScoreRepository.getScoresBySchool(dbn, new Callback<List<ModelSATScores>>() {
            @Override
            public void onResponse(Call<List<ModelSATScores>> call, Response<List<ModelSATScores>> response) {
                if (response.isSuccessful()) {
                    List<ModelSATScores> scores = response.body();
                    school.setSatScores(scores);
                    schoolLiveData.postValue(school);
                } else {
                    errorLiveData.postValue("Error fetching SAT scores data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSATScores>> call, Throwable t) {
                errorLiveData.postValue("Error fetching SAT scores data: " + t.getMessage());
            }
        });
    }

}