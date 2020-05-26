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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crm.R;
import com.crm.Utils.AddressUtils;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.EmployeeEntity;
import com.crm.databinding.FragmentAddJobBinding;
import com.crm.databinding.FragmentAddProfileBinding;
import com.crm.viewmodel.EmployeeListViewModel;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class AddProfileFragment extends Fragment implements DatePickerFragment.IUpdateDate, View.OnClickListener{
    private FragmentAddProfileBinding mProfileBinding;
    private final static String TAG = AddProfileFragment.class.getName();

    private EditText editTextName;
    private EditText editTextEmpID;
    private EditText editTextBU;
    private EditText editTextEmailID;
    private AutoCompleteTextView countrySpinner;
    private AutoCompleteTextView citySpinner;
    private AutoCompleteTextView stateSpinner;
    private EditText designation;
    private EditText jobTitle;
    private EditText reportingManager;
    private EditText mobileNumber;
    private  EditText doj;
    private EditText dob;
    private EditText tenure;
    private Button saveButton;


    final Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    private EmployeeEntity employee;

    private DataAdapter employeeAdapter;


    private EmployeeListViewModel mViewModel;

    private EmployeeDatabase employeeDatabase;
    private AddressUtils mAddressUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProfileBinding = mProfileBinding.inflate(inflater, container, false);
        setUpViews();
        setToolbar();
        setOnClickListeners();
        return(mProfileBinding.getRoot());
    }

    private void setUpViews() {
        mViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        mAddressUtils = AddressUtils.getInstance();
        mAddressUtils.setContext(getContext());
        mAddressUtils.initialize();

        editTextName = mProfileBinding.contentAddProfile.EditTextName;
        editTextEmpID = mProfileBinding.contentAddProfile.EditTextEmpID;
        editTextBU = mProfileBinding.contentAddProfile.EditTextBU;
        editTextEmailID = mProfileBinding.contentAddProfile.EditTextEmailID;
        designation = mProfileBinding.contentAddProfile.designation;
        jobTitle = mProfileBinding.contentAddProfile.jobTitle;
        reportingManager = mProfileBinding.contentAddProfile.reportingManager;
        mobileNumber = mProfileBinding.contentAddProfile.mobileNumber;
        tenure = mProfileBinding.contentAddProfile.tenure;
        countrySpinner = mProfileBinding.contentAddProfile.countryPickerSearch;
        citySpinner = mProfileBinding.contentAddProfile.cityPickerSearch;
        stateSpinner = mProfileBinding.contentAddProfile.statePickerSearch;
        doj = mProfileBinding.contentAddProfile.doj;
        dob = mProfileBinding.contentAddProfile.dob;
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH);
        mDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        employeeDatabase = EmployeeDatabase.getInstance(getActivity());
        saveButton = mProfileBinding.contentAddProfile.saveButton;
    }

    private void setToolbar() {
        Toolbar toolbar = mProfileBinding.toolBar.mainToolbar;
        TextView main_title = mProfileBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.add_profile));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mProfileBinding.getRoot()).navigate(R.id.action_addProfileFragment_to_dashboardFragment);
            }
        });
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private void setOnClickListeners() {
        saveButton.setOnClickListener(this);
        doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(AddProfileFragment.this, doj);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(AddProfileFragment.this, dob);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        countrySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,mAddressUtils.getCountryList());
                countrySpinner.setThreshold(0);//will start working from first character
                countrySpinner.setAdapter(adapter);
            }
        });

        citySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,mAddressUtils.getCityList());
                citySpinner.setThreshold(0);//will start working from first character

                citySpinner.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            }
        });

        stateSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,mAddressUtils.getStateList());
                stateSpinner.setThreshold(0);//will start working from first character
                stateSpinner.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveButton) {
            employee = new EmployeeEntity(editTextName.getText().toString(), editTextEmpID.getText().toString(), editTextBU.getText().toString(), editTextEmailID.getText().toString(),
                    countrySpinner.getText().toString(), citySpinner.getText().toString(), stateSpinner.getText().toString(),
                    designation.getText().toString(), jobTitle.getText().toString(), reportingManager.getText().toString(), mobileNumber.getText().toString(),
                    doj.getText().toString(), dob.getText().toString(), tenure.getText().toString());

            mViewModel.insertEmployee(employee);
            Navigation.findNavController(mProfileBinding.getRoot()).navigate(R.id.action_addProfileFragment_to_dashboardFragment);
        }
    }

    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
    }
}
