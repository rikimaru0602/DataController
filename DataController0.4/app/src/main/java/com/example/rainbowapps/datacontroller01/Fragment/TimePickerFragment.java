package com.example.rainbowapps.datacontroller01.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.rainbowapps.datacontroller01.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    public interface OnTimeSetListener{
        void onTimeSet(int hour, int minute);
    }

    public static final String EXTRA_HOUR   = "new_hour";
    public static final String EXTRA_MINUTE = "new_minute";

    private static final String KEY_TITLE   = "title";
    private static final String KEY_HOUR   = "hour";
    private static final String KEY_MINUTE = "minute";

    private TimePicker mTimePicker;
    private OnTimeSetListener mListener;

    public static TimePickerFragment newInstance(final int title, final Calendar calendar) {
        return newInstance(title, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static TimePickerFragment newInstance(final int title, final int hour, final int minute) {
        final TimePickerFragment fragment = new TimePickerFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE, title);
        bundle.putInt(KEY_HOUR, hour);
        bundle.putInt(KEY_MINUTE, minute);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TimePickerFragment() {}

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            mListener  = null;
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState){
        mTimePicker = createTimePicker(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getInt(KEY_TITLE))
                .setView(mTimePicker)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void  onClick(DialogInterface dialog, int which){
                        final int hour = mTimePicker.getCurrentHour();
                        final int minute = mTimePicker.getCurrentMinute();

                        final  Fragment fragment = getTargetFragment();
                        if(fragment != null){
                            final Intent intent = new Intent();
                            intent.putExtra(EXTRA_HOUR, hour);
                            intent.putExtra(EXTRA_MINUTE, minute);
                            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }else{
                            mListener.onTimeSet(hour,minute);
                        }
                    }
                })

                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_HOUR, mTimePicker.getCurrentHour());
        outState.putInt(KEY_MINUTE, mTimePicker.getCurrentMinute());
    }

    private TimePicker createTimePicker(final Bundle savedInstanceState){
        final Activity activity = getActivity();
        final TimePicker timePicker = new TimePicker(activity);
        final Bundle bundle = getArguments();
        final int hour   = savedInstanceState ==  null ? bundle.getInt(KEY_HOUR)   : savedInstanceState.getInt(KEY_HOUR);
        final int minute = savedInstanceState ==  null ? bundle.getInt(KEY_MINUTE) : savedInstanceState.getInt(KEY_MINUTE);
        timePicker.setIs24HourView(DateFormat.is24HourFormat(activity));
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        return timePicker;
    }
}
