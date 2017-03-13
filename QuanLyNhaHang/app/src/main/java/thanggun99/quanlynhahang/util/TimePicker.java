package thanggun99.quanlynhahang.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Thanggun99 on 13/03/2017.
 */

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnFinishPickTimeListener onFinishPickTimeListener;

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

        onFinishPickTimeListener.onFinishPickTime(Utils.formatDate(year + "-" + (month + 1) + "-" + day
                + " " + hourOfDay + ":" + minute + ":" + "00"));

    }

    public void setOnFinishPickTimeListener(OnFinishPickTimeListener onFinishPickTimeListener) {
        this.onFinishPickTimeListener = onFinishPickTimeListener;
    }

    public interface OnFinishPickTimeListener {
        void onFinishPickTime(String date);
    }

}