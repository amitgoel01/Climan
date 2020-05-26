package com.crm.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crm.R;
import com.crm.Utils.Constants;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.JobEntity;
import com.crm.databinding.FragmentSearchJobBinding;
import com.crm.viewmodel.JobListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchJobFragment extends Fragment implements View.OnClickListener{
    private FragmentSearchJobBinding mSearchJobBinding;
    private  Spinner mSpinner;
    private JobListViewModel mViewModel;
    private static final String TAG = SearchJobFragment.class.getName();
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private HashMap<String, String> map = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchJobBinding = FragmentSearchJobBinding.inflate(inflater, container, false);
        EmployeeDatabase.getInstance(getActivity());
        setUpViews();
        setToolbar();
        return(mSearchJobBinding.getRoot());
    }

    private void setUpViews() {
       mViewModel = ViewModelProviders.of(this).get(JobListViewModel.class);
       listAllJobs();
       mViewModel.getSearchResults().observe(getActivity(),
               new Observer<List<JobEntity>>() {
                   @Override
                   public void onChanged(@Nullable final List<JobEntity> jobList) {
                       if (jobList.size() > 0) {
//                           updateJobContent(jobList.get(0));
                           dataAdapter.setList(jobList);
                           dataAdapter.notifyDataSetChanged();
                       } else {
                           Toast.makeText(getActivity(), getResources().getString(R.string.no_match_found), Toast.LENGTH_LONG).show();
//                           Log.d(TAG, "No Match found");
                       }
                   }
               });


       mSpinner = mSearchJobBinding.contentSearchJob.searchSpinner;

       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
               R.array.job_search_input, android.R.layout.simple_spinner_dropdown_item);

       mSpinner.setAdapter(adapter);
       mSearchJobBinding.contentSearchJob.searchButton.setOnClickListener(this);
        recyclerView = mSearchJobBinding.contentSearchJob.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<JobEntity>jobList = new ArrayList<>();
        dataAdapter = new DataAdapter(jobList, getContext());
        recyclerView.setAdapter(dataAdapter);
        initializeMapping();
    }

    private void setToolbar() {
        Toolbar toolbar = mSearchJobBinding.toolBar.mainToolbar;;
        TextView main_title = mSearchJobBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.search_job));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mSearchJobBinding.getRoot()).navigate(R.id.action_searchJobFragment_to_dashboardFragment);
            }
        });
    }




 private void updateJobContent(JobEntity employee) {
//        TextView text = mSearchJobBinding.contentSearchJob.contentSearchProfile.TextViewEmpName;
     /*   text.setText(employee.getEmpName());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.TextViewEmpID;
        text.setText(employee.getEmpId());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.TextViewBU;
        text.setText(employee.getBusinessUnit());*/
        /*text = mSearchJobBinding.contentSearchJob.contentSearchProfile.TextViewEmailID;
        text.setText(employee.getEmailId());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.countryPickerSearch;*/
      /*  text.setText(employee.getCountry());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.cityPickerSearch;
        text.setText(employee.getCity());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.statePickerSearch;
        text.setText(employee.getState());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.designation;
        text.setText(employee.getDesignation());
         text = mSearchJobBinding.contentSearchJob.contentSearchProfile.jobTitle;
         text.setText(employee.getJobType());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.reportingManager;
        text.setText(employee.getRpManager());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.contactNumber;
        text.setText(employee.getPhNumber());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.doj;
        text.setText(employee.getDoj());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.dob;
        text.setText(employee.getDob());
        text = mSearchJobBinding.contentSearchJob.contentSearchProfile.tenure;
        text.setText(employee.getTenure());*/

    }

    private void initializeMapping() {
        map.put(getResources().getString(R.string.job_id), Constants.JOB_ID);
        map.put(getResources().getString(R.string.job_posted_date), Constants.JOB_POSTED_DATE);
        map.put(getResources().getString(R.string.country), Constants.JOB_COUNTRY);
        map.put(getResources().getString(R.string.city), Constants.JOB_CITY);
        map.put(getResources().getString(R.string.state), Constants.JOB_STATE);
        map.put(getResources().getString(R.string.job_department), Constants.JOB_DEPARTMENT);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchButton) {
            mViewModel = ViewModelProviders.of(this).get(JobListViewModel.class);
            EditText  text = mSearchJobBinding.contentSearchJob.searchText;
            if(mSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.all_jobs))){
                listAllJobs();
            }
            else{
                mViewModel.findJob(map.get(mSpinner.getSelectedItem().toString()), text.getText().toString());
            }
        }
    }
    
    private void listAllJobs() {
        mViewModel.listAllJobs().observe(getActivity(), new Observer<List<JobEntity>>() {
            @Override
            public void onChanged(List<JobEntity> jobEntities) {
                dataAdapter.setList(jobEntities);
                dataAdapter.notifyDataSetChanged();
            }
        });
    }
}
