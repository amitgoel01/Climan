package com.crm.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.crm.R;
import com.crm.database.entity.EmployeeEntity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.BeanHolder> {

 private List<EmployeeEntity> mList;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClick onEmployeeItemClick;

    public DataAdapter(List<EmployeeEntity> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.mList = list;
        this.context = context;
        this.onEmployeeItemClick = (OnItemClick) context;
    }

    public void setList(List<EmployeeEntity> list) {
        mList = list;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: " + mList.get(position));
        if(mList.size() >0) {

            String space = " : ";
            String empName = context.getResources().getString(R.string.emp_name) + space+ mList.get(position).getEmpName();
            holder.editTextName.setText(empName);
            String empId = context.getResources().getString(R.string.emp_id) + space + mList.get(position).getEmpId();
            holder.editTextEmpID.setText(empId);
            String businessUnit = context.getResources().getString(R.string.emp_bu) + space + mList.get(position).getBusinessUnit();
            holder.editTextBU.setText(businessUnit);
            String emailId = context.getResources().getString(R.string.email_id) + space + mList.get(position).getEmailId();
            holder.editTextEmailID.setText(emailId);
            String country = context.getResources().getString(R.string.country) + space + mList.get(position).getCountry();
            holder.countrySpinner.setText(country);
            String city = context.getResources().getString(R.string.city) + space + mList.get(position).getCity();
            holder.citySpinner.setText(city);
            String state = context.getResources().getString(R.string.state) + space + mList.get(position).getState();
            holder.stateSpinner.setText(state);
            String designation = context.getResources().getString(R.string.designation) + space + mList.get(position).getDesignation();
            holder.designation.setText(designation);
            String jobTitle = context.getResources().getString(R.string.job_type) + space + mList.get(position).getJobType();
            holder.designation.setText(jobTitle);
            String rpManager = context.getResources().getString(R.string.reporting_manager) + space + mList.get(position).getRpManager();
            holder.reportingManager.setText(rpManager);
            String phNumber = context.getResources().getString(R.string.phone_number) + space + mList.get(position).getPhNumber();
            holder.contactNumber.setText(phNumber);
            String doj = context.getResources().getString(R.string.doj) + space + mList.get(position).getDoj();
            holder.doj.setText(doj);
            String dob = context.getResources().getString(R.string.dob) + space + mList.get(position).getDob();
            holder.dob.setText(dob);
            String tenure = context.getResources().getString(R.string.tenure) + space + mList.get(position).getTenure();
            holder.tenure.setText(tenure);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*TextView textViewContent;
        TextView textViewTitle;*/
        private TextView editTextName;
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
        private TextView tenure;

        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            editTextName  =itemView.findViewById(R.id.TextViewName);
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
            dob = itemView.findViewById(R.id.dob);
        }

        @Override
        public void onClick(View view) {
            onEmployeeItemClick.onEmployeeClick(getAdapterPosition());
        }
    }


    public interface OnItemClick {
        void onEmployeeClick(int pos);
    }
}
