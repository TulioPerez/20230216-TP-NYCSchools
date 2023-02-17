package com.tulioperezalgaba.wixsite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SchoolDetailActivity extends AppCompatActivity {
    private static final String SCHOOL_EXTRA = "school_extra";
    public static final String EXTRA_SCHOOL_DBN = "extra_school_dbn";

    private SchoolListViewModel schoolListViewModel;
    private SchoolDetailViewModel schoolDetailViewModel;
    private TextView schoolNameTextView;
//    private TextView overviewParagraphTextView;
//    private TextView locationTextView;
//    private TextView phoneNumberTextView;
    private TextView satReadingTextView;
    private TextView satMathTextView;
    private TextView satWritingTextView;

    public static Intent newIntent(Context context, School school) {
        Intent intent = new Intent(context, SchoolDetailActivity.class);
        intent.putExtra(SCHOOL_EXTRA, school);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

//        schoolDetailViewModel = new ViewModelProvider(this).get(SchoolDetailViewModel.class);
        schoolListViewModel = new ViewModelProvider(this).get(SchoolListViewModel.class);

        School school = getIntent().getParcelableExtra(SCHOOL_EXTRA);
        schoolNameTextView = findViewById(R.id.textViewSchoolName);
        schoolNameTextView.setText(school.getSchoolName());

//        overviewParagraphTextView = findViewById(R.id.textViewOverviewContent);
//        overviewParagraphTextView.setText(school.getOverviewParagraph());

//        locationTextView = findViewById(R.id.location_text_view);
//        locationTextView.setText(school.getLocation());

//        phoneNumberTextView = findViewById(R.id.textViewPhone);
//        phoneNumberTextView.setText(school.getPhoneNumber());

        satReadingTextView = findViewById(R.id.textViewSATReading);
        satMathTextView = findViewById(R.id.textViewSATMath);
        satWritingTextView = findViewById(R.id.textViewSATEnglish);

        schoolListViewModel.getSATScoreLiveData().observe(this, satScore -> {
            if (satScore != null) {
                satReadingTextView.setText(String.valueOf(satScore.getCriticalReadingMean()));
                satMathTextView.setText(String.valueOf(satScore.getMathMean()));
                satWritingTextView.setText(String.valueOf(satScore.getWritingMean()));
            } else {
                satReadingTextView.setText(getString(R.string.no_data));
                satMathTextView.setText(getString(R.string.no_data));
                satWritingTextView.setText(getString(R.string.no_data));
            }
        });

//        schoolDetailViewModel.getSATScoreLiveData().observe(this, satScore -> {
//            if (satScore != null) {
//                satReadingTextView.setText(String.valueOf(satScore.getSatCriticalReadingAvgScore()));
//                satMathTextView.setText(String.valueOf(satScore.getSatMathAvgScore()));
//                satWritingTextView.setText(String.valueOf(satScore.getSatWritingAvgScore()));
//            } else {
//                satReadingTextView.setText(getString(R.string.no_data));
//                satMathTextView.setText(getString(R.string.no_data));
//                satWritingTextView.setText(getString(R.string.no_data));
//            }
//        });

        schoolDetailViewModel.getErrorLiveData().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        schoolListViewModel.loadSATScoresForSchool(school.getDbn());
//        schoolDetailViewModel.loadSATScoresForSchool(school.getDbn());
    }
}
