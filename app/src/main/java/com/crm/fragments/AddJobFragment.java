package com.crm.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crm.R;
import com.crm.Utils.AddressUtils;
import com.crm.Utils.Constants;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.JobEntity;
import com.crm.database.entity.JobEntity;
import com.crm.databinding.FragmentAddJobBinding;
import com.crm.viewmodel.JobListViewModel;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class AddJobFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate {
    private final static String TAG = AddJobFragment.class.getName();

    private AddressUtils mAddressUtils;
    private JobEntity job;
    private JobListViewModel mViewModel;
    private FragmentAddJobBinding mAddJobBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAddJobBinding = mAddJobBinding.inflate(inflater, container, false);
        setUpViews();
        setToolbar();
        setOnClickListeners();
        return(mAddJobBinding.getRoot());
    }

    private void setUpViews() {
        mViewModel = ViewModelProviders.of(this).get(JobListViewModel.class);
        mAddressUtils = AddressUtils.getInstance();
        mAddressUtils.setContext(getContext());
        mAddressUtils.initialize();

    }

    private void setToolbar() {
        Toolbar toolbar = mAddJobBinding.toolBar.mainToolbar;
        TextView main_title = mAddJobBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.add_job));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mAddJobBinding.getRoot()).navigate(R.id.action_addJobFragment_to_dashboardFragment);
            }
        });
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveButton) {
            job = new JobEntity(mAddJobBinding.contentAddJob.jobId.getText().toString(),
                    mAddJobBinding.contentAddJob.jobTitle.getText().toString(),
                    mAddJobBinding.contentAddJob.jobDescription.getText().toString(),
                    mAddJobBinding.contentAddJob.jobPostedDate.getText().toString(),
                    mAddJobBinding.contentAddJob.jobExpiryDate.getText().toString(),
                    mAddJobBinding.contentAddJob.countryPickerSearch.getText().toString(),
                    mAddJobBinding.contentAddJob.cityPickerSearch.getText().toString(),
                    mAddJobBinding.contentAddJob.statePickerSearch.getText().toString(),
                    mAddJobBinding.contentAddJob.jobType.getText().toString(),
                    mAddJobBinding.contentAddJob.department.getText().toString(),
                    mAddJobBinding.contentAddJob.emailId.getText().toString());
            mViewModel.insertJob(job);
            mViewModel.findJob(Constants.JOB_CITY, "bengaluru");
            Navigation.findNavController(mAddJobBinding.getRoot()).navigate(R.id.action_addJobFragment_to_dashboardFragment);
        }
    }

    private void setOnClickListeners() {
        mAddJobBinding.contentAddJob.saveButton.setOnClickListener(this);
        mAddJobBinding.contentAddJob.jobPostedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(AddJobFragment.this,
                        mAddJobBinding.contentAddJob.jobPostedDate);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        mAddJobBinding.contentAddJob.jobExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(AddJobFragment.this,
                        mAddJobBinding.contentAddJob.jobExpiryDate);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        mAddJobBinding.contentAddJob.countryPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mAddJobBinding.contentAddJob.countryPickerSearch, mAddressUtils.getCountryList()));
        mAddJobBinding.contentAddJob.cityPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mAddJobBinding.contentAddJob.cityPickerSearch, mAddressUtils.getCityList()));
        mAddJobBinding.contentAddJob.statePickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mAddJobBinding.contentAddJob.statePickerSearch, mAddressUtils.getStateList()));
    }

    private View.OnFocusChangeListener onFocusChangeListener(AutoCompleteTextView view, List<String> list) {
        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(), android.R.layout.simple_list_item_1, list);

                view.setThreshold(0);//will start working from first character
                view.setAdapter(adapter);
            }
        };
        return focusChangeListener;
    }




    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
    }
}
