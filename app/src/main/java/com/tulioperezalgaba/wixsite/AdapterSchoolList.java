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
    private OnSchoolSelectedListener mListener;

    public AdapterSchoolList(List<ModelSchool> schools, OnSchoolSelectedListener listener) {
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

        // alternate item bg
        if (position % 2 == 0) {
            holder.mTextViewSchoolName.setBackgroundResource(R.drawable.background_lt);
        } else {
            holder.mTextViewSchoolName.setBackgroundResource(R.drawable.background_dk);
        }
    }

    @Override
    public int getItemCount() {
        return mSchools.size();
    }

    public interface OnSchoolSelectedListener {
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
            mTextViewSchoolName.setText(school.getSchool_name());
        }

        @Override
        public void onClick(View view) {
            mListener.onSchoolClick(mSchools.get(getAdapterPosition()));
        }
    }
}

