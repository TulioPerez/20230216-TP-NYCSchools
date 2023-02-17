package com.tulioperezalgaba.wixsite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSchoolList extends RecyclerView.Adapter<AdapterSchoolList.ViewHolder> {

    private List<ModelSchool> mSchools;
    private OnSchoolClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ModelSchool school);
    }

    public AdapterSchoolList(List<ModelSchool> schools, OnSchoolClickListener listener) {
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
        ModelSchool school = mSchools.get(position);
        holder.bind(school);
    }

    @Override
    public int getItemCount() {
        return mSchools.size();
    }

    public interface OnSchoolClickListener {
        void onSchoolClick(ModelSchool school);
    }

    public void setSchools(List<ModelSchool> schools) {
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

        public void bind(ModelSchool school) {
            mTextViewSchoolName.setText(school.getSchoolName());
        }

        @Override
        public void onClick(View view) {
            mListener.onSchoolClick(mSchools.get(getAdapterPosition()));
        }
    }
}

