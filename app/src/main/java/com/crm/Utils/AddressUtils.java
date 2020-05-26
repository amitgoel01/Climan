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

public class AddressUtils {

    private static AddressUtils sInstance;
    private  String[] mCountryList;
    private  String[] mCityList;
    private  String[] mStateList;
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
        mCountryList = new String[1];
        mStateList = new String[cityArray.length];
        mCityList = new String[cityArray.length];

        for(int i=0; i<cityArray.length; i++) {
            mCityList[i] = cityArray[i].getName();
            mStateList[i] = cityArray[i].getState();
        }
        mCountryList[0] = "India";
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

    public String[] getCountryList() {
        return mCountryList;
    }

    public String[] getCityList() {
        return mCityList;
    }

    public String[] getStateList() {
        return mStateList;
    }
}
