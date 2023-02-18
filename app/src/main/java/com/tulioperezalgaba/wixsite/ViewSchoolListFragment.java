//package com.tulioperezalgaba.wixsite;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ViewSchoolListFragment extends Fragment {
//    private ViewModelSchoolList viewModel;
//    private RecyclerView recyclerView;
//    private AdapterSchoolList adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_school_list, container, false);
//        adapter = new AdapterSchoolList(new ArrayList<>(), this::onSchoolSelected);
//
//        recyclerView = view.findViewById(R.id.recyclerViewSchoolList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        viewModel = new ViewModelProvider(this).get(ViewModelSchoolList.class);
//        viewModel.getSchoolsLiveData().observe(getViewLifecycleOwner(), this::updateSchoolList);
//        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::showError);
//
//        return view;
//    }
//
//    private void updateSchoolList(List<ModelSchool> schools) {
//        adapter.setSchools(schools);
//    }
//
//    private void onSchoolSelected(ModelSchool school) {
//        Intent intent = new Intent(getActivity(), ActivitySatScores.class);
//        intent.putExtra(ActivitySatScores.EXTRA_SCHOOL_DBN, school.getDbn());
//        startActivity(intent);
//    }
//
//    private void showError(String error) {
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
//    }
//}