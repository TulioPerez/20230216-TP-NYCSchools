package com.tulioperezalgaba.wixsite;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


/* Initial (launcher) activity used to display a list of NYC schools */

public class ActivitySchoolList extends AppCompatActivity implements AdapterSchoolList.OnSchoolSelectedListener {
    private final String TAG = "ActivitySchoolList";

    private RecyclerView mRecyclerView;
    private AdapterSchoolList mAdapter;

    private ViewModelSchoolList mViewModel;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // initialize views
        mAdapter = new AdapterSchoolList(new ArrayList<>(), this);
        mRecyclerView = findViewById(R.id.recyclerViewSchoolList);

        // setup recycler view & adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // initialize view model instance
        mViewModel = new ViewModelProvider(this).get(ViewModelSchoolList.class);

        // observe for changes in schools data & errors
        mViewModel.getSchoolsLiveData().observe(this, schools -> {
            mAdapter.setSchools(schools);
            mSwipeRefreshLayout.setRefreshing(false); // stop the animation
        });

        mViewModel.getErrorLiveData().observe(this, error -> {
            mSwipeRefreshLayout.setRefreshing(false); // stop the animation

            Toast.makeText(this, R.string.no_data_now, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: " + error);
        });

        // setup refresh listener
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this::fetchData);

        fetchData();

    }

    // handle click events on school items
    @Override
    public void onSchoolClick(ModelSchools school) {
        Intent intent = new Intent(this, ActivitySatScores.class);
        intent.putExtra(ActivitySatScores.EXTRA_SCHOOL_DBN, school);
        startActivity(intent);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // fetches / refreshes school data
    private void fetchData() {
        Log.d(TAG, "Fetching data");

        mSwipeRefreshLayout.setRefreshing(true);
        mViewModel.loadSchools();
    }

}