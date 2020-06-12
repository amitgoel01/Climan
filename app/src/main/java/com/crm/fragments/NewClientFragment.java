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
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientPersonEntity;
import com.crm.databinding.FragmentClientBinding;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class NewClientFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate {
    private final static String TAG = NewClientFragment.class.getName();
//    private TextView clientGroupSpinner;
    private ClientGroupViewModel mClientGroupViewModel;
    private ClientViewModel mClientViewModel;
    private ClientPersonViewModel mClientPersonViewModel;
    private FragmentClientBinding mClientBinding;
    private List<ClientGroupEntity> mClientGroupList = new ArrayList<>();
    private List<String> clientGroupList = new ArrayList<>();
    private List<String> pinCodeList = new ArrayList<>();
    private Dialog dialog;
    private AddressUtils mAddressUtils;
    private EmployeeDatabase employeeDatabase;
    private ClientItemAdapter adapter;
    private List<ClientEntity> mSearchList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mClientBinding = FragmentClientBinding.inflate(inflater, container, false);
        mClientGroupViewModel = ViewModelProviders.of(this).get(ClientGroupViewModel.class);
        mClientViewModel = ViewModelProviders.of(this).get(ClientViewModel.class);
        mClientPersonViewModel = ViewModelProviders.of(this).get(ClientPersonViewModel.class);
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
                Log.d(TAG, "getAllGroupClients ClientGroupViewModel.listAllClients():  " + clientGroupEntityList.size());
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
                Log.d(TAG, "getAddClientObserver clientEntityList size:  " + clientEntityList.size());
                mClientViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
                            @Override
                            public void onChanged(List<ClientEntity> searchResults) {
                                if(searchResults.size() > 0) {
                                    Long clientId = searchResults.get(0).getClientId();

                                    mClientPersonViewModel.updateClientPersonId(1L, searchResults.get(0).getClientId());
                                   Toast.makeText(getContext(), "client is added succesfully added \n Note your client id:  "
                                            + "c" + searchResults.get(0).getClientId() + "  clieint Id:  "  +clientId, Toast.LENGTH_LONG).show();
                                }
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
                DialogFragment newFragment = new DatePickerFragment(NewClientFragment.this, view);
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
                                mClientBinding.clientContent.clientPersonDetail.nextMeetingTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        };
        return onClickListener;
    }


    private void setClickListeners() {
        mClientBinding.clientContent.clientGroupId.setOnClickListener(this);
        mClientBinding.clientContent.countryPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.countryPickerSearch, mAddressUtils.getCountryList()));
        mClientBinding.clientContent.cityPickerSearch.setOnFocusChangeListener(
                onFocusChangeListener(mClientBinding.clientContent.cityPickerSearch, mAddressUtils.getCityList()));
        mClientBinding.clientContent.statePickerSearch.setOnFocusChangeListener (
                onFocusChangeListener(mClientBinding.clientContent.statePickerSearch, mAddressUtils.getStateList()));

        mClientBinding.clientContent.clientPersonDetail.dateOfContact.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.clientPersonDetail.dateOfContact));

        mClientBinding.clientContent.clientPersonDetail.surveyDate.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.clientPersonDetail.surveyDate));
        mClientBinding.clientContent.clientPersonDetail.nextMeetingDate.setOnClickListener(datePickerClick
                (mClientBinding.clientContent.clientPersonDetail.nextMeetingDate));
        mClientBinding.clientContent.clientPersonDetail.nextMeetingTime.setOnClickListener(timePickerClick());

        mClientBinding.clientContent.saveButton.setOnClickListener(this);
//        mClientBinding.goButton.setOnClickListener(this);

//        clientTypeSelection();

        checkSameOrOther();

        checkClientInterest();

        mClientBinding.clientContent.clientPersonDetail.allService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClientBinding.clientContent.clientPersonDetail.service1.setChecked(false);
                mClientBinding.clientContent.clientPersonDetail.service2.setChecked(false);
                mClientBinding.clientContent.clientPersonDetail.service3.setChecked(false);
                mClientBinding.clientContent.clientPersonDetail.service4.setChecked(false);
                mClientBinding.clientContent.clientPersonDetail.service5.setChecked(false);
            }
        });
    }

    private void checkClientInterest() {
        mClientBinding.clientContent.clientPersonDetail.statusSelection.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.interested:
                                mClientBinding.clientContent.clientPersonDetail.nextPersonOuterLayout.
                                        setVisibility(View.VISIBLE);
                                mClientBinding.clientContent.clientPersonDetail.nextContactPersonLayout.
                                        setVisibility(View.VISIBLE);
                                break;
                            case R.id.not_interested:
                                mClientBinding.clientContent.clientPersonDetail.nextPersonOuterLayout.
                                        setVisibility(View.GONE);
                                mClientBinding.clientContent.clientPersonDetail.nextContactPersonLayout.
                                        setVisibility(View.GONE);
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
    }

/*    private void clientTypeSelection() {
        mClientBinding.clientContent.clientPersonDetail.nextContactPersonSelection.
                setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                checkSameOrOther();
               *//* switch (checkedRadioButton.getId()) {
                    case R.id.new_client:
                        mClientBinding.clientListView.setVisibility(View.GONE);
                        mClientBinding.clientId.setVisibility(View.GONE);
                        break;
                    case R.id.follow_up_client:
                        mClientBinding.clientId.setVisibility(View.VISIBLE);
                        break;
                    case R.id.existing_client:
                        mClientBinding.clientId.setVisibility(View.VISIBLE);
                        break;
                    case R.id.my_clients:
                        mClientBinding.clientId.setVisibility(View.GONE);
                        updateMyClientsLists();
                        break;
                    default:
                        break;
                }*//*
            }
        });
    }*/




    private String checkServiceRequired() {
        StringBuilder serviceRequired = new StringBuilder();

        if(mClientBinding.clientContent.clientPersonDetail.allService.isChecked()) {
            return  serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.allService.
                    getText().toString()).toString();
        }

        if(mClientBinding.clientContent.clientPersonDetail.service1.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.service1.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.clientPersonDetail.service2.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.service2.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.clientPersonDetail.service3.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.service3.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.clientPersonDetail.service4.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.service4.getText().toString()).append(",");
        }
        if(mClientBinding.clientContent.clientPersonDetail.service5.isChecked()) {
            serviceRequired.append(mClientBinding.clientContent.clientPersonDetail.service5.getText().toString()).append(",");
        }

        if(serviceRequired.length() > 0) {
            return serviceRequired.toString().substring(0, serviceRequired.toString().length() - 1);
        }
        else {
            return serviceRequired.toString();
        }
    }

   private String statusTypeSelection(RadioGroup radioGroup) {
       int radioButtonId = radioGroup.getCheckedRadioButtonId();
       RadioButton radioButton = mClientBinding.getRoot().findViewById(radioButtonId);
       return radioButton.getText().toString();
    }


    private void checkSameOrOther() {
        mClientBinding.clientContent.clientPersonDetail.nextContactPersonSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.not_required) {
                    mClientBinding.clientContent.clientPersonDetail.nextContactPersonLayout.setVisibility(View.GONE);
                    return;
                }
                mClientBinding.clientContent.clientPersonDetail.nextContactPersonLayout.setVisibility(View.VISIBLE);
                String name = "";
                String designation = "";
                String emailId = "";
                String phNo = "";
                String phCode = mClientBinding.clientContent.clientPersonDetail.countryPhoneCode.getText().toString();
                String officeNumber =  mClientBinding.clientContent.clientPersonDetail.officeNumber.getText().toString();
                String countryOfficeExtension = "";

                if (checkedId == R.id.same) {
                    name = mClientBinding.clientContent.clientPersonDetail.clientContactPersonName.getText().toString();
                    designation = mClientBinding.clientContent.clientPersonDetail.clientDesignation.getText().toString();
                    emailId = mClientBinding.clientContent.clientPersonDetail.emailId.getText().toString();
                    phNo =  mClientBinding.clientContent.clientPersonDetail.phoneNumber.getText().toString();
                    countryOfficeExtension =  mClientBinding.clientContent.clientPersonDetail.officeExtension.getText().toString();
                }
                mClientBinding.clientContent.clientPersonDetail.nextClientContactPerson.setText(name);
                mClientBinding.clientContent.clientPersonDetail.nextClientDesignation.setText(designation);
                mClientBinding.clientContent.clientPersonDetail.nextEmailId.setText(emailId);

                mClientBinding.clientContent.clientPersonDetail.nextPhoneNumber.setText(phNo);

                mClientBinding.clientContent.clientPersonDetail.nextCountryPhoneCode.setText(phCode);
                mClientBinding.clientContent.clientPersonDetail.nextOfficeNumber.setText(officeNumber);
                mClientBinding.clientContent.clientPersonDetail.nextOfficeExtension.setText(countryOfficeExtension);
            }
        });
    }

    private String getNextPersonDetail() {
        if(statusTypeSelection(mClientBinding.clientContent.clientPersonDetail.nextContactPersonSelection).equals(getResources().getString(R.string.same))) {
            return getResources().getString(R.string.same);
        }
        else {
            return mClientBinding.clientContent.clientPersonDetail.nextClientContactPerson.getText().toString();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_group_id:
                dialog = showDialog();
                dialog.show();
                break;
            case R.id.save_button:
                saveClientPersonInformation();
                break;
            default:
                break;

        }
    }




    private void saveClientPersonInformation() {
//        getNextPersonDetail();
        ClientPersonEntity clientPersonEntity = new ClientPersonEntity(
                "id",
                mClientBinding.clientContent.clientPersonDetail.clientContactPersonName.getText().toString(),
                mClientBinding.clientContent.clientPersonDetail.clientDesignation.getText().toString(),
                mClientBinding.clientContent.clientPersonDetail.emailId.getText().toString(),

                mClientBinding.clientContent.clientPersonDetail.countryMobileCode.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.phoneNumber.getText().toString(),

                mClientBinding.clientContent.clientPersonDetail.countryPhoneCode.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.officeNumber.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.officeExtension.getText().toString(),

                "doc",
                statusTypeSelection(mClientBinding.clientContent.clientPersonDetail.statusSelection),
                mClientBinding.clientContent.clientPersonDetail.comments.getText().toString(),

                statusTypeSelection(mClientBinding.clientContent.clientPersonDetail.nextContactPersonSelection),


                mClientBinding.clientContent.clientPersonDetail.nextClientContactPerson.getText().toString(),
                mClientBinding.clientContent.clientPersonDetail.nextClientDesignation.getText().toString(),
                mClientBinding.clientContent.clientPersonDetail.nextEmailId.getText().toString(),


                mClientBinding.clientContent.clientPersonDetail.nextCountryMobileCode.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.nextPhoneNumber.getText().toString(),

                mClientBinding.clientContent.clientPersonDetail.nextCountryPhoneCode.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.nextOfficeNumber.getText().toString() + "," +
                        mClientBinding.clientContent.clientPersonDetail.nextOfficeExtension.getText().toString(),

                mClientBinding.clientContent.clientPersonDetail.nextMeetingDate.getText().toString(),
                mClientBinding.clientContent.clientPersonDetail.nextMeetingTime.getText().toString(),
                checkServiceRequired(),
                statusTypeSelection(mClientBinding.clientContent.clientPersonDetail.surveyDoneSelection),
                mClientBinding.clientContent.clientPersonDetail.surveyDate.getText().toString(),

        mClientBinding.clientContent.clientPersonDetail.meetingOutcome.getText().toString());

        mClientPersonViewModel.insertClientPerson(clientPersonEntity);



        mClientPersonViewModel.listAllPersonClients().observe(getActivity(), new Observer<List<ClientPersonEntity>>() {
            @Override
            public void onChanged(List<ClientPersonEntity> clientPersonEntities) {
                Log.d(TAG, "line no 449 clientEntityList size:  " + clientPersonEntities.size());
                setClientDetails();
            }
        });
    }

    private void setClientDetails() {
        ClientEntity clientEntity = new ClientEntity(
                mClientBinding.clientContent.clientGroupId.getText().toString(),
                mClientBinding.clientContent.clientCompanyName.getText().toString(),

                mClientBinding.clientContent.clientAddressFirstLine.getText().toString(),
                mClientBinding.clientContent.clientAddressSecondLine.getText().toString(),

                mClientBinding.clientContent.countryPickerSearch.getText().toString(),
                mClientBinding.clientContent.cityPickerSearch.getText().toString(),
                mClientBinding.clientContent.statePickerSearch.getText().toString(),
                mClientBinding.clientContent.pincode.getText().toString(),
                statusTypeSelection(mClientBinding.clientContent.referenceSelection),

                "amit");  //PersonalDetails.getInstance().getEmpId());
        mClientViewModel.insertClient(clientEntity);
       /* mClientViewModel.listAllClients().observe(this, new Observer<List<ClientEntity>>() {
            @Override
            public void onChanged(List<ClientEntity> clientEntities) {
                mClientPersonViewModel.updateClientPersonId(1L, mClientId);
            }
        });*/
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
                        Log.d(TAG, "line no 509 clientEntityList size:  " + clientGroupEntityList.size());
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
                                                   Log.d(TAG, "line no 548 clientEntityList size:  " + clientGroupEntityList.size()  + "  count:  " + count) ;
//                                                   Log.d(TAG, "Size : " + clientGroupEntityList.size() + "  count:  " + count);
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
