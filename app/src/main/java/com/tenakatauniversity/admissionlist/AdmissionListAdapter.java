package com.tenakatauniversity.admissionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tenakatauniversity.databinding.AdmissionListRecyclerViewLayoutBinding;
import com.tenakatauniversity.studentapplication.StudentApplication;

import java.util.List;

public class AdmissionListAdapter extends RecyclerView.Adapter<AdmissionListAdapter.AdmissionListViewHolder> {

    private List<StudentApplication> studentApplications;

    public AdmissionListAdapter(List<StudentApplication> studentApplications) {
        this.studentApplications = studentApplications;
    }

    @NonNull
    @Override
    public AdmissionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdmissionListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionListViewHolder holder, int position) {
        holder.bind(studentApplications.get(position));
    }

    @Override
    public int getItemCount() {
        return studentApplications.size();
    }

    public void submitList (List<StudentApplication> studentApplications) {
        this.studentApplications = studentApplications;
        notifyDataSetChanged();
    }

    static class AdmissionListViewHolder extends RecyclerView.ViewHolder {
        private AdmissionListRecyclerViewLayoutBinding binding;

        public static AdmissionListViewHolder create(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            AdmissionListRecyclerViewLayoutBinding binding = AdmissionListRecyclerViewLayoutBinding.inflate(inflater, parent, false);
            return new AdmissionListViewHolder(binding);
        }

        public AdmissionListViewHolder(AdmissionListRecyclerViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StudentApplication studentApplication) {
            binding.setStudentApplication(studentApplication);
            binding.executePendingBindings();
        }

    }

}
