package com.example.rainbowapps.datacontroller01.content;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.rainbowapps.datacontroller01.utility.Account;

public class SamplePeriodicService extends BasePeriodicService {
    // 画面から常駐を解除したい場合のために，常駐インスタンスを保持
    public static BasePeriodicService activeService;

    @Override
    protected long getIntervalMS() {
        return 1000 * 10;
    }

    @Override
    protected void execTask() {
        activeService = this;
        // ※もし毎回の処理が重い場合は，メインスレッドを妨害しないために
        // ここから下を別スレッドで実行する。

        // ログ出力（ここに定期実行したい処理を書く）
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo w_info = wifiManager.getConnectionInfo();
        Log.d("Sample", "----------------- Wifi-Status from here-------------------");
        Log.i("Sample", "SSID:" + w_info.getSSID());
        Log.i("Sample", "BSSID:"+w_info.getBSSID());
        Log.i("Sample", "IP Address:"+w_info.getIpAddress());
        Log.i("Sample", "Mac Address:"+w_info.getMacAddress());
        Log.i("Sample", "Network ID:" + w_info.getNetworkId());
        Log.i("Sample", "Link Speed:" + w_info.getLinkSpeed());
        Log.d("Sample", "----------------- Wifi-Status till here-------------------");
        String nowSSID = w_info.getSSID();
        checkSSID(nowSSID);

        // 次回の実行について計画を立てる
        makeNextPlan();
    }

    @Override
    public void makeNextPlan()
    {
        this.scheduleNextTime();
    }

    /**
     * もし起動していたら，常駐を解除する
     */
    public static void stopResidentIfActive(Context context) {
        if( activeService != null )
        {
            activeService.stopResident(context);
        }
    }

    public boolean checkSSID(String nowSSID){
        boolean bl = false;
        String AvailableSSID = "\""+Account.loadSSID(this) + "\"";
        String status ="unavailable";
        if(nowSSID.equals(AvailableSSID)){
            status = "available";
            bl = true;
        }
        Log.d("Status", AvailableSSID + " " + nowSSID + " " + status);
        Toast.makeText(this, "SSID: "+ nowSSID + "(" + status + ")", Toast.LENGTH_SHORT).show();
        return bl;
    }

}
