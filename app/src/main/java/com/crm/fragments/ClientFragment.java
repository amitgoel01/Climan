package com.crm.fragments;

import android.os.AsyncTask;
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

import com.crm.R;
import com.crm.Utils.DatePickerFragment;
import com.crm.adapters.DataAdapter;
import com.crm.database.EmployeeDatabase;
import com.crm.database.entity.EmployeeEntity;
import com.crm.databinding.FragmentClientBinding;
import com.crm.model.Address;
import com.crm.viewmodel.EmployeeListViewModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class ClientFragment extends Fragment implements DatePickerFragment.IUpdateDate, View.OnClickListener {
    private final static String TAG = ClientFragment.class.getName();

    private RadioGroup mRadioGroup;
    private EditText mEmpId;

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

    private  String[] country;
    private  String[] names;
    private  String[] states;

    final Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    private EmployeeEntity employee;
    private DataAdapter employeeAdapter;


    private EmployeeListViewModel mViewModel;

    private EmployeeDatabase employeeDatabase;
    private FragmentClientBinding mClientBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mClientBinding = FragmentClientBinding.inflate(inflater, container, false);
        getCity();

        setUpViews();
        setToolbar();
        setClickListeners();

        return(mClientBinding.getRoot());
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
    }


    private void setUpViews() {
        mRadioGroup = mClientBinding.radioGroup;
        mViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        editTextName  = mClientBinding.clientContent.clientName;
        editTextEmpID = mClientBinding.clientContent.EditTextEmpID;
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
        mDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        employeeDatabase = EmployeeDatabase.getInstance(getActivity());
        saveButton = mClientBinding.clientContent.ButtonSaveButton;

    }

    private void setClickListeners() {
        mClientBinding.goButton.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if(checkedRadioButton.getId() == R.id.new_client) {
                    EditText empId = mClientBinding.clientId;
                    empId.setVisibility(View.GONE);
                }
                else {
                    EditText empIdText = mClientBinding.clientId;
                    empIdText.setVisibility(View.VISIBLE);
                }
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee = new EmployeeEntity(editTextName.getText().toString(), editTextEmpID.getText().toString(), editTextBU.getText().toString(), editTextEmailID.getText().toString(),
                        countrySpinner.getText().toString(), citySpinner.getText().toString(), stateSpinner.getText().toString(),
                        designation.getText().toString(), jobTitle.getText().toString(), reportingManager.getText().toString(), mobileNumber.getText().toString(),
                        doj.getText().toString(), dob.getText().toString(), tenure.getText().toString());

                mViewModel.insertEmployee(employee);
                setResult(employee, 1);
//                finish();
//                new InsertTask(AddClient.this, employee).execute();
            /*  String name = "a";
                employee = new Employee(name, name, name, name, name, name, name, name, name, name, name, name, name);
                        */
            }
        });

        doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(ClientFragment.this, doj);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(ClientFragment.this, dob);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        countrySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,country);
                countrySpinner.setThreshold(0);//will start working from first character
                countrySpinner.setAdapter(adapter);
            }
        });

        citySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,names);
                citySpinner.setThreshold(0);//will start working from first character

                citySpinner.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            }
        });

        stateSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),android.R.layout.simple_list_item_1 ,states);
                stateSpinner.setThreshold(0);//will start working from first character
                stateSpinner.setAdapter(adapter);
            }
        });
    }


    private void getCity() {
        InputStream inputStream = getResources().openRawResource(R.raw.cities);
        String response = readJsonFile(inputStream);
        Gson gson = new Gson();
        Address[] address = gson.fromJson(response, Address[].class);
        country = new String[1];
        states = new String[address.length];
        names = new String[address.length];

        for(int i = 0; i< address.length; i++) {

            names[i] = address[i].getName();
            states[i] = address[i].getState();
        }
        country[0] = "India";
    }



    private static String readJsonFile(InputStream inputStream) {
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.e(TAG, "Unhandled exception while using JSONResourceReader", e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception while using JSONResourceReader", e);
            }
        }
        String jsonString = writer.toString();
        System.out.println(jsonString);
        return jsonString;
    }
    private void setResult(EmployeeEntity employee, int flag) {
//        setResult(flag, new Intent().putExtra("employee", employee));
//        finish();
    }

    @Override
    public void setDate(View view, String date) {
        ((EditText)view).setText(date);
    }


    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<ClientFragment> activityReference;
        private EmployeeEntity employee;

        // only retain a weak reference to the activity
        InsertTask(ClientFragment context, EmployeeEntity employee) {
            activityReference = new WeakReference<>(context);
            this.employee = employee;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented Employee id

//            long j = activityReference.get().employeeDatabase.getEmployeeDao().insertEmployee(employee);
//            employee.setEmpNumber(j);
//            Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(employee, 1);
//                activityReference.get().finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.go_button) {
            mClientBinding.clientGroupLayout.setVisibility(View.GONE);
            mClientBinding.layout.setVisibility(View.VISIBLE);
        }
    }
}
