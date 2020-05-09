package com.crm.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private IUpdateDate updateDate;
    private View mView;

    public DatePickerFragment(IUpdateDate updateDate, View view) {
        this.updateDate = updateDate;
        mView = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year,
                          int monthOfYear, int dayOfMonth) {
        // Do something with the date chosen by the user
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        updateDate.setDate(mView, date);
    }

    public interface IUpdateDate {
        public void setDate(View view, String date);
    }
}