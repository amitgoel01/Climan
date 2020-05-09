package com.crm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crm.R;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.EmployeeEntity;
import com.crm.databinding.FragmentAddJobBinding;
import com.crm.viewmodel.EmployeeListViewModel;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class AddJobFragment extends Fragment {
    private final static String TAG = AddJobFragment.class.getName();
    private EditText mJobId;
    private EditText mJobTitle;
    private EditText mJobDescription;
    private EditText mJobPostedDate;
    private EditText mJobLastDate;
    private EditText mJobExpiryDate;
    private EditText mJobCountry;
    private EditText mJobCity;
    private EditText mJobState;
    private EditText mJobType;
    private EditText mJobDepartment;
    private EditText mEmailId;
    private EditText mPhNumber;
    private Button saveButton;

    private  String[] country;
    private  String[] names;
    private  String[] states;

    final Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    private EmployeeEntity employee;
    private DataAdapter employeeAdapter;


    private EmployeeListViewModel mViewModel;

    private EmployeeDatabase employeeDatabase;
    private FragmentAddJobBinding mAddJobBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAddJobBinding = mAddJobBinding.inflate(inflater, container, false);
        setUpViews();
        setToolbar();

        return(mAddJobBinding.getRoot());
    }

    private void setUpViews() {
        mViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        /*mJobId = findViewById(R.id.jobId);
        mJobTitle = findViewById(R.id.jobTitle);
        mJobDescription = findViewById(R.id.jobDescription);
        mJobPostedDate = findViewById(R.id.job_posted_date);
        mJobLastDate = findViewById(R.id.job_last_Date);

        mJobCountry = findViewById(R.id.country_picker_search);
        mJobCity = findViewById(R.id.city_picker_search);
        mJobState = findViewById(R.id.state_picker_search);

        mJobType = findViewById(R.id.jobType);
        mJobDepartment = findViewById(R.id.department);


        mJobPostedDate = findViewById(R.id.job_posted_date);
        mJobLastDate = findViewById(R.id.job_last_Date);

        mEmailId = findViewById(R.id.email_id);
        mPhNumber = findViewById(R.id.phone_number);

        saveButton = findViewById(R.id.ButtonSaveButton);

        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH);
        mDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        employeeDatabase = EmployeeDatabase.getInstance(this);*/
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
    }

}
