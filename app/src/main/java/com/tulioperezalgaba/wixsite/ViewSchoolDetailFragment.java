//package com.tulioperezalgaba.wixsite;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//public class ViewSchoolDetailFragment extends Fragment {
//    private ViewModelSATScores viewModel;
//    private TextView schoolNameTextView;
//    private TextView englishScoreTextView;
//    private TextView mathScoreTextView;
//    private TextView readingScoreTextView;
//    private String dbn;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_school_detail, container, false);
//        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
//
//        schoolNameTextView = view.findViewById(R.id.textViewSchoolName);
//        englishScoreTextView = view.findViewById(R.id.textViewSATEnglish);
//        mathScoreTextView = view.findViewById(R.id.textViewSATMath);
//        readingScoreTextView = view.findViewById(R.id.textViewSATReading);
//        dbn = getArguments().getString(ActivitySatScores.EXTRA_SCHOOL_DBN);
//        viewModel = viewModelProvider.get(ViewModelSATScores.class);
//
////        viewModel = new ViewModelProvider(this).get(ViewModelSATScores.class);
//        viewModel.getSchoolLiveData().observe(getViewLifecycleOwner(), this::updateSchoolDetails);
//        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::showError);
//        viewModel.loadSchoolDetails(dbn);
//        return view;
//    }
//
//    private void updateSchoolDetails(ModelSchool school) {
//        schoolNameTextView.setText(school.getSchool_name());
//        if (school.getSatScores() != null) {
//            englishScoreTextView.setText(String.valueOf(school.getSatScores().get(0).getWritingMean()));
//            mathScoreTextView.setText(String.valueOf(school.getSatScores().get(0).getMathMean()));
//            readingScoreTextView.setText(String.valueOf(school.getSatScores().get(0).getCriticalReadingMean()));
//        }
//    }
//
//    private void showError(String error) {
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
//    }
//}
