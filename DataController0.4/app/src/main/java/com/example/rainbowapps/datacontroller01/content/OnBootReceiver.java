package com.example.rainbowapps.datacontroller01.content;

import android.content.Context;

/**
 * 端末起動時の処理。
 */
public class OnBootReceiver extends BaseOnBootReceiver {
    @Override
    protected void onDeviceBoot(Context context)
    {
        // サンプルのサービス常駐を開始
        new SamplePeriodicService().startResident(context);
    }
}
