package com.tulioperezalgaba.wixsite;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
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

    public List<ModelSATScore> getScoresBySchool(String dbn) throws IOException {
        Call<List<ModelSATScore>> call = satService.getScoresBySchool(dbn);
        Response<List<ModelSATScore>> response = call.execute();
        if (response.isSuccessful()) {

            // Log the successful retrieval of information
            Log.i("###RepoSATScore", "Retrieved information successfully. List size: " + response.body().size());
            return response.body();
        } else {
            // Log the unsuccessful retrieval of information
            Log.e("###RepoSATScore", "Failed to retrieve information: " + response.code());
            throw new IOException("Failed to retrieve information: " + response.code());
        }
    }

    private interface SATService {
        @GET(JSON_SOURCE)
        Call<List<ModelSATScore>> getScoresBySchool(@Query("dbn") String dbn);
    }
}
