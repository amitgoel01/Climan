package com.crm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.crm.R;
import com.crm.Utils.Constants;
import com.crm.adapters.DashboardViewAdapter;
import com.crm.databinding.FragmentDashboardBinding;
import com.crm.model.DataModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardFragment extends Fragment implements DashboardViewAdapter.ItemListener{

    RecyclerView recyclerView;
    ArrayList<DataModel> mArrayList;
    private final String TAG= DashboardFragment.class.getName();

    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    private ListView attributesList;
    private FragmentDashboardBinding mFragmentDashboardBinding;

    // Cognito user objects
    private CognitoUser user;
    private CognitoUserSession session;
    private CognitoUserDetails details;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        setUpViews();
        setToolbar();
        setDrawer();
        return(mFragmentDashboardBinding.getRoot());
    }

    private void setUpViews() {
        nDrawer = mFragmentDashboardBinding.navView;
        recyclerView = mFragmentDashboardBinding.contentDashboard.recyclerView;
        mArrayList = new ArrayList<>();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jobTitle = sharedPref.getString(Constants.JOB_TITLE, getResources().getString(R.string.hr));

        List<Integer> colorList = new ArrayList<>();

        colorList.addAll(Arrays.asList(R.color.sky_blue, R.color.green, R.color.blue, R.color.red,
                R.color.orange, R.color.maroon, R.color.light_sky_blue, R.color.light_yellow,
                R.color.dark_pink, R.color.silver, R.color.light_silver, R.color.sky_blue));

        DataModel addProfile = new DataModel(getResources().getString(R.string.add_profile), R.drawable.ic_add_profile, colorList.get(0),
                R.id.action_dashboardFragment_to_addProfileFragment);
        DataModel searchProfile = new DataModel(getResources().getString(R.string.search_profile), R.drawable.ic_searchprofile, colorList.get(1),
                R.id.action_dashboardFragment_to_searchProfileFragment);
        DataModel addJob = new DataModel(getResources().getString(R.string.add_job), R.drawable.ic_add_job_64px, colorList.get(2),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel searchJob = new DataModel(getResources().getString(R.string.search_job), R.drawable.ic_search_job_48px, colorList.get(3),
                R.id.action_dashboardFragment_to_searchJobFragment);
        DataModel announcements =  new DataModel(getResources().getString(R.string.announcements), R.drawable.ic_aannouncement_2, colorList.get(4),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel expenses = new DataModel(getResources().getString(R.string.expenses), R.drawable.ic_expenses_64px, colorList.get(5),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel targetOverview = new DataModel(getResources().getString(R.string.target_overview), R.drawable.ic_track_changes_64, colorList.get(6),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel client = new DataModel(getResources().getString(R.string.client), R.drawable.ic_client_64_px, colorList.get(7),
                R.id.action_dashboardFragment_to_clientFragment);
        DataModel myTask = new DataModel(getResources().getString(R.string.my_tasks), R.drawable.ic_my_task_64px, colorList.get(8),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel tracking = new DataModel(getResources().getString(R.string.tracking), R.drawable.ic_supervised_user_circle_24px, colorList.get(9),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel expensesOverview = new DataModel(getResources().getString(R.string.expenses_overview), R.drawable.ic_expanses_overview, colorList.get(10),
                R.id.action_dashboardFragment_to_addJobFragment);
        DataModel navigation = new DataModel(getResources().getString(R.string.navigation), R.drawable.ic_navigation_64px, colorList.get(11),
                R.id.action_dashboardFragment_to_addJobFragment);

        if(jobTitle.equals(getResources().getString(R.string.hr))) {
            mArrayList.addAll(Arrays.asList(addProfile, searchProfile, addJob, searchJob,
                    announcements, expenses, targetOverview, navigation));
        }
        else if(jobTitle.equals(getResources().getString(R.string.sales))) {
            mArrayList.addAll(Arrays.asList(client, myTask, searchJob, announcements, expenses, targetOverview, tracking, navigation));
        }

        else if(jobTitle.equals(getResources().getString(R.string.finance))) {
            mArrayList.addAll(Arrays.asList(expensesOverview, searchJob, announcements, expenses, targetOverview, tracking, navigation));
        }

        else if(jobTitle.equals(getResources().getString(R.string.management))) {
            mArrayList.addAll(Arrays.asList(searchJob, announcements, expenses, targetOverview, tracking, navigation));
        }

        DashboardViewAdapter adapter = new DashboardViewAdapter(getContext(), mArrayList, this);
        recyclerView.setAdapter(adapter);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

     /*   float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/150);*/


        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        Log.d("amit", "Width:  "  + w + " height:  " + h);
        int columns = 2;
        if(w >= 1900) {
            columns = 6;
        }
        else if(w > 1000 && w < 1900) {
            columns = 4;
        }

        GridLayoutManager manager = new GridLayoutManager(getContext(), columns, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);

    }

    private void setToolbar() {
        toolbar = mFragmentDashboardBinding.toolBar.mainToolbar;
        TextView main_title = mFragmentDashboardBinding.toolBar.mainToolbarTitle;
        main_title.setText(getResources().getString(R.string.dashboard));
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    private void setDrawer() {
        // Set navigation drawer for getContext() screen
        mDrawer = mFragmentDashboardBinding.userDrawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawer, toolbar, R.string.nav_drawer_open,
                R.string.nav_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.getDrawerArrowDrawable().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_ATOP);



        mDrawerToggle.syncState();
        closeDrawer();
        setNavDrawer();
    }

    private void closeDrawer() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
    }



    @Override
    public void onItemClick(DataModel item) {
        View rootView = mFragmentDashboardBinding.getRoot();
        Navigation.findNavController(rootView).navigate(item.getAction());
    }

    private void setNavDrawer() {
        View navigationHeader = nDrawer.getHeaderView(0);
        TextView navHeaderSubTitle = navigationHeader.findViewById(R.id.textViewNavUserSub);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String empId = sharedPref.getString(Constants.EMPLOYEE_ID, getResources().getString(R.string.emp_id));
        navHeaderSubTitle.setText(empId);
        //amit to uncomment
        addMenuItemInNavMenuDrawer();
    }

    private void addMenuItemInNavMenuDrawer() {
        final NavigationView navView = mFragmentDashboardBinding.navView;
        Menu menu = navView.getMenu();
        ArrayList<DataModel> drawerList = new ArrayList<>();
        for(int i=0; i< mArrayList.size(); i++) {
            menu.add(Menu.NONE, mArrayList.get(i).getAction(), i, mArrayList.get(i).getText()).
                    setIcon(mArrayList.get(i).getIcon());
        }
        addOtherItems(menu, mArrayList.size());

        navView.invalidate();
        nDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                View rootView = mFragmentDashboardBinding.getRoot();
                Navigation.findNavController(rootView).navigate(item.getItemId());
                closeDrawer();
                return true;
            }
        });
    }

    private void addOtherItems(Menu menu, int index) {
        // adding myProfile
        menu.add(Menu.NONE, R.id.action_dashboardFragment_to_addJobFragment, index,
                getResources().getString(R.string.my_profile)).setIcon(R.drawable.ic_my_profile);

        // adding faq's
        menu.add(Menu.NONE, R.id.action_dashboardFragment_to_addJobFragment, index,
                getResources().getString(R.string.faqs)).setIcon(R.drawable.ic_faqs);

        // adding logout
        menu.add(Menu.NONE, R.id.action_dashboardFragment_to_addJobFragment, index,
                getResources().getString(R.string.logout)).setIcon(R.drawable.ic_person_sign_out_24dp);

        // adding about
      /*  menu.add(Menu.NONE, R.id.action_dashboardFragment_to_addJobFragment, index,
                getResources().getString(R.string.about_this_release)).setIcon(R.drawable.ic_add_client);
*/
    }

    // Perform the action for the selected navigation item
/*    private void performAction(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        // Find which item was selected
        switch(item.getItemId()) {
            case R.id.nav_user_add_attribute:
                // Add a new attribute
                addAttribute();
                break;

            case R.id.nav_user_change_password:
                // Change password
                changePassword();
                break;
            case R.id.nav_user_verify_attribute:
                // Confirm new user
                // confirmUser();
                attributesVerification();
                break;
            case R.id.nav_user_settings:
                // Show user settings
                showSettings();
                break;
            case R.id.nav_user_sign_out:
                // Sign out from getContext() account
                signOut();
                break;
            case R.id.nav_user_trusted_devices:
                showTrustedDevices();
                break;
            case R.id.nav_user_about:
                // For the inquisitive
                Intent aboutAppActivity = new Intent(getContext(), AboutApp.class);
                startActivity(aboutAppActivity);
                break;

            case R.id.nav_item:
                // For the inquisitive
                Intent petActivity = new Intent(getContext(), AddLocation.class);
                startActivity(petActivity);
                break;
        }
    }*/
/*
    // Get user details from CIP service
    private void getDetails() {
        AppHelper.getPool().getUser(username).getDetailsInBackground(detailsHandler);
    }

    // Show user attributes from CIP service
    private void showAttributes() {
        final UserAttributesAdapter attributesAdapter = new UserAttributesAdapter(getApplicationContext());
        final ListView attributesListView;
        attributesListView = (ListView) findViewById(R.id.listViewUserAttributes);
        attributesListView.setAdapter(attributesAdapter);
        attributesList = attributesListView;

        attributesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView data = (TextView) view.findViewById(R.id.editTextUserDetailInput);
                String attributeType = data.getHint().toString();
                String attributeValue = data.getText().toString();
                showUserDetail(attributeType, attributeValue);
            }
        });
    }

    // Update attributes
    private void updateAttribute(String attributeType, String attributeValue) {

        if(attributeType == null || attributeType.length() < 1) {
            return;
        }
        CognitoUserAttributes updatedUserAttributes = new CognitoUserAttributes();
        updatedUserAttributes.addAttribute(attributeType, attributeValue);
        Toast.makeText(getApplicationContext(), attributeType + ": " + attributeValue, Toast.LENGTH_LONG);
        showWaitDialog("Updating...");
        AppHelper.getPool().getUser(AppHelper.getCurrUser()).updateAttributesInBackground(updatedUserAttributes, updateHandler);
    }

    // Show user MFA Settings
    private void showSettings() {
        Intent userSettingsActivity = new Intent(getContext(), SettingsActivity.class);
        startActivityForResult(userSettingsActivity, 20);
    }

    // Add a new attribute
    private void addAttribute() {
     *//*   Intent addAttrbutesActivity = new Intent(getContext(), AddAttributeActivity.class);
        startActivityForResult(addAttrbutesActivity, 22);*//*
        Intent salesUserActivity = new Intent(getContext(), DashboardFragment.class);
        startActivity(salesUserActivity);
    }

    // Delete attribute
    private void deleteAttribute(String attributeName) {
        showWaitDialog("Deleting...");
        List<String> attributesToDelete = new ArrayList<>();
        attributesToDelete.add(attributeName);
        AppHelper.getPool().getUser(AppHelper.getCurrUser()).deleteAttributesInBackground(attributesToDelete, deleteHandler);
    }

    // Change user password
    private void changePassword() {
        Intent changePssActivity = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(changePssActivity);
    }

    // Verify attributes
    private void attributesVerification() {
        Intent attrbutesActivity = new Intent(getContext(), VerifyActivity.class);
        startActivityForResult(attrbutesActivity, 21);
    }

    private void showTrustedDevices() {
        Intent trustedDevicesActivity = new Intent(getContext(), DeviceSettings.class);
        startActivity(trustedDevicesActivity);
    }

    // Sign out user
    private void signOut() {
        user.signOut();
        exit();
    }

    // Initialize getContext() activity
    private void init() {
        // Get the user name
        Bundle extras = getIntent().getExtras();
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
        getDetails();
    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            closeWaitDialog();
            // Store details in the AppHandler
            AppHelper.setUserDetails(cognitoUserDetails);
            showAttributes();
            // Trusted devices?
            handleTrustedDevice();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Could not fetch user details!", AppHelper.formatException(exception), true);
        }
    };

    private void handleTrustedDevice() {
        CognitoDevice newDevice = AppHelper.getNewDevice();
        if (newDevice != null) {
            AppHelper.newDevice(null);
            trustedDeviceDialog(newDevice);
        }
    }

    private void updateDeviceStatus(CognitoDevice device) {
        device.remembergetContext()DeviceInBackground(trustedDeviceHandler);
    }

    private void trustedDeviceDialog(final CognitoDevice newDevice) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remember getContext() device?");
        //final EditText input = new EditText(UserActivity.getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //input.setLayoutParams(lp);
        //input.requestFocus();
        //builder.setView(input);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //String newValue = input.getText().toString();
                    showWaitDialog("Remembering getContext() device...");
                    updateDeviceStatus(newDevice);
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    // Callback handlers

    UpdateAttributesHandler updateHandler = new UpdateAttributesHandler() {
        @Override
        public void onSuccess(List<CognitoUserCodeDeliveryDetails> attributesVerificationList) {
            // Update successful
            if(attributesVerificationList.size() > 0) {
                showDialogMessage("Updated", "The updated attributes has to be verified",  false);
            }
            getDetails();
        }

        @Override
        public void onFailure(Exception exception) {
            // Update failed
            closeWaitDialog();
            showDialogMessage("Update failed", AppHelper.formatException(exception), false);
        }
    };

    GenericHandler deleteHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            closeWaitDialog();
            // Attribute was deleted
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);

            // Fetch user details from the the service
            getDetails();
        }

        @Override
        public void onFailure(Exception e) {
            closeWaitDialog();
            // Attribute delete failed
            showDialogMessage("Delete failed", AppHelper.formatException(e), false);

            // Fetch user details from the service
            getDetails();
        }
    };

    GenericHandler trustedDeviceHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            // Close wait dialog
            closeWaitDialog();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Failed to update device status", AppHelper.formatException(exception), true);
        }
    };

    private void showUserDetail(final String attributeType, final String attributeValue) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(attributeType);
        final EditText input = new EditText(DashboardFragment.getContext());
        input.setText(attributeValue);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        input.requestFocus();
        builder.setView(input);

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String newValue = input.getText().toString();
                    if(!newValue.equals(attributeValue)) {
                        showWaitDialog("Updating...");
                        updateAttribute(AppHelper.getSignUpFieldsC2O().get(attributeType), newValue);
                    }
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    deleteAttribute(AppHelper.getSignUpFieldsC2O().get(attributeType));
                } catch (Exception e) {
                    // Log failure
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(getContext());
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit();
                    }
                } catch (Exception e) {
                    // Log failure
                    Log.e(TAG," -- Dialog dismiss failed");
                    if(exit) {
                        exit();
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

    private void exit () {
        Intent intent = new Intent();
        if(username == null)
            username = "";
        intent.putExtra("name",username);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Find which menu item was selected
        int menuItem = item.getItemId();

        // Do the task
        if(menuItem == R.id.user_update_attribute) {
            //updateAllAttributes();
            showWaitDialog("Updating...");
            getDetails();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
