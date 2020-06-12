package com.crm.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crm.R;
import com.crm.Utils.AddressUtils;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.ClientItemAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.ClientPersonEntity;
import com.crm.databinding.FragmentClientBinding;
import com.crm.databinding.FragmentClientTypeBinding;
import com.crm.viewmodel.ClientGroupViewModel;
import com.crm.viewmodel.ClientViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientTypeFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate {
    private final static String TAG = ClientTypeFragment.class.getName();
//    private TextView clientGroupSpinner;
    private ClientGroupViewModel mClientGroupViewModel;
    private ClientViewModel mClientViewModel;
    private FragmentClientTypeBinding mBinding;
    private List<ClientGroupEntity> mClientGroupList = new ArrayList<>();
    private List<String> clientGroupList = new ArrayList<>();
    private List<String> pinCodeList = new ArrayList<>();
    private Dialog dialog;
    private AddressUtils mAddressUtils;
    private EmployeeDatabase employeeDatabase;
    private ClientItemAdapter adapter;
    private List<ClientEntity> mSearchList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentClientTypeBinding.inflate(inflater, container, false);
        mClientGroupViewModel = ViewModelProviders.of(this).get(ClientGroupViewModel.class);
        mClientViewModel = ViewModelProviders.of(this).get(ClientViewModel.class);
        setUpViews();
        setToolbar();
        setClickListeners();
        return(mBinding.getRoot());
    }


    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
    }

    private void setToolbar() {
        Toolbar toolbar = mBinding.toolBar.mainToolbar;;
        TextView main_title = mBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.client));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mBinding.getRoot()).navigate(R.id.action_clientFragment_to_dashboardFragment);
            }
        });
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }


    private void setUpViews() {
        employeeDatabase = EmployeeDatabase.getInstance(getActivity());
    }

    private void setClickListeners() {

        mBinding.newClient.setOnClickListener(this);
        mBinding.followUpClient.setOnClickListener(this);
        mBinding.existingClient.setOnClickListener(this);
        mBinding.myClients.setOnClickListener(this);

        mBinding.followUpClientView.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBinding.existingClientView.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        resetRadioButtons();
        switch (v.getId()) {
            case R.id.new_client:
                ((RadioButton)v).setChecked(true);
                Navigation.findNavController(mBinding.getRoot()).navigate(R.id.action_clientTypeFragment_to_clientFragment);
                break;
            case R.id.follow_up_client:
                ((RadioButton)v).setChecked(true);
                mBinding.followUpClientView.textandbuttonLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.existing_client:
                ((RadioButton)v).setChecked(true);
                mBinding.existingClientView.textandbuttonLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.my_clients:
                ((RadioButton)v).setChecked(true);
                updateMyClientsLists();
                break;
            default:
                break;
        }
    }

    private void resetRadioButtons() {
        mBinding.newClient.setChecked(false);
        mBinding.followUpClient.setChecked(false);
        mBinding.existingClient.setChecked(false);
        mBinding.myClients.setChecked(false);
        mBinding.followUpClientView.textandbuttonLayout.setVisibility(View.GONE);
        mBinding.existingClientView.textandbuttonLayout.setVisibility(View.GONE);
    }

    private void updateMyClientsLists() {
        RecyclerView recyclerView = mBinding.clientListView;
        recyclerView.setVisibility(View.VISIBLE);

        if(layoutManager == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ClientItemAdapter(mSearchList, getContext());
            recyclerView.setAdapter(adapter);
        }
        getMyClients();

    }

    private void getMyClients() {
        mClientViewModel.getClientResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
            @Override
            public void onChanged(List<ClientEntity> clientEntities) {
                if(mBinding.clientListView.getVisibility() == View.VISIBLE) {
                    mSearchList = clientEntities;
                    if(adapter != null) {
                        adapter.setList(clientEntities);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mClientViewModel.findMyClientWithId("amit");
    }

}
