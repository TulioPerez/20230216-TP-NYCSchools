package com.tulioperezalgaba.wixsite;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RepoSATScore {

    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private static final String JSON_SOURCE = "resource/f9bf-2cp4.json";

    private final SATService satService;

    public RepoSATScore() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        satService = retrofitBuilder.create(SATService.class);
    }


    public void getScoresBySchool(String dbn, Callback<List<ModelSATScore>> callback) {
        Call<List<ModelSATScore>> call = satService.getScoresBySchool(dbn);
        call.enqueue(new Callback<List<ModelSATScore>>() {
            @Override
            public void onResponse(Call<List<ModelSATScore>> call, Response<List<ModelSATScore>> response) {
                Log.i("RepoSATScore", "API " +
                        "response code: " + response.code() + "\n\t" +
                        "response body: " + response.body());

                if (response.isSuccessful()) {
                    Log.i("###RepoSATScore", "Fetched list size: " + response.body().size());
                    callback.onResponse(call, response);
                } else {
                    Log.e("###RepoSATScore", "Failed to retrieve information: " + response.code());
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ModelSATScore>> call, Throwable t) {
                Log.e("***RepoSATScore", "Failed to retrieve information", t);
                callback.onFailure(call, t);
            }
        });
    }

    private interface SATService {
        @GET(JSON_SOURCE)
        Call<List<ModelSATScore>> getScoresBySchool(@Query("dbn") String dbn);
    }
}
