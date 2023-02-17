package com.tulioperezalgaba.wixsite;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RepoSATScore {

    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private final ApiService apiService;

    public RepoSATScore() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public List<ModelSATScore> getScoresBySchool(String dbn) throws IOException {
        Call<List<ModelSATScore>> call = apiService.getScoresBySchool(dbn);
        return call.execute().body();
    }

    private interface ApiService {
        @GET("resource/f9bf-2cp4.json")
        Call<List<ModelSATScore>> getScoresBySchool(@Query("dbn") String dbn);
    }
}
