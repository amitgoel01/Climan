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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crm.R;
import com.crm.Utils.AddressUtils;
import com.crm.Utils.DatePickerFragment;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.ClientEntity;
import com.crm.databinding.FragmentClientBinding;
import com.crm.model.PersonalDetails;
import com.crm.viewmodel.ClientGroupViewModel;
import com.crm.viewmodel.ClientViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

public class ClientFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate {
    private final static String TAG = ClientFragment.class.getName();
//    private TextView clientGroupSpinner;
    private ClientGroupViewModel mClientGroupViewModel;
    private ClientViewModel mClientViewModel;
    private FragmentClientBinding mClientBinding;
    private List<ClientGroupEntity> mClientGroupList = new ArrayList<>();
    private List<String> clientGroupList = new ArrayList<>();
    private List<String> pinCodeList = new ArrayList<>();
    private Dialog dialog;
    private AddressUtils mAddressUtils;
    private EmployeeDatabase employeeDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mClientBinding = FragmentClientBinding.inflate(inflater, container, false);
        mClientGroupViewModel = ViewModelProviders.of(this).get(ClientGroupViewModel.class);
        mClientViewModel = ViewModelProviders.of(this).get(ClientViewModel.class);
        setUpViews();
        setToolbar();
        setClickListeners();
        return(mClientBinding.getRoot());
    }


    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
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
        getAddClientObserver();
        initializeAddressData();
        employeeDatabase = EmployeeDatabase.getInstance(getActivity());
    }

    private void initializeAddressData() {
        mAddressUtils = AddressUtils.getInstance();
        mAddressUtils.setContext(getContext());
        mAddressUtils.initialize();
    }

    private void getAllGroupClients() {
        mClientGroupViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientGroupEntity>>() {
            @Override
            public void onChanged(List<ClientGroupEntity> clientGroupEntityList) {
                mClientGroupList = clientGroupEntityList;
                for(int i=0; i< mClientGroupList.size(); i++) {
                    clientGroupList.add(mClientGroupList.get(i).getClientGroupName());
                }
            }
        });
    }

    private void getAddClientObserver() {
        mClientViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientEntity>>() {
            @Override
            public void onChanged(List<ClientEntity> clientEntityList) {
                Log.d(TAG, "clientEntityList size:  " + clientEntityList.size());
                mClientViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
                            @Override
                            public void onChanged(List<ClientEntity> searchResults) {
                                Toast.makeText(getContext(), "client is added succesfully added \n Note your client id:  "
                                        + "c"+ searchResults.get(0).getClientId(), Toast.LENGTH_LONG).show();
                            }
                        });

                        mClientViewModel.findClientWithId("amit");
            }
        });
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

    private  View.OnClickListener datePickerClick(EditText view) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(ClientFragment.this, view);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        };
        return onClickListener;
    }

    private  View.OnClickListener timePickerClick() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                TimePickerDialog picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mClientBinding.clientContent.meetingTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        };
        return onClickListener;
    }


    private void setClickListeners() {
        mClientBinding.clientContent.clientGroupId.setOnClickListener(this);
        mClientBinding.clientContent.clientPersonDetail.countryPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail.countryPickerSearch, mAddressUtils.getCountryList()));
        mClientBinding.clientContent.clientPersonDetail.cityPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail.cityPickerSearch, mAddressUtils.getCityList()));
        mClientBinding.clientContent.clientPersonDetail.statePickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail.statePickerSearch, mAddressUtils.getStateList()));

        mClientBinding.clientContent.clientPersonDetail2.countryPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail2.countryPickerSearch,
                        mAddressUtils.getCountryList()));
        mClientBinding.clientContent.clientPersonDetail2.cityPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail2.cityPickerSearch,
                        mAddressUtils.getCityList()));
        mClientBinding.clientContent.clientPersonDetail2.statePickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.clientPersonDetail2.statePickerSearch,
                        mAddressUtils.getStateList()));

        mClientBinding.clientContent.surveyDate.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.surveyDate));
        mClientBinding.clientContent.dateOfContact.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.dateOfContact));
        mClientBinding.clientContent.meetingDate.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.meetingDate));
        mClientBinding.clientContent.meetingTime.setOnClickListener(timePickerClick());

        mClientBinding.clientContent.saveButton.setOnClickListener(this);
        mClientBinding.goButton.setOnClickListener(this);

        clientTypeSelection();
        clientContactPersonSelection();
        referenceTypeSelection();
//        statusTypeSelection();
    }

    private void clientTypeSelection() {
        mClientBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
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

    private String getReferenceEmpId() {
        if(statusTypeSelection(mClientBinding.clientContent.statusSelection).equals(getResources().getString(R.string.self))) {
            return getResources().getString(R.string.self);
        }
        else {
            return mClientBinding.clientContent.referenceEmpId.getText().toString();
        }
    }

    private String checkServiceRequired() {
        StringBuilder serviceRequired = new StringBuilder();
        if(mClientBinding.clientContent.service1.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.service1.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.service2.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.service2.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.service3.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.service3.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.service4.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.service4.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.service5.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.service5.getText().toString()).append(",");
        }
        if(serviceRequired.length() > 0) {
            return serviceRequired.toString().substring(0, serviceRequired.toString().length() - 1);
        }
        else {
            return serviceRequired.toString();
        }
    }

    private void referenceTypeSelection() {
        mClientBinding.clientContent.referenceSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                if (checkedRadioButton.getId() == R.id.reference) {
                    mClientBinding.clientContent.referenceEmpId.setVisibility(View.VISIBLE);
                } else {
                    mClientBinding.clientContent.referenceEmpId.setVisibility(View.GONE);
                }
            }
        });
    }

   private String statusTypeSelection(RadioGroup radioGroup) {
       int radioButtonId = radioGroup.getCheckedRadioButtonId();
       RadioButton radioButton = mClientBinding.getRoot().findViewById(radioButtonId);
       return radioButton.getText().toString();
    }


    private void clientContactPersonSelection() {
        mClientBinding.clientContent.nextContactPersonSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                if (checkedRadioButton.getId() == R.id.self) {
                    mClientBinding.clientContent.clientPersonDetail2.clientDetailLayout.setVisibility(View.GONE);
                } else {
                    mClientBinding.clientContent.clientPersonDetail2.clientDetailLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String getNextPersonDetail() {
        if(statusTypeSelection(mClientBinding.clientContent.nextContactPersonSelection).equals(getResources().getString(R.string.self))) {
            return getResources().getString(R.string.self);
        }
        else {
            return mClientBinding.clientContent.clientPersonDetail2.clientContactPerson.getText().toString();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_button:
                mClientBinding.clientGroupLayout.setVisibility(View.GONE);
                mClientBinding.layout.setVisibility(View.VISIBLE);
                break;
            case R.id.client_group_id:
                dialog = showDialog();
                dialog.show();
                break;
            case R.id.save_button:
                saveClientDetails();
                break;
            default:
                break;

        }
    }

    private void saveClientDetails() {
         ClientEntity clientEntity = new ClientEntity(mClientBinding.clientContent.clientGroupId.getText().toString(),
                 mClientBinding.clientContent.clientCompanyName.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.clientContactPerson.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.clientDesignation.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.emailId.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.countryMobileCode.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.phoneNumber.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.countryPhoneCode.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.officeNumber.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.officeExtension.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.clientAddressFirstLine.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.clientAddressSecondLine.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.countryPickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.statePickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.cityPickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail.pinCode.getText().toString(),
                 statusTypeSelection(mClientBinding.clientContent.referenceSelection),
                 getReferenceEmpId(),
                 mClientBinding.clientContent.dateOfContact.getText().toString(),
                 checkServiceRequired(),
                 statusTypeSelection(mClientBinding.clientContent.statusSelection),
                 mClientBinding.clientContent.comments.getText().toString(),
                 mClientBinding.clientContent.meetingDate.getText().toString(),
                 mClientBinding.clientContent.meetingTime.getText().toString(),
                 getNextPersonDetail(),
                 mClientBinding.clientContent.clientPersonDetail2.clientContactPerson.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.clientDesignation.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.emailId.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.countryMobileCode.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.phoneNumber.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.countryPhoneCode.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.officeNumber.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.officeExtension.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.clientAddressFirstLine.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.clientAddressSecondLine.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.countryPickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.statePickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.cityPickerSearch.getText().toString(),
                 mClientBinding.clientContent.clientPersonDetail2.pinCode.getText().toString(),
                 "hello",
                 mClientBinding.clientContent.meetingOutcome.getText().toString(),
                 statusTypeSelection(mClientBinding.clientContent.followUpMeetingSelection),
                 statusTypeSelection(mClientBinding.clientContent.surveyDoneSelection),
                 mClientBinding.clientContent.surveyDate.getText().toString(),
                "amit");
                /* PersonalDetails.getInstance().getEmpId());*/

         String name = "a";
//         ClientEntity c1 = new ClientEntity(name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, checkServiceRequired(), name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, name, "amit");
                 mClientViewModel.insertClient(clientEntity);



    }

    private Dialog showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        int count = mClientGroupList.size();
        View  view = inflater.inflate(R.layout.item_dialog, null);
        TextView message = view.findViewById(R.id.client_not_exist);
        TextView clientGroupId = view.findViewById(R.id.clientGroupId);
        Button actionButton = view.findViewById(R.id.create);
        builder.setView(view);
        getAllGroupClients();


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
        mClientGroupViewModel.getSearchResults().observe(getActivity(),
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

                                 /*   mViewModel.getInsertResult().observe(getActivity(), new Observer<Long>() {
                                       @Override
                                       public void onChanged(Long aLong) {
*/
                                           mClientGroupViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientGroupEntity>>() {
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
                                     /* }
                                   });*/
                                    mClientGroupViewModel.insertClientGroup(clientGroupEntity);
                                }
                            });
                        }
                    }
                });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clientGroupName.getText().toString()
                mClientGroupViewModel.findClientGroupCode(clientGroupName.getText().toString());

            }
        });
        return builder.create();

    }
}
