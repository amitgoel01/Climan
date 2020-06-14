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
import com.crm.Utils.Constants;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.ClientItemAdapter;
import com.crm.adapters.ClientItemDetailsAdapter;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.ClientPersonEntity;
import com.crm.database.entity.JobEntity;
import com.crm.databinding.FragmentClientBinding;
import com.crm.databinding.FragmentClientTypeBinding;
import com.crm.model.Item;
import com.crm.model.PersonalDetails;
import com.crm.viewmodel.ClientGroupViewModel;
import com.crm.viewmodel.ClientPersonViewModel;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientTypeFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate,
        ClientItemAdapter.IItemClickListener {
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
    private ClientItemDetailsAdapter mClientItemDetailsAdapter;
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
//                mClientViewModel.setClientId(mBinding.followUpClientView.clientId.getText().toString());  TBD
                PersonalDetails.getInstance().setClientId(mBinding.followUpClientView.clientId.getText().toString());
                mClientViewModel.findClientWithClientId(mBinding.followUpClientView.clientId.getText().toString());
                mClientViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
                    @Override
                    public void onChanged(List<ClientEntity> clientEntities) {
                        if(clientEntities.size() > 0) {
                            Navigation.findNavController(mBinding.getRoot()).navigate(R.id.action_clientTypeFragment_to_followUpClientFragment);
                        }
                        else {
                            Toast.makeText(getActivity(), "client Id not available", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
                mBinding.textView.setVisibility(View.VISIBLE);
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
        mBinding.textView.setVisibility(View.GONE);
    }

    private void updateMyClientsLists() {
        RecyclerView recyclerView = mBinding.clientListView;
        recyclerView.setVisibility(View.VISIBLE);

        if(layoutManager == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            mBinding.textView.setText(getResources().getString(R.string.no_client_available));
            /*
            if(mSearchList.size() > 0) {
                mBinding.textView.setVisibility(View.VISIBLE);
                mBinding
            }*/
            adapter = new ClientItemAdapter(mSearchList, ClientTypeFragment.this, getContext());
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
                    if(clientEntities.size() > 0) {
                        mBinding.textView.setVisibility(View.VISIBLE);
                        mBinding.textView.setText(getResources().getString(R.string.more_info));
                        ClientEntity clientEntity = new ClientEntity(Constants.CLIENT_GROUP, Constants.CLIENT_GROUP_NAME, Constants.CLIENT_COMPANY_NAME,
                                Constants.ADDRESS_1, Constants.ADDRESS_2, Constants.COUNTRY, Constants.STATE, Constants.CITY,
                                Constants.PIN_CODE, Constants.SOURCE, Constants.TIME_STAMP, PersonalDetails.getInstance().getEmpId());
                        clientEntities.add(0, clientEntity);

                        if (adapter != null) {
                            adapter.setList(clientEntities);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        mClientViewModel.findMyClientWithId("amit");
    }


    private Dialog showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_client_details, null);

        RecyclerView recyclerView = view.findViewById(R.id.client_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Item>list = new ArrayList<>();
        mClientItemDetailsAdapter = new ClientItemDetailsAdapter(list, getContext());
        recyclerView.setAdapter(mClientItemDetailsAdapter);


        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClickItem(int position, Long clientId) {
        ClientEntity clientEntity = mSearchList.get(position);
        showDialog().show();
        formItemList(clientEntity);
        ClientPersonViewModel clientPersonViewModel = ViewModelProviders.of(this).get(ClientPersonViewModel.class);
        clientPersonViewModel.findClientWithId(String.valueOf(clientId));
        clientPersonViewModel.getSearchResults().observe(this, new Observer<List<ClientPersonEntity>>() {
            @Override
            public void onChanged(List<ClientPersonEntity> clientPersonEntities) {
                Log.d("goel", "size is : " + clientPersonEntities.size());
                mClientItemDetailsAdapter.setList(formItemList(clientEntity));
                mClientItemDetailsAdapter.notifyDataSetChanged();
//                showDialog(clientEntity, clientPersonEntities);
            }
        });
    }

    private ArrayList<Item> formItemList(ClientEntity clientEntity) {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.group_id), clientEntity.getClientGroup()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.client_group_name), clientEntity.getClientGroupName()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.company_name), clientEntity.getClientCompanyName()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.client_address_first_line), clientEntity.getClientAddressFirstLine1()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.client_address_second_line), clientEntity.getClientAddressSecondLine1()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.country), clientEntity.getClientCountry()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.state), clientEntity.getClientState()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.city), clientEntity.getClientCity()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.pin_code), clientEntity.getClientPinCode()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.client_source), clientEntity.getClientSource()));
        return itemList;
    }
}
