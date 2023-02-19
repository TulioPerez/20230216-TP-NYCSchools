package com.tulioperezalgaba.wixsite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/* Adapter for ModelSchool object list, handling item click events and providing alternating item background colors */

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

        // alternate the background colors of individual items in the view for clarity
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

    // handle click on individual school item
    public interface OnSchoolSelectedListener {
        void onSchoolClick(ModelSchool school);
    }

    // update schools list & notify the adapter to refresh the UI
    public void setSchools(List<ModelSchool> schools) {
        mSchools = schools;
        notifyDataSetChanged();
    }


    /* Click listener interface for handling click events on individual schools in the list */
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

        // handle click events on school items
        @Override
        public void onClick(View view) {
            mListener.onSchoolClick(mSchools.get(getAdapterPosition()));
        }
    }
}

