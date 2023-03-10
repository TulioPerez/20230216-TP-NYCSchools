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


/* repository class that fetches NYC school names and database numbers (Dbn) using the Socrata Open Data API */

public class RepoSchools {
    private final String TAG = "RepoSchools";

    // base url & endpoint for the NYC Open Data API
    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private static final String JSON_SOURCE = "resource/s3k6-pzi2.json";

    // Retrofit service for retrieving school data
    private final SchoolService schoolService;

    // initialize Retrofit & instantiate SchoolService
    public RepoSchools() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        schoolService = retrofitBuilder.create(SchoolService.class);
    }

    // fetches list of NYC schools
    public void getSchools(Callback<List<ModelSchools>> callback) {
        Call<List<ModelSchools>> call = schoolService.getSchools();
        call.enqueue(new Callback<List<ModelSchools>>() {
            @Override
            public void onResponse(Call<List<ModelSchools>> call, Response<List<ModelSchools>> response) {
                // did we get a valid response?
                Log.i(TAG, "API response code (getSchools): " + response.code() + " response message: " + response.message());

                if (response.isSuccessful()) {
                    // got a live one!
                    callback.onResponse(call, response);
                    Log.i(TAG, "Fetched school list size: " + response.body().size());

                } else {
                    // no - log the error
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                    Log.e(TAG, "Failed to retrieve data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchools>> call, Throwable t) {
                // log the failure
                callback.onFailure(call, t);
                Log.e(TAG, "Failed to retrieve data", t);
            }
        });
    }

    public void getSchoolByDbn(String dbn, Callback<List<ModelSchools>> callback) {
        Call<List<ModelSchools>> call = schoolService.getSchoolsByDbn(dbn);
        call.enqueue(new Callback<List<ModelSchools>>() {
            @Override
            public void onResponse(Call<List<ModelSchools>> call, Response<List<ModelSchools>> response) {
                // did we get a valid response?
                Log.i(TAG, "API response code (getSchoolByDbn): " + response.code() + " response message: " + response.message());

                if (response.isSuccessful()) {
                    // got a live one!
                    List<ModelSchools> schools = response.body();
                    callback.onResponse(call, response);
                    Log.i(TAG, "Fetched school list size: " + response.body().size());

                } else {
                    // no - log the error
                    callback.onFailure(call, new Throwable("Failed to retrieve information: " + response.code()));
                    Log.e(TAG, "Failed to retrieve data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchools>> call, Throwable t) {
                // log the failure
                callback.onFailure(call, t);
                Log.e(TAG, "Failed to retrieve data", t);
            }
        });
    }

    private interface SchoolService {
        @GET(JSON_SOURCE)
        Call<List<ModelSchools>> getSchools();

        @GET(JSON_SOURCE)
        Call<List<ModelSchools>> getSchoolsByDbn(@Query("dbn") String dbn);
    }
}
