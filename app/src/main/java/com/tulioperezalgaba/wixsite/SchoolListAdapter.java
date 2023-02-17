package com.tulioperezalgaba.wixsite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {

    private List<School> mSchools;
    private OnSchoolClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(School school);
    }

    public SchoolListAdapter(List<School> schools, OnSchoolClickListener listener) {
        mSchools = schools;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_school, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        School school = mSchools.get(position);
        holder.bind(school);
    }

    @Override
    public int getItemCount() {
        return mSchools.size();
    }

    public interface OnSchoolClickListener {
        void onSchoolClick(School school);
    }

    public void setSchools(List<School> schools) {
        mSchools = schools;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewSchoolName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewSchoolName = itemView.findViewById(R.id.text_school_name);
            itemView.setOnClickListener(this);
        }

        public void bind(School school) {
            mTextViewSchoolName.setText(school.getSchoolName());
        }

        @Override
        public void onClick(View view) {
            mListener.onSchoolClick(mSchools.get(getAdapterPosition()));
        }
    }
}

