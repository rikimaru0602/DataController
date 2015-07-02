package com.example.rainbowapps.datacontroller01.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.rainbowapps.datacontroller01.content.ImportScheduleReceiver;

import java.util.Calendar;

/**
 *通知を行うためのユーティリティクラスです。
 */
public class ImportScheduleUtils {
	public static void setRepeatAlarm(Context context, int hour, int minutes) {
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// 設定されているアラームのキャンセル
		alarmManager.cancel(getPendingIntent(context));
		// PendingIntentの設定(第二引数はAPI的に現在未使用、フラグ未設定)
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getNearestTime(hour, minutes, 0), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
	}

	private static PendingIntent getPendingIntent(Context context) {
		final Intent intent = new Intent(context, ImportScheduleReceiver.class);
		intent.setAction(ImportScheduleReceiver.INTENT_ACTION_NOTIFICATION);
		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public static long getNearestTime(int hour, int minutes, int second) {
		final Calendar currentCalendar = Calendar.getInstance();
		final Calendar nearestCalendar = Calendar.getInstance();
		nearestCalendar.set(Calendar.HOUR_OF_DAY, hour);
		nearestCalendar.set(Calendar.MINUTE, minutes);
		nearestCalendar.set(Calendar.SECOND, second);
		nearestCalendar.set(Calendar.MILLISECOND, 0);

		// 設定時間が現在時刻より前または同じならば現在時刻まで進める
		if (nearestCalendar.before(currentCalendar) || nearestCalendar.equals(currentCalendar)) {
			nearestCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
			nearestCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
			nearestCalendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE));
			nearestCalendar.add(Calendar.DATE, 1);
		}

		return nearestCalendar.getTimeInMillis();
	}

}
