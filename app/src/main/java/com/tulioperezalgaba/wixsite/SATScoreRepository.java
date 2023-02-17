package com.tulioperezalgaba.wixsite;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SATScoreRepository {
    private static final String BASE_URL = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";
    private final Gson gson = new Gson();

    public List<SATScore> getScoresBySchool(String dbn) throws IOException {
        String url = BASE_URL + "?dbn=" + dbn;
        String json = fetchJsonData(url);
        SATScore[] scores = gson.fromJson(json, SATScore[].class);
        return Arrays.asList(scores);
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
