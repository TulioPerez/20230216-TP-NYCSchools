package com.tulioperezalgaba.wixsite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ActivitySchoolDetail extends AppCompatActivity {
    private static final String SCHOOL_EXTRA = "school_extra";
    public static final String EXTRA_SCHOOL_DBN = "extra_school_dbn";

    private ViewModelSchoolDetail schoolDetailViewModel;
    private TextView schoolNameTextView;
    private TextView satReadingTextView;
    private TextView satMathTextView;
    private TextView satWritingTextView;

    public static Intent newIntent(Context context, ModelSchool school) {
        Intent intent = new Intent(context, ActivitySchoolDetail.class);
        intent.putExtra(SCHOOL_EXTRA, school);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        schoolDetailViewModel = new ViewModelProvider(this).get(ViewModelSchoolDetail.class);

        ModelSchool school = getIntent().getParcelableExtra(SCHOOL_EXTRA);
        if (school != null) {

            schoolNameTextView = findViewById(R.id.textViewSchoolName);
            schoolNameTextView.setText(school.getSchoolName());
            satReadingTextView = findViewById(R.id.textViewSATReading);
            satMathTextView = findViewById(R.id.textViewSATMath);
            satWritingTextView = findViewById(R.id.textViewSATEnglish);

            schoolDetailViewModel.getSchoolLiveData().observe(this, this::updateSchoolDetails);
            schoolDetailViewModel.getErrorLiveData().observe(this, this::showError);
            schoolDetailViewModel.loadSchoolDetails(school.getDbn());
        } else {
            System.out.println("School data not found");
        }
    }

    private void updateSchoolDetails(ModelSchool school) {
        if (school.getSatScores() != null) {
            satReadingTextView.setText(String.valueOf(school.getSatScores().get(0).getCriticalReadingMean()));
            satMathTextView.setText(String.valueOf(school.getSatScores().get(0).getMathMean()));
            satWritingTextView.setText(String.valueOf(school.getSatScores().get(0).getWritingMean()));
        } else {
            satReadingTextView.setText(getString(R.string.no_data));
            satMathTextView.setText(getString(R.string.no_data));
            satWritingTextView.setText(getString(R.string.no_data));
        }
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}