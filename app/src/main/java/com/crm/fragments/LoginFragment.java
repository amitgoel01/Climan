package com.crm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChooseMfaContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.crm.R;
import com.crm.Utils.Constants;
import com.crm.databinding.FragmentLoginBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment implements View.OnClickListener {
    FragmentLoginBinding mFragmentLoginBinding;
    private NavigationView nDrawer;
    /*private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;*/
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;

    // Screen fields
    private EditText inUsername;
    private EditText inPassword;

    private RadioButton mFinanceRadioButton;
    private RadioButton mHrRadioButton;
    private RadioButton mSalesRadioButton;
    private RadioButton mManagementRadioButton;
    private Button mLoginButton;

    //Continuations
    private MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation;
    private ForgotPasswordContinuation mForgotPasswordContinuation;
    private NewPasswordContinuation newPasswordContinuation;
    private ChooseMfaContinuation mfaOptionsContinuation;

    // User Details
    private String mUsername;
    private String mPassword;
    private View mRootView;
    private final static String TAG = LoginFragment.class.getName();
    private SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        setUpViews();
        mRootView = mFragmentLoginBinding.getRoot();
        return(mRootView);
    }

    private void setUpViews() {
        mLoginButton = mFragmentLoginBinding.loginScreen.buttonLogIn;
        setRadioButtons();
        clearRadioChecked();
        setClickListener();
        mSharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        setDefaultRadioButton();
    }

    private void setDefaultRadioButton() {
        String jobTitle = mSharedPreferences.getString(Constants.JOB_TITLE, getResources().getString(R.string.hr));
        mEditor.putString(Constants.JOB_TITLE, jobTitle);
        clearRadioChecked();
        if(jobTitle.equals(getResources().getString(R.string.finance))) {
            mFinanceRadioButton.setChecked(true);
        }
        else if(jobTitle.equals(getResources().getString(R.string.hr))) {
            mHrRadioButton.setChecked(true);
        }
        else if(jobTitle.equals(getResources().getString(R.string.sales))) {
            mSalesRadioButton.setChecked(true);
        }
        else if(jobTitle.equals(getResources().getString(R.string.management))) {
            mManagementRadioButton.setChecked(true);
        }
    }

    private void setRadioButtons() {
        mFinanceRadioButton = mFragmentLoginBinding.loginScreen.radiobutton1;
        mHrRadioButton = mFragmentLoginBinding.loginScreen.radiobutton2;
        mSalesRadioButton = mFragmentLoginBinding.loginScreen.radiobutton3;
        mManagementRadioButton = mFragmentLoginBinding.loginScreen.radiobutton4;
    }

    private void clearRadioChecked() {
        mFinanceRadioButton.setChecked(false);
        mHrRadioButton.setChecked(false);
        mSalesRadioButton.setChecked(false);
        mManagementRadioButton.setChecked(false);

    }

    private void setClickListener() {
        mFinanceRadioButton.setOnClickListener(this);
        mHrRadioButton.setOnClickListener(this);
        mSalesRadioButton.setOnClickListener(this);
        mManagementRadioButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.radiobutton1 || v.getId() == R.id.radiobutton2 ||
                v.getId() == R.id.radiobutton3 || v.getId() == R.id.radiobutton4) {
            clearRadioChecked();
            RadioButton radioButton = (RadioButton) v;
            radioButton.setChecked(true);
            mEditor.putString(Constants.JOB_TITLE, radioButton.getText().toString());
        } else if (v.getId() == R.id.buttonLogIn) {
            EditText text = mFragmentLoginBinding.loginScreen.editTextUserId;
            mEditor.putString(Constants.EMPLOYEE_ID, text.getText().toString());
            Navigation.findNavController(mRootView).navigate(R.id.action_loginFragment_to_dashboardFragment);
        }
        mEditor.commit();
    }

}
