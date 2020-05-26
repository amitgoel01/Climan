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
import com.crm.database.entity.EmployeeEntity;
import com.crm.databinding.FragmentSearchProfileBinding;
import com.crm.viewmodel.EmployeeListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class SearchProfileFragment extends Fragment implements View.OnClickListener{
    private FragmentSearchProfileBinding mSearchProfileBinding;
    private  Spinner mSpinner;
    private EmployeeListViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchProfileBinding = FragmentSearchProfileBinding.inflate(inflater, container, false);
        setUpViews();
        setToolbar();
        return(mSearchProfileBinding.getRoot());
    }

    private void setUpViews() {
       mViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
       mViewModel.getSearchResults().observe(getActivity(),
               new Observer<List<EmployeeEntity>>() {
                   @Override
                   public void onChanged(@Nullable final List<EmployeeEntity> employeeList) {
                       if (employeeList.size() > 0) {
                           updateEmployeeContent(employeeList.get(0));
                       } else {
                           Toast.makeText(getActivity(), getResources().getString(R.string.no_match_found), Toast.LENGTH_LONG).show();
//                           Log.d(TAG, "No Match found");
                       }
                   }
               });


       mSpinner = mSearchProfileBinding.searchProfileContent.searchSpinner;

       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
               R.array.search_input, android.R.layout.simple_spinner_dropdown_item);

       mSpinner.setAdapter(adapter);
       mSearchProfileBinding.searchProfileContent.searchButton.setOnClickListener(this);

    }

    private void setToolbar() {
        Toolbar toolbar = mSearchProfileBinding.toolBar.mainToolbar;;
        TextView main_title = mSearchProfileBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.search_profile));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mSearchProfileBinding.getRoot()).navigate(R.id.action_searchProfileFragment_to_dashboardFragment);
            }
        });
    }




 private void updateEmployeeContent(EmployeeEntity employee) {
        TextView text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.TextViewEmpName;
        text.setText(employee.getEmpName());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.TextViewEmpID;
        text.setText(employee.getEmpId());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.TextViewBU;
        text.setText(employee.getBusinessUnit());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.TextViewEmailID;
        text.setText(employee.getEmailId());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.countryPickerSearch;
        text.setText(employee.getCountry());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.cityPickerSearch;
        text.setText(employee.getCity());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.statePickerSearch;
        text.setText(employee.getState());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.designation;
        text.setText(employee.getDesignation());
         text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.jobTitle;
         text.setText(employee.getJobType());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.reportingManager;
        text.setText(employee.getRpManager());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.contactNumber;
        text.setText(employee.getPhNumber());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.doj;
        text.setText(employee.getDoj());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.dob;
        text.setText(employee.getDob());
        text = mSearchProfileBinding.searchProfileContent.contentSearchProfile.tenure;
        text.setText(employee.getTenure());

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchButton) {
            mViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
            EditText  text = mSearchProfileBinding.searchProfileContent.searchText;
            mViewModel.findEmployee(mSpinner.getSelectedItem().toString(), text.getText().toString());
        }
    }
}
