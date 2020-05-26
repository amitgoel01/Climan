package com.crm.fragments;

import android.app.Dialog;
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
import android.widget.Toast;

import com.crm.R;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.EmployeeEntity;
import com.crm.databinding.FragmentClientBinding;
import com.crm.viewmodel.ClientGroupViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class ClientFragment extends Fragment implements DatePickerFragment.IUpdateDate, View.OnClickListener {
    private final static String TAG = ClientFragment.class.getName();


    private EditText editTextName;
    private EditText editTextEmpID;
    private EditText editTextBU;
    private EditText editTextEmailID;
    private TextView clientGroupSpinner;
    private AutoCompleteTextView clientCompanyNameSpinner;
    private AutoCompleteTextView pinCodeSpinner;
    private EditText designation;
    private EditText jobTitle;
    private EditText reportingManager;
    private EditText mobileNumber;
    private  EditText doj;
    private EditText dob;
    private EditText tenure;
    private Button saveButton;

    private  String[] country;
    private  String[] names;
    private  String[] states;

    final Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    private EmployeeEntity employee;
    private DataAdapter employeeAdapter;


    private ClientGroupViewModel mViewModel;

    private EmployeeDatabase employeeDatabase;
    private FragmentClientBinding mClientBinding;
    private List<ClientGroupEntity> mClientGroupList = new ArrayList<>();
    private List<String> clientGroupList = new ArrayList<>();
    private List<String> clientCompanyNameList = new ArrayList<>();
    private List<String> pinCodeList = new ArrayList<>();
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mClientBinding = FragmentClientBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this).get(ClientGroupViewModel.class);
//        loadAllClientGroups();


        setUpViews();
        setToolbar();
        setClickListeners();

        return(mClientBinding.getRoot());
    }


    @Override
    public void setDate(View view, String date) {

    }

    private void setToolbar() {
        Toolbar toolbar = mClientBinding.toolBar.mainToolbar;;
        TextView main_title = mClientBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.client));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mClientBinding.getRoot()).navigate(R.id.action_clientFragment_to_dashboardFragment);
            }
        });
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }


    private void setUpViews() {
        getAllGroupClients();
//        Dialog dialog = showDialog();
//        dialog.show();
//        showDialog();
       /* mViewModel.getSearchResults().observe(getActivity(),
                new Observer<List<ClientGroupEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<ClientGroupEntity> clientGroupEntityList) {
                        if (clientGroupEntityList.size() > 0) { //5
                            mClientGroupList = clientGroupEntityList;

                            updateClientGroups();
//                           updateJobContent(jobList.get(0));
//                            dataAdapter.setList(jobList);
//                            dataAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_match_found), Toast.LENGTH_LONG).show();


//                           Log.d(TAG, "No Match found");
                        }
                    }
                });*/
        clientGroupSpinner = mClientBinding.clientContent.clientGroupId;
//        clientCompanyNameSpinner = mClientBinding.clientContent.clientCompanyName;
//        pinCodeSpinner = mClientBinding.clientContent.pinCode;

//        getCity();
       /* editTextName  = mClientBinding.clientContent.clientName;
        editTextBU = mClientBinding.clientContent.EditTextBU;
        editTextEmailID = mClientBinding.clientContent.emailId;
        designation = mClientBinding.clientContent.designation;
        jobTitle = mClientBinding.clientContent.jobTitle;
        reportingManager = mClientBinding.clientContent.reportingManager;
        mobileNumber = mClientBinding.clientContent.mobileNumber;


        tenure = mClientBinding.clientContent.tenure;

        countrySpinner = mClientBinding.clientContent.countryPickerSearch;
        citySpinner = mClientBinding.clientContent.cityPickerSearch;
        stateSpinner = mClientBinding.clientContent.statePickerSearch;
        doj = mClientBinding.clientContent.doj;
        dob = mClientBinding.clientContent.dob;
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH);
        mDay = myCalendar.get(Calendar.DAY_OF_MONTH);*/
        employeeDatabase = EmployeeDatabase.getInstance(getActivity());
//        saveButton = mClientBinding.clientContent.ButtonSaveButton;

    }

    private void getAllGroupClients() {
        mViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientGroupEntity>>() {
            @Override
            public void onChanged(List<ClientGroupEntity> clientGroupEntityList) {
                mClientGroupList = clientGroupEntityList;
                for(int i=0; i< mClientGroupList.size(); i++) {
                    clientGroupList.add(mClientGroupList.get(i).getClientGroupName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 , clientGroupList);


                /*clientGroupSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getActivity(),android.R.layout.simple_list_item_1 , clientGroupList);
                       *//* clientGroupSpinner.setThreshold(0);//will start working from first character
                        clientGroupSpinner.setAdapter(adapter);*//*
                    }
                });*/
            }
        });
    }

    private void updateClientCode() {

    }


    private void setClickListeners() {
        clientGroupSpinner.setOnClickListener(this);
        mClientBinding.goButton.setOnClickListener(this);
       /* clientGroupSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dialog = showDialog();
                dialog.show();
            }
        });*/
//        mClientBinding.clientContent.checkButton.setOnClickListener(this);
                mClientBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                        if (checkedRadioButton.getId() == R.id.new_client) {
                            EditText empId = mClientBinding.clientId;
                            empId.setVisibility(View.GONE);
                        } else {
                            EditText empIdText = mClientBinding.clientId;
                            empIdText.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.go_button) {
            mClientBinding.clientGroupLayout.setVisibility(View.GONE);
            mClientBinding.layout.setVisibility(View.VISIBLE);
            setClientView();
        }

        else if(v.getId() == R.id.client_group_id) {
            dialog = showDialog();
            dialog.show();
        }
    }

    private void setClientView() {

    }

    private void checkClientGroup() {

    }

    public Dialog showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        int count = mClientGroupList.size();
        View  view = inflater.inflate(R.layout.item_dialog, null);
        TextView message = view.findViewById(R.id.client_not_exist);
        TextView clientGroupId = view.findViewById(R.id.clientGroupId);
        Button actionButton = view.findViewById(R.id.create);
        builder.setView(view);

        AutoCompleteTextView clientGroupName = view.findViewById(R.id.client_group_id);
        clientGroupName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 , clientGroupList);
                clientGroupName.setThreshold(0);//will start working from first character

                clientGroupName.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            }
        });
        Button button = view.findViewById(R.id.checkButton);
        mViewModel.getSearchResults().observe(getActivity(),
                new Observer<List<ClientGroupEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<ClientGroupEntity> clientGroupEntityList) {
                        if (clientGroupEntityList.size() > 0) { //5
                            message.setVisibility(View.VISIBLE);
                            actionButton.setVisibility(View.VISIBLE);
                            message.setText(getResources().getString(R.string.client_code_available));
                            clientGroupId.setVisibility(View.VISIBLE);
                            StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.client_group_id));
                            clientGroupId.setText(stringBuilder.append(clientGroupEntityList.get(0).getClientGroupId()));
                            actionButton.setText(getResources().getText(R.string.back));
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    mClientBinding.clientContent.clientGroupId.setText(getResources().getString(R.string.client_group_id)+ "g"+Long.toString(clientGroupEntityList.get(0).getClientGroupId()));
                                    mClientBinding.clientContent.clientGroupName.setText(clientGroupEntityList.get(clientGroupEntityList.size() - 1).getClientGroupName());
                                    mClientBinding.clientContent.container.setVisibility(View.VISIBLE);
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_match_found), Toast.LENGTH_LONG).show();
                            message.setVisibility(View.VISIBLE);
                            actionButton.setVisibility(View.VISIBLE);
                            message.setText(getResources().getString(R.string.client_not_exist));
                            actionButton.setText(getResources().getText(R.string.generate));
                            clientGroupId.setVisibility(View.GONE);

                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    ClientGroupEntity clientGroupEntity = new ClientGroupEntity(clientGroupName.getText().toString());
                                   mViewModel.getInsertResult().observe(getActivity(), new Observer<Long>() {
                                       @Override
                                       public void onChanged(Long aLong) {

                                           mViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientGroupEntity>>() {
                                               @Override
                                               public void onChanged(List<ClientGroupEntity> clientGroupEntityList) {
                                                   Log.d(TAG, "Size : " + clientGroupEntityList.size() + "  count:  " + count);
                                                   if(clientGroupEntityList.size() > count) {
                                                       message.setText(getResources().getString(R.string.client_code_generated));
                                                       clientGroupId.setVisibility(View.VISIBLE);
                                                       StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.client_group_id));
                                                       clientGroupId.setText(stringBuilder.append(clientGroupEntityList.get(clientGroupEntityList.size() - 1).getClientGroupId()));
                                                       actionButton.setText(getResources().getText(R.string.back));
                                                       actionButton.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               dialog.dismiss();
                                                               mClientBinding.clientContent.clientGroupId.setText(getResources().getString(R.string.client_group_id)+"g"+Long.toString(clientGroupEntityList.get(clientGroupEntityList.size() - 1).getClientGroupId()));
                                                               mClientBinding.clientContent.clientGroupName.setText(clientGroupEntityList.get(clientGroupEntityList.size() - 1).getClientGroupName());
                                                               mClientBinding.clientContent.container.setVisibility(View.VISIBLE);
                                                           }
                                                       });
                                                   }

                                               }
                                           });
                                      }
                                   });
                                    mViewModel.insertClientGroup(clientGroupEntity);

                                }
                            });
//                            mViewModel.insertClientGroup();
//                           Log.d(TAG, "No Match found");
                        }
                    }
                });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clientGroupName.getText().toString()
                mViewModel.findClientGroupCode(clientGroupName.getText().toString());

            }
        });
        return builder.create();

    }
}
