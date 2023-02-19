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

public class RepoSchool {

    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private static final String JSON_SOURCE = "resource/s3k6-pzi2.json";


    private final SchoolService schoolService;

    public RepoSchool() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        schoolService = retrofitBuilder.create(SchoolService.class);
    }

    public void getSchools(Callback<List<ModelSchool>> callback) {
        Call<List<ModelSchool>> call = schoolService.getSchools();
        call.enqueue(new Callback<List<ModelSchool>>() {
            @Override
            public void onResponse(Call<List<ModelSchool>> call, Response<List<ModelSchool>> response) {
                Log.i("###RepoSchool", "API " +
                        "response code: " + response.code() + "\n\t" +
                        "response body: " + response.body());

                if (response.isSuccessful()) {
                    Log.i("###RepoSchool", "Fetched list size: " + response.body().size());

                    callback.onResponse(call, response);

                    List<ModelSchool> schools = response.body();
                    if (schools != null && schools.size() > 0) {
                        // Print the first entry in the response
                    }

                    callback.onResponse(call, response);

                } else {
                    // Log the unsuccessful retrieval of information
                    Log.e("###RepoSchool", "Failed to retrieve information: " + response.code());
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchool>> call, Throwable t) {
                Log.e("###RepoSchool", "API request failed to retrieve information: " + t.getMessage(), t);

                callback.onFailure(call, t);
            }
        });
    }

    public void getSchoolByDbn(String dbn, Callback<List<ModelSchool>> callback) {
        Call<List<ModelSchool>> call = schoolService.getSchoolsByDbn(dbn);
        call.enqueue(new Callback<List<ModelSchool>>() {
            @Override
            public void onResponse(Call<List<ModelSchool>> call, Response<List<ModelSchool>> response) {
                Log.i("###RepoSchool", "API " +
                        "response code: " + response.code() + "\n\t" +
                        "response body: " + response.body());

                if (response.isSuccessful()) {
                    List<ModelSchool> schools = response.body();
                    callback.onResponse(call, response);
                    Log.i("###RepoSchool", "Fetched list size: " + response.body().size());
                } else {
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                    Log.e("###RepoSchool", "Failed to retrieve information: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchool>> call, Throwable t) {
                callback.onFailure(call, t);
                Log.e("***RepoSchool", "Failed to retrieve information", t);
            }
        });
    }

    private interface SchoolService {
        @GET(JSON_SOURCE)
        Call<List<ModelSchool>> getSchools();

        @GET(JSON_SOURCE)
        Call<List<ModelSchool>> getSchoolsByDbn(@Query("dbn") String dbn);
    }
}
