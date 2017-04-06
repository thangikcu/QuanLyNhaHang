package com.thanggun99.khachhang.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Thanggun99 on 06/04/2017.
 */
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnPickTimeListener onPickTimeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        onPickTimeListener.onPickTime(Utils.formatDate(year + "-" + (month + 1) + "-" + day + " " + hourOfDay + ":" + minute + ":" + "00"));

    }

    public void setOnPickTimeListener(OnPickTimeListener onPickTimeListener) {
        this.onPickTimeListener = onPickTimeListener;
    }

    public interface OnPickTimeListener {
        void onPickTime(String time);
    }

}