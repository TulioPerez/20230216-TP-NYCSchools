package com.tulioperezalgaba.wixsite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelSchoolList extends ViewModel {
    private RepoSchool schoolRepository;
    private MutableLiveData<List<ModelSchool>> schoolsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ViewModelSchoolList() {
        this.schoolRepository = new RepoSchool();
        loadSchools();
    }

    public LiveData<List<ModelSchool>> getSchoolsLiveData() {
        return schoolsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    void loadSchools() {
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

}