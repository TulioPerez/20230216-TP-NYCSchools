package com.tulioperezalgaba.wixsite;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RepoSchool {

    private static final String BASE_URL = "https://data.cityofnewyork.us/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final SchoolService schoolService = retrofit.create(SchoolService.class);

    public List<ModelSchool> getSchools() throws IOException {
        Call<List<ModelSchool>> call = schoolService.getSchools();
        return call.execute().body();
    }

    public ModelSchool getSchoolByDbn(String dbn) throws IOException {
        Call<List<ModelSchool>> call = schoolService.getSchoolsByDbn(dbn);
        List<ModelSchool> schools = call.execute().body();
        if (schools != null && !schools.isEmpty()) {
            return schools.get(0);
        } else {
            throw new IOException("School not found");
        }
    }

    interface SchoolService {
        @GET("resource/s3k6-pzi2.json")
        Call<List<ModelSchool>> getSchools();

        @GET("resource/s3k6-pzi2.json")
        Call<List<ModelSchool>> getSchoolsByDbn(@Query("dbn") String dbn);
    }
}
