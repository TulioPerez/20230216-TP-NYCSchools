package com.tulioperezalgaba.wixsite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SchoolDetailFragment extends Fragment {
    private SchoolDetailViewModel viewModel;
    private TextView schoolNameTextView;
//    private TextView schoolAddressTextView;
//    private TextView schoolCityTextView;
//    private TextView schoolStateTextView;
//    private TextView schoolZipTextView;
//    private TextView schoolPhoneTextView;
//    private TextView schoolEmailTextView;
    private TextView englishScoreTextView;
    private TextView mathScoreTextView;
    private TextView readingScoreTextView;
    private String dbn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_school_detail, container, false);
        schoolNameTextView = view.findViewById(R.id.textViewSchoolName);
//        schoolAddressTextView = view.findViewById(R.id.school_address_text_view);
//        schoolCityTextView = view.findViewById(R.id.school_city_text_view);
//        schoolStateTextView = view.findViewById(R.id.school_state_text_view);
//        schoolZipTextView = view.findViewById(R.id.school_zip_text_view);
//        schoolPhoneTextView = view.findViewById(R.id.school_phone_text_view);
//        schoolEmailTextView = view.findViewById(R.id.school_email_text_view);
        englishScoreTextView = view.findViewById(R.id.textViewSATEnglish);
        mathScoreTextView = view.findViewById(R.id.textViewSATMath);
        readingScoreTextView = view.findViewById(R.id.textViewSATReading);
        dbn = getArguments().getString(SchoolDetailActivity.EXTRA_SCHOOL_DBN);
        viewModel = new ViewModelProvider(this).get(SchoolDetailViewModel.class);
        viewModel.getSchoolLiveData().observe(getViewLifecycleOwner(), this::updateSchoolDetails);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::showError);
        viewModel.loadSchoolDetails(dbn);
        return view;
    }

    private void updateSchoolDetails(School school) {
        schoolNameTextView.setText(school.getSchoolName());
//        schoolAddressTextView.setText(school.getPrimaryAddressLine1());
//        schoolCityTextView.setText(school.getCity());
//        schoolStateTextView.setText(school.getStateCode());
//        schoolZipTextView.setText(school.getZip());
//        schoolPhoneTextView.setText(school.getPhoneNumber());
//        schoolEmailTextView.setText(school.getEmail());
        if (school.getSatScores() != null) {
            englishScoreTextView.setText(String.valueOf(school.getSatScores().getCriticalReadingMean()));
            mathScoreTextView.setText(String.valueOf(school.getSatScores().getMathMean()));
            readingScoreTextView.setText(String.valueOf(school.getSatScores().getWritingMean()));
        }
    }

    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}
