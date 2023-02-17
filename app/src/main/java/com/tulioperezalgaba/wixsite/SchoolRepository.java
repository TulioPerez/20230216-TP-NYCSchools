package com.tulioperezalgaba.wixsite;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SchoolRepository {
    private static final String BASE_URL = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private final Gson gson = new Gson();

    public List<School> getSchools() throws IOException {
        String json = fetchJsonData(BASE_URL);
        School[] schools = gson.fromJson(json, School[].class);
        return Arrays.asList(schools);
    }

    public School getSchoolByDbn(String dbn) throws IOException {
        String url = BASE_URL + "?dbn=" + dbn;
        String json = fetchJsonData(url);
        School[] schools = gson.fromJson(json, School[].class);
        if (schools.length > 0) {
            return schools[0];
        } else {
            throw new IOException("School not found");
        }
    }

    private String fetchJsonData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException("Error fetching data: " + responseCode);
        }
    }
}

