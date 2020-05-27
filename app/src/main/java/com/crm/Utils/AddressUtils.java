package com.crm.Utils;

import android.content.Context;
import android.util.Log;

import com.crm.R;
import com.crm.model.Address;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddressUtils {

    private static AddressUtils sInstance;
    private List<String> mCountryList;
    private List<String> mCityList;
    private  List<String> mStateList;
    private Context mContext;
    private static final String TAG = AddressUtils.class.getName();
    private AddressUtils() {
    }

    public static AddressUtils getInstance() {
        if (sInstance == null) {
            synchronized (AddressUtils.class) {
                if (sInstance == null) {
                    sInstance = new AddressUtils();
                }
            }
        }
        return sInstance;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void initialize() {
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.cities);
        String response = readJsonFile(inputStream);
        Gson gson = new Gson();
        Address[] cityArray = gson.fromJson(response, Address[].class);
        mCountryList = new ArrayList<>(1);
//        String[] stateList = new String[cityArray.length];
        List<String> stateList = new ArrayList<>(cityArray.length);
        mCityList = new ArrayList<>(cityArray.length);

        for(int i=0; i<cityArray.length; i++) {
            mCityList.add(cityArray[i].getName());
            stateList.add(cityArray[i].getState());
        }
        mStateList = stateList.stream().distinct().collect(Collectors.toList());
        mCountryList.add("India");
    }

    private  String readJsonFile(InputStream inputStream) {
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

    public List<String> getCountryList() {
        return mCountryList;
    }

    public List<String> getCityList() {
        return mCityList;
    }

    public List<String> getStateList() {
        return mStateList;
    }
}
