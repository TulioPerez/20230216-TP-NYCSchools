package com.tulioperezalgaba.wixsite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/* Detail activity displaying SAT scores for school in initial activity */

public class ActivityDetailSatScores extends AppCompatActivity {
    private final String TAG = "ActivityDetailSatScores";
    public static final String EXTRA_SCHOOL_DBN = "extra_school_dbn";

    private ViewModelSATScores schoolDetailViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView schoolNameTextView;
    private TextView satReadingTextView;
    private TextView satMathTextView;
    private TextView satWritingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // initialize views
        schoolNameTextView = findViewById(R.id.textViewSchoolName);
        satReadingTextView = findViewById(R.id.textViewSATReading);
        satMathTextView = findViewById(R.id.textViewSATMath);
        satWritingTextView = findViewById(R.id.textViewSATEnglish);

        // setup refresh listener
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchData();
        });

        // initialize view model instance & fetch data
        schoolDetailViewModel = new ViewModelProvider(this).get(ViewModelSATScores.class);

        fetchData();
    }

    // updates the UI with SAT score data
    private void updateSchoolDetails(ModelSchool school) {
        swipeRefreshLayout.setRefreshing(false);

        if (school.getSatScores() != null && !school.getSatScores().isEmpty()) {
            satReadingTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_critical_reading_avg_score()));
            satMathTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_math_avg_score()));
            satWritingTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_writing_avg_score()));

        } else {
            Toast.makeText(this, R.string.no_data_now, Toast.LENGTH_SHORT).show();
            satReadingTextView.setText(getString(R.string.null_data));
            satMathTextView.setText(getString(R.string.null_data));
            satWritingTextView.setText(getString(R.string.null_data));

            Log.d(TAG, "updateSchoolDetails: " + "Scores list is null or empty");
        }
    }

    // fetches / refreshes SAT score data
    private void fetchData() {
        Log.d(TAG, "Refreshing data");

        ModelSchool school = getIntent().getParcelableExtra(EXTRA_SCHOOL_DBN);
        if (school != null) {
            Log.d(TAG, "School data fetched successfully");
            schoolNameTextView.setText(school.getSchool_name());
            schoolDetailViewModel.loadSchoolDetails(school.getDbn());

            // check for changes to live data
            schoolDetailViewModel.getSchoolLiveData().observe(this, this::updateSchoolDetails);
            schoolDetailViewModel.getErrorLiveData().observe(this, this::showError);

        } else {
            Log.d(TAG, "School data not found");
        }
    }

    private void showError(String error) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    // improved transition back to ActivitySchoolList
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        schoolDetailViewModel = null;
        super.onDestroy();
    }

}