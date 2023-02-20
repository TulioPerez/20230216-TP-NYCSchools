package com.tulioperezalgaba.wixsite;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/* repository class that fetches SAT scores using the Socrata Open Data API */

public class RepoSATScores {
    private final String TAG = "RepoSATScores";

    // base url & endpoint for the NYC Open Data API
    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private static final String JSON_SOURCE = "resource/f9bf-2cp4.json";

    private Call<List<ModelSATScores>> call;


    // Retrofit service for retrieving SAT score data
    private final SATScoreService satScoreService;

    // initialize Retrofit & instantiate SATScoreService
    public RepoSATScores() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        satScoreService = retrofitBuilder.create(SATScoreService.class);
    }

    // fetches list of SAT scores
    public void getScoresBySchool(String dbn, Callback<List<ModelSATScores>> callback) {
        call = satScoreService.getScoresBySchool(dbn);

        Call<List<ModelSATScores>> call = satScoreService.getScoresBySchool(dbn);
        call.enqueue(new Callback<List<ModelSATScores>>() {
            @Override
            public void onResponse(Call<List<ModelSATScores>> call, Response<List<ModelSATScores>> response) {
                // did we get a valid response?
                Log.i(TAG, "API response code (getScoresBySchool): " + response.code() + " response message: " + response.message());

                if (response.isSuccessful()) {
                    // got a live one!
                    callback.onResponse(call, response);

                } else {
                    // no - log the error
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                    Log.e(TAG, "Failed to retrieve data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSATScores>> call, Throwable t) {
                // log the failure
                callback.onFailure(call, t);
                Log.e(TAG, "Failed to retrieve data", t);
            }
        });
    }

    // The Retrofit service that defines the API endpoints for retrieving SAT score data.
    private interface SATScoreService {
        @GET(JSON_SOURCE)
        Call<List<ModelSATScores>> getScoresBySchool(@Query("dbn") String dbn);
    }

}
