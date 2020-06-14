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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.crm.R;
import com.crm.Utils.AddressUtils;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.ClientItemAdapter;
import com.crm.adapters.ClientItemDetailsAdapter;
import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.ClientPersonEntity;
import com.crm.databinding.FragmentFollowUpClientBinding;
import com.crm.model.Item;
import com.crm.model.PersonalDetails;
import com.crm.viewmodel.ClientPersonViewModel;
import com.crm.viewmodel.ClientViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class FollowUpClientFragment extends Fragment implements View.OnClickListener, DatePickerFragment.IUpdateDate {
    private final static String TAG = FollowUpClientFragment.class.getName();
//    private TextView clientGroupSpinner;
    private ClientViewModel mClientViewModel;
    private ClientPersonViewModel mClientPersonViewModel;
    private FragmentFollowUpClientBinding mFollowUpClientBinding;
    private List<ClientGroupEntity> mClientGroupList = new ArrayList<>();
    private Dialog dialog;
    private AddressUtils mAddressUtils;
    private ClientItemAdapter adapter;
    private List<ClientEntity> mSearchList = new ArrayList<>();
    private String timeStamp;
    private ClientItemDetailsAdapter mClientItemDetailsAdapter;
    private String mClientId;
    ArrayList<Item> itemList = new ArrayList<>();
    private boolean detailStatus;
    Dialog mDialog;
    private boolean flag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFollowUpClientBinding = FragmentFollowUpClientBinding.inflate(inflater, container, false);
        mClientViewModel = ViewModelProviders.of(this).get(ClientViewModel.class);
        mClientPersonViewModel = ViewModelProviders.of(this).get(ClientPersonViewModel.class);
      /*  mClientViewModel.getClientId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mClientId = s;
            }
        });*/
        mClientId = PersonalDetails.getInstance().getClientId();
        setUpViews();
        setToolbar();
        setClickListeners();
        return(mFollowUpClientBinding.getRoot());
    }


    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
    }

    private void setToolbar() {
        Toolbar toolbar = mFollowUpClientBinding.toolBar.mainToolbar;;
        TextView main_title = mFollowUpClientBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.client));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mFollowUpClientBinding.getRoot()).navigate(R.id.action_followUpClientFragment_to_dashboardFragment);
            }
        });
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }


    private void setUpViews() {
        mDialog = createDialog();
        getClientPrevDetails();
        ClientPersonViewModel clientPersonViewModel = ViewModelProviders.of(this).get(ClientPersonViewModel.class);
        clientPersonViewModel.findClientWithId(String.valueOf(mClientId));
        clientPersonViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientPersonEntity>>() {
            @Override
            public void onChanged(List<ClientPersonEntity> clientPersonEntities) {
                Log.d("goel", "size is : " + clientPersonEntities.size());
            }
        });
//        getAddClientObserver();
//        initializeAddressData();
    }

    private void initializeAddressData() {
        mAddressUtils = AddressUtils.getInstance();
        mAddressUtils.setContext(getContext());
        mAddressUtils.initialize();
    }

   private void getClientPrevDetails() {
            mClientViewModel.findClientWithClientId(mClientId);
            mClientViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
                @Override
                public void onChanged(List<ClientEntity> clientEntities) {
                    mClientItemDetailsAdapter.setList(formItemList(clientEntities.get(0)));
                    detailStatus = false;
                    addClientPersonDetails();

//                mClientItemDetailsAdapter.notifyDataSetChanged();
                }
            });
        }

   private void addClientPersonDetails() {
       mClientPersonViewModel.findClientWithId(String.valueOf(mClientId));
       mClientPersonViewModel.getSearchResults().observe(this, new Observer<List<ClientPersonEntity>>() {
           @Override
           public void onChanged(List<ClientPersonEntity> clientPersonEntities) {
               Log.d("goel", "size is : " + clientPersonEntities.size());

//               mClientItemDetailsAdapter.setList(formItemList(clientEntity));
               if(!detailStatus) {
                   detailStatus = true;
                   for(int i=0; i<clientPersonEntities.size(); i++) {
                       itemList.add(new Item(R.drawable.back , " Client Meeting Outcome :" + (i+1),""));
                       formPersonClientList(clientPersonEntities.get(i));
                       if(i == clientPersonEntities.size() -1) {
                           loadNextPersonDetail(clientPersonEntities.get(i));
                       }
                   }

                   mClientItemDetailsAdapter.notifyDataSetChanged();
               }


//                showDialog(clientEntity, clientPersonEntities);
           }
       });
   }

    /*private void getAddClientObserver() {
        mClientViewModel.listAllClients().observe(getActivity(), new Observer<List<ClientEntity>>() {
            @Override
            public void onChanged(List<ClientEntity> clientEntityList) {
                Log.d(TAG, "getAddClientObserver clientEntityList size:  " + clientEntityList.size());
                mClientViewModel.getSearchResults().observe(getActivity(), new Observer<List<ClientEntity>>() {
                            @Override
                            public void onChanged(List<ClientEntity> searchResults) {
                                Log.d(TAG, "timestamp: " + timeStamp);
                                if(searchResults.size() > 0 && searchResults.get(0).getTimeStamp().equals(timeStamp)) {
                                    Long clientId = searchResults.get(0).getClientId();

                                    mClientPersonViewModel.updateClientPersonId(timeStamp*//*searchResults.get(0).getClientId()*//*, String.valueOf(searchResults.get(0).getClientId()));
                                   Toast.makeText(getContext(), "client is added succesfully added \n Note your client id:  "
                                            + "c" + searchResults.get(0).getClientId() + "  clieint Id:  "  +clientId, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        mClientViewModel.findClientWithId("amit");
            }
        });
    }*/



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
                DialogFragment newFragment = new DatePickerFragment(FollowUpClientFragment.this, view);
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
                                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        };
        return onClickListener;
    }


    private void setClickListeners() {

        mFollowUpClientBinding.followUpClientContent.clientDetails.setOnClickListener(this);
        
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.dateOfContact.setOnClickListener(datePickerClick
                (mFollowUpClientBinding.followUpClientContent.clientPersonDetail.dateOfContact));

        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.surveyDate.setOnClickListener(datePickerClick
                (mFollowUpClientBinding.followUpClientContent.clientPersonDetail.surveyDate));
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingDate.setOnClickListener(datePickerClick
                (mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingDate));
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingTime.setOnClickListener(timePickerClick());

        mFollowUpClientBinding.followUpClientContent.saveButton.setOnClickListener(this);
//        mClientBinding.goButton.setOnClickListener(this);

//        clientTypeSelection();

        checkSameOrOther();

        checkClientInterest();

        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.allService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service1.setChecked(false);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service2.setChecked(false);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service3.setChecked(false);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service4.setChecked(false);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service5.setChecked(false);
            }
        });
    }

    private void checkClientInterest() {
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.statusSelection.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.interested:
                                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextPersonOuterLayout.
                                        setVisibility(View.VISIBLE);
                                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonLayout.
                                        setVisibility(View.VISIBLE);
                                break;
                            case R.id.not_interested:
                                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextPersonOuterLayout.
                                        setVisibility(View.GONE);
                                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonLayout.
                                        setVisibility(View.GONE);
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
    }



    private String checkServiceRequired() {
        StringBuilder serviceRequired = new StringBuilder();

        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.allService.isChecked()) {
            return  serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.allService.
                    getText().toString()).toString();
        }

        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service1.isChecked()) {
            serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service1.getText().toString()).append(",");
        }
        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service2.isChecked()) {
            serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service2.getText().toString()).append(",");
        }
        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service3.isChecked()) {
            serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service3.getText().toString()).append(",");
        }
        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service4.isChecked()) {
            serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service4.getText().toString()).append(",");
        }
        if(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service5.isChecked()) {
            serviceRequired.append(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.service5.getText().toString()).append(",");
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
       RadioButton radioButton = mFollowUpClientBinding.getRoot().findViewById(radioButtonId);
       return radioButton.getText().toString();
    }


    private void checkSameOrOther() {
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.not_required) {
                    mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonLayout.setVisibility(View.GONE);
                    return;
                }
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonLayout.setVisibility(View.VISIBLE);
                String name = "";
                String designation = "";
                String emailId = "";
                String phNo = "";
                String phCode = mFollowUpClientBinding.followUpClientContent.clientPersonDetail.countryPhoneCode.getText().toString();
                String officeNumber =  mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeNumber.getText().toString();
                String countryOfficeExtension = "";

                if (checkedId == R.id.same) {
                    name = mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientContactPersonName.getText().toString();
                    designation = mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientDesignation.getText().toString();
                    emailId = mFollowUpClientBinding.followUpClientContent.clientPersonDetail.emailId.getText().toString();
                    phNo =  mFollowUpClientBinding.followUpClientContent.clientPersonDetail.phoneNumber.getText().toString();
                    countryOfficeExtension =  mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeExtension.getText().toString();
                }
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextClientContactPerson.setText(name);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextClientDesignation.setText(designation);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextEmailId.setText(emailId);

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextPhoneNumber.setText(phNo);

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextCountryPhoneCode.setText(phCode);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextOfficeNumber.setText(officeNumber);
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextOfficeExtension.setText(countryOfficeExtension);
            }
        });
    }

    private String getNextPersonDetail() {
        if(statusTypeSelection(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonSelection).equals(getResources().getString(R.string.same))) {
            return getResources().getString(R.string.same);
        }
        else {
            return mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextClientContactPerson.getText().toString();
        }
    }

    private ArrayList<Item> formItemList(ClientEntity clientEntity) {
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

    private ArrayList<Item> formPersonClientList(ClientPersonEntity clientPersonEntity) {
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.contact_person), clientPersonEntity.getCpName()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.designation), clientPersonEntity.getCpDesignation()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.email_id), clientPersonEntity.getCpEmailId()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.mobile_number), clientPersonEntity.getCpMobNo()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.phone_number), clientPersonEntity.getCpPhNo()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.date_of_contact), clientPersonEntity.getDoc()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.status), clientPersonEntity.getCpStatus()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.comments), clientPersonEntity.getCpComments()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.service_required), clientPersonEntity.getServiceRequired()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.survey_done), clientPersonEntity.getSurveyDone()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.survey_date), clientPersonEntity.getSurveyDate()));
        itemList.add(new Item(R.drawable.ic_emp_id , getResources().getString(R.string.meeting_outcome), clientPersonEntity.getMeetingOutcome()));
        return itemList;
    }

    private ArrayList<Item> loadNextPersonDetail(ClientPersonEntity clientPersonEntity) {
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientContactPersonName.setText(clientPersonEntity.getNextClientContactPerson());
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientDesignation.setText(clientPersonEntity.getNextClientDesignation());
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.emailId.setText(clientPersonEntity.getNextClientEmailId());
        String[] mobNo = clientPersonEntity.getNextClientMobNo().split("\\s+");
        if (mobNo.length == 2) {
            mFollowUpClientBinding.followUpClientContent.clientPersonDetail.phoneNumber.setText(mobNo[1]);
        }

        String[] phNo = clientPersonEntity.getNextClientPhNo().split("\\s+");
        if (phNo.length == 3) {
            mFollowUpClientBinding.followUpClientContent.clientPersonDetail.countryPhoneCode.setText(phNo[0]);
            mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeNumber.setText(phNo[1]);
            mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeExtension.setText(phNo[2]);
        }
        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.dateOfContact.setText(clientPersonEntity.getNextMeetingDate());
        return itemList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_details:
                mDialog.show();
                break;

            case R.id.save_button:
                timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                saveClientPersonInformation();
                 break;
            default:
                break;

        }
    }

    private Dialog createDialog() {
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



    private void saveClientPersonInformation() {
//        getNextPersonDetail();
        ClientPersonEntity clientPersonEntity = new ClientPersonEntity(
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientContactPersonName.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.clientDesignation.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.emailId.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.countryMobileCode.getText().toString() + " " +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.phoneNumber.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.countryPhoneCode.getText().toString() + " " +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeNumber.getText().toString() + " " +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.officeExtension.getText().toString(),

                "doc",
                statusTypeSelection(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.statusSelection),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.comments.getText().toString(),

                statusTypeSelection(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextContactPersonSelection),


                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextClientContactPerson.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextClientDesignation.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextEmailId.getText().toString(),


                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextCountryMobileCode.getText().toString() + "," +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextPhoneNumber.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextCountryPhoneCode.getText().toString() + "," +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextOfficeNumber.getText().toString() + "," +
                        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextOfficeExtension.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingDate.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.nextMeetingTime.getText().toString(),
                checkServiceRequired(),
                statusTypeSelection(mFollowUpClientBinding.followUpClientContent.clientPersonDetail.surveyDoneSelection),
                mFollowUpClientBinding.followUpClientContent.clientPersonDetail.surveyDate.getText().toString(),

        mFollowUpClientBinding.followUpClientContent.clientPersonDetail.meetingOutcome.getText().toString(),
                timeStamp);

        mClientPersonViewModel.insertClientPerson(clientPersonEntity);

        mClientPersonViewModel.listAllPersonClients().observe(getActivity(), new Observer<List<ClientPersonEntity>>() {
            @Override
            public void onChanged(List<ClientPersonEntity> clientPersonEntities) {
                Log.d("goel", "timestamp: " + timeStamp);
                for(int i = 0; i< clientPersonEntities.size(); i++) {
                    Log.d("goel", "timestamp: value of i " + i);
                    if(clientPersonEntities.get(i).getTimeStamp().equals(timeStamp)) {
                        if(!flag) {
                            flag = true;
                            mClientPersonViewModel.updateClientPersonId(timeStamp, mClientId);
                            Log.d("goel", "exit ");
                            Navigation.findNavController(mFollowUpClientBinding.getRoot()).navigate(R.id.action_followUpClientFragment_to_dashboardFragment);
                        }
                    }
                }
            }
        });

    }

    /*private void setClientDetails() {
        ClientEntity clientEntity = new ClientEntity(
                mFollowUpClientBinding.followUpClientContent.clientGroupId.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientGroupName.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientCompanyName.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.clientAddressFirstLine.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.clientAddressSecondLine.getText().toString(),

                mFollowUpClientBinding.followUpClientContent.countryPickerSearch.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.statePickerSearch.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.cityPickerSearch.getText().toString(),
                mFollowUpClientBinding.followUpClientContent.pincode.getText().toString(),
                statusTypeSelection(mFollowUpClientBinding.followUpClientContent.referenceSelection),
                timeStamp,
                "amit");  //PersonalDetails.getInstance().getEmpId());
        mClientViewModel.insertClient(clientEntity);
    }*/

}
