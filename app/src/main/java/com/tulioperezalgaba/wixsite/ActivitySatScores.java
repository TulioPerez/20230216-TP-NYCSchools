package com.tulioperezalgaba.wixsite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

public class ActivitySatScores extends AppCompatActivity {
    private static final String SCHOOL_EXTRA = "school_extra";
    public static final String EXTRA_SCHOOL_DBN = "extra_school_dbn";

    private ViewModelSATScores schoolDetailViewModel;
    private TextView schoolNameTextView;
    private TextView satReadingTextView;
    private TextView satMathTextView;
    private TextView satWritingTextView;

//    public static Intent intentToDetailActivity(Context context, ModelSchool school) {
//        Intent intent = new Intent(context, ActivitySatScores.class);
//        intent.putExtra(SCHOOL_EXTRA, school);
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        schoolDetailViewModel = new ViewModelProvider(this).get(ViewModelSATScores.class);

        ModelSchool school = getIntent().getParcelableExtra(SCHOOL_EXTRA);
        if (school != null) {

            schoolDetailViewModel.loadSchoolDetails(school.getDbn());
            schoolDetailViewModel.getSchoolLiveData().observe(this, this::updateSchoolDetails);
            schoolDetailViewModel.getErrorLiveData().observe(this, this::showError);

            schoolNameTextView.setText(school.getSchool_name());
            schoolNameTextView = findViewById(R.id.textViewSchoolName);

            satReadingTextView = findViewById(R.id.textViewSATReading);
            satMathTextView = findViewById(R.id.textViewSATMath);
            satWritingTextView = findViewById(R.id.textViewSATEnglish);

        } else {
            Log.d("ActivitySatScores", "School data not found");
        }
    }

    private void updateSchoolDetails(ModelSchool school) {
        Log.d("ActivitySatScores", "updateSchoolDetails: " + school.getSchool_name());
        if (school.getSatScores() != null) {
            satReadingTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_critical_reading_avg_score()));
            satMathTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_math_avg_score()));
            satWritingTextView.setText(String.valueOf(school.getSatScores().get(0).getSat_writing_avg_score()));
        } else {
            satReadingTextView.setText(getString(R.string.no_data));
            satMathTextView.setText(getString(R.string.no_data));
            satWritingTextView.setText(getString(R.string.no_data));
        }

    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        schoolDetailViewModel = null;

        super.onDestroy();
    }
}