package com.tulioperezalgaba.wixsite;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* school list's view model class that is responsible for fetching and exposing the school's live data in the UI */

public class ViewModelSchoolList extends ViewModel {
    private final String TAG = "ViewModelSchoolList";

    private RepoSchools schoolRepository;

    private MutableLiveData<List<ModelSchools>> schoolsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    // initialize repository
    public ViewModelSchoolList() {
        this.schoolRepository = new RepoSchools();
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
//        Log.d(TAG, "loadSchools: Loading school data...");
        schoolRepository.getSchools(new Callback<List<ModelSchools>>() {
            @Override
            public void onResponse(Call<List<ModelSchools>> call, Response<List<ModelSchools>> response) {

                if (response.isSuccessful()) {
                    schoolsLiveData.postValue(response.body());
//                    Log.d(TAG, "loadSchools: school data loaded successfully.");

                } else {
                    errorLiveData.postValue("Error fetching schools data: " + response.message());
                    Log.e(TAG, "Error fetching schools data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchools>> call, Throwable t) {
                errorLiveData.postValue("Error fetching schools data: " + t.getMessage());
                Log.e(TAG, "Failed to load schools data: " + t.getMessage());

            }
        });
    }

}