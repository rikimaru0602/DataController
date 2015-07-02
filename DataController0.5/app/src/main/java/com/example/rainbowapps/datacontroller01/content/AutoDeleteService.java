package com.example.rainbowapps.datacontroller01.content;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Environment;

import java.util.Calendar;

public class AutoDeleteService extends IntentService {

    private String path;
    private int INTERVAL_DELETE_MINUTE = 1;
    private int INTERVAL_DELETE_SECOND = 5;
    private int intervalDeleteMillis;

    private String filename;

    public AutoDeleteService() {
        super("AutoDeleteService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        filename = intent.getStringExtra("path");
//        intervalDeleteMillis = INTERVAL_DELETE_MINUTE * 60 * 1000;
        path = Environment.getExternalStorageDirectory().getPath()+"/Movies/";
        Intent intent1 = new Intent(getApplicationContext(), AutoDeleteReceiver.class); // 呼び出すインテントを作成
        intent1.putExtra("path",filename);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent1, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, INTERVAL_DELETE_SECOND); // 現時刻より10秒後を設定
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); // AlramManager取得
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); // AlramManagerにPendingIntentを登録
    }
}
