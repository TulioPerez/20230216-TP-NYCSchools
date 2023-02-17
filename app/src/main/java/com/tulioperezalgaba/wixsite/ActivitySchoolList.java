package com.tulioperezalgaba.wixsite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivitySchoolList extends AppCompatActivity implements AdapterSchoolList.OnSchoolClickListener {

    private RecyclerView mRecyclerView;
    private AdapterSchoolList mAdapter;
    private RepoSchool mRepoSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_school_list);

        mRecyclerView = findViewById(R.id.recyclerViewSchoolList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterSchoolList(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);

        mRepoSchool = new RepoSchool();

        loadSchools();
    }

    private void loadSchools() {
        try {
            List<ModelSchool> schools = mRepoSchool.getSchools();
            mAdapter.setSchools(schools);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading schools", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSchoolClick(ModelSchool school) {
        Intent intent = new Intent(this, ActivitySchoolDetail.class);
        intent.putExtra(ActivitySchoolDetail.EXTRA_SCHOOL_DBN, school.getDbn());
        startActivity(intent);
    }
}

