package com.example.rainbowapps.datacontroller01.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class BaseOnBootReceiver extends BroadcastReceiver {
    /**
     * 端末起動時にサービスを自動開始させるためのクラス。
     */

    //ブロードキャスト インテント検知時
    @Override
    public void onReceive(final Context context, Intent intent) {
        // 端末起動時？
        if( Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            new Thread(new Runnable(){
                @Override
                public void run()
                {
                    onDeviceBoot(context);
                }
            }).start();
        }
    }

    /**
     * 端末起動時に呼ばれるメソッド。
     */
    protected abstract void onDeviceBoot(Context context);
}
