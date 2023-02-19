package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* school list's view model class that is responsible for fetching and exposing the school's live data in the UI */

public class ViewModelSchoolList extends ViewModel {
    private RepoSchools schoolRepository;

    private MutableLiveData<List<ModelSchools>> schoolsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    // initialize repository & load schools
    public ViewModelSchoolList() {
        this.schoolRepository = new RepoSchools();
        loadSchools();
    }

    // live data getters for schools & errors
    public LiveData<List<ModelSchools>> getSchoolsLiveData() {
        return schoolsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // load the school data
    void loadSchools() {
        schoolRepository.getSchools(new Callback<List<ModelSchools>>() {
            @Override
            public void onResponse(Call<List<ModelSchools>> call, Response<List<ModelSchools>> response) {
                if (response.isSuccessful()) {
                    schoolsLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error fetching schools data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchools>> call, Throwable t) {
                errorLiveData.postValue("Error fetching schools data: " + t.getMessage());
            }
        });
    }

}