package com.tulioperezalgaba.wixsite;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import java.util.ArrayList;

public class ActivitySchoolList extends AppCompatActivity implements AdapterSchoolList.OnSchoolSelectedListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AdapterSchoolList mAdapter;
    private ViewModelSchoolList mViewModel;



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
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.loadSchools();
        });

        mViewModel = new ViewModelProvider(this).get(ViewModelSchoolList.class);
        mViewModel.getSchoolsLiveData().observe(this, schools -> {
            mAdapter.setSchools(schools);
            mSwipeRefreshLayout.setRefreshing(false); // stop the animation
        });

        mViewModel.getErrorLiveData().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false); // stop the animation
        });

        mViewModel.loadSchools();
    }

    @Override
    public void onSchoolClick(ModelSchool school) {
        Intent intent = new Intent(this, ActivitySatScores.class);
        intent.putExtra(ActivitySatScores.EXTRA_SCHOOL_DBN, school);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}