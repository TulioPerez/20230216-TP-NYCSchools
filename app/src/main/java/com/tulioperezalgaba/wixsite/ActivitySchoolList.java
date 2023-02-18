package com.tulioperezalgaba.wixsite;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySchoolList extends AppCompatActivity implements AdapterSchoolList.OnSchoolSelectedListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AdapterSchoolList mAdapter;
    private RepoSchool mRepoSchool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_school_list);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        mAdapter = new AdapterSchoolList(new ArrayList<>(), this);

        mRecyclerView = findViewById(R.id.recyclerViewSchoolList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSchools();
            }
        });

        mRepoSchool = new RepoSchool();

        loadSchools();
    }

    private void loadSchools() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRepoSchool.getSchools(new Callback<List<ModelSchool>>() {

            @Override
            public void onResponse(Call<List<ModelSchool>> call, Response<List<ModelSchool>> response) {
                mSwipeRefreshLayout.setRefreshing(false);

                Log.i("###ActivitySchool", "API response \n\t" +
                        "code: " + response.code() + "\n\t" +
                        "body: " + response.body());

                if (response.isSuccessful()) {
                    List<ModelSchool> schools = response.body();
                    mAdapter.setSchools(schools);

                    Log.i("###RepoSchool", "Fetch successful.\n\t" +
                            "List size: " + response.body().size() + "\n\t" +
                            "Dbn: " + response.body().get(0).getDbn());

                } else {
                    Toast.makeText(ActivitySchoolList.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelSchool>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
                Toast.makeText(ActivitySchoolList.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSchoolClick(ModelSchool school) {
        Intent intent = new Intent(this, ActivitySatScores.class);
        intent.putExtra(ActivitySatScores.EXTRA_SCHOOL_DBN, school.getDbn());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}