package com.crm.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.crm.R;
import com.crm.database.entity.EmployeeEntity;
import com.crm.database.entity.JobEntity;
import com.crm.databinding.ListItemBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.BeanHolder> {

 private List<JobEntity> mList;
    private Context context;
    private LayoutInflater layoutInflater;
//    private OnItemClick onEmployeeItemClick;
    private ListItemBinding mListItemBinding;

    public DataAdapter(List<JobEntity> list, Context context) {
        this.mList = list;
        this.context = context;
//        this.onEmployeeItemClick = (OnItemClick) context;
    }

    public void setList(List<JobEntity> list) {
        mList = list;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mListItemBinding = ListItemBinding.inflate(layoutInflater, parent, false);
        return new BeanHolder(mListItemBinding);
       /* View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new BeanHolder(view);*/
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: " + mList.get(position));
        if(mList.size() >0) {

            String space = " : ";
            String jobId = context.getResources().getString(R.string.job_id) + space+ mList.get(position).getJobId();
            holder.binding.jobId.setText(jobId);
            String jobTitle = context.getResources().getString(R.string.job_title) + space + mList.get(position).getJobTitle();
            holder.binding.jobTitle.setText(jobTitle);
            String jobDescription = context.getResources().getString(R.string.job_description) + space + mList.get(position).getJobDescription();
            holder.binding.jobDescription.setText(jobDescription);
            String jobPostedDate = context.getResources().getString(R.string.job_posted_date) + space + mList.get(position).getJobPostedDate();
            holder.binding.jobPostedDate.setText(jobPostedDate);
            String jobExpDate = context.getResources().getString(R.string.job_expiry_date) + space + mList.get(position).getJobLastDate();
            holder.binding.jobExpiryDate.setText(jobExpDate);
            String country = context.getResources().getString(R.string.country) + space + mList.get(position).getJobCountry();
            holder.binding.countryPickerSearch.setText(country);
            String city = context.getResources().getString(R.string.city) + space + mList.get(position).getJobCity();
            holder.binding.cityPickerSearch.setText(city);
            String state = context.getResources().getString(R.string.state) + space + mList.get(position).getJobState();
            holder.binding.statePickerSearch.setText(state);
            String category = context.getResources().getString(R.string.job_category) + space + mList.get(position).getJobCategory();
            holder.binding.jobType.setText(category);
            String jobDepartment = context.getResources().getString(R.string.job_department) + space + mList.get(position).getJobDepartment();
            holder.binding.department.setText(jobDepartment);
            String emailId = context.getResources().getString(R.string.email_id) + space + mList.get(position).getEmailId();
            holder.binding.emailId.setText(emailId);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        /*TextView textViewContent;
        TextView textViewTitle;*/
        /*private TextView editTextName;
        private TextView editTextEmpID;
        private TextView editTextBU;
        private TextView editTextEmailID;
        private TextView countrySpinner;
        private TextView citySpinner;
        private TextView stateSpinner;
        private TextView designation;
        private TextView reportingManager;
        private TextView contactNumber;
        private  TextView doj;
        private TextView dob;
        private TextView tenure;*/
        private final ListItemBinding binding;

        public BeanHolder(ListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            binding = listItemBinding;

//            listItemBinding.getRoot().setOnClickListener(this);

            /*editTextName  =itemView.findViewById(R.id.TextViewName);
            editTextEmpID = itemView.findViewById(R.id.TextViewEmpID);
            editTextBU = itemView.findViewById(R.id.TextViewBU);
            editTextEmailID = itemView.findViewById(R.id.TextViewEmailID);
            designation = itemView.findViewById(R.id.designation);
            reportingManager = itemView.findViewById(R.id.reporting_manager);
            contactNumber = itemView.findViewById(R.id.contact_number);
            tenure = itemView.findViewById(R.id.tenure);

            countrySpinner = itemView.findViewById(R.id.country_picker_search);
            citySpinner = itemView.findViewById(R.id.city_picker_search);
            stateSpinner = itemView.findViewById(R.id.state_picker_search);
            doj = itemView.findViewById(R.id.doj);
            dob = itemView.findViewById(R.id.dob);*/
        }

       /* @Override
        public void onClick(View view) {
            onEmployeeItemClick.onEmployeeClick(getAdapterPosition());
        }*/
    }


 /*   public interface OnItemClick {
        void onEmployeeClick(int pos);
    }*/
}
