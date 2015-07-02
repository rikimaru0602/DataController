package com.example.rainbowapps.datacontroller01.content;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.rainbowapps.datacontroller01.utility.Account;

public class SamplePeriodicService extends BasePeriodicService {
    // 画面から常駐を解除したい場合のために，常駐インスタンスを保持
    public static BasePeriodicService activeService;
    private int connectStatus;
    private WifiInfo w_info;

    @Override
    protected long getIntervalMS() {
        return 1000 * 2;
    }

    @Override
    protected void execTask() {
        activeService = this;
        // ※もし毎回の処理が重い場合は，メインスレッドを妨害しないために
        // ここから下を別スレッドで実行する。
        // ログ出力（ここに定期実行したい処理を書く）
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        w_info = wifiManager.getConnectionInfo();
        String nowSSID = w_info.getSSID();
//        showNetworkInfo();

        int fragTrigger;
        if (checkSSID(nowSSID)) {
            if (connectStatus == 0) {
                Log.d("Progress Trigger", "指定Wi-fiに接続したため、自動的にダウンロードを開始しました。");
                connectStatus = 1;
                fragTrigger = 0; //0->Wi-fi検知, 1->ImpAcにてボタン押下
                Intent intent = new Intent(this, AutoDownloadService.class);
                intent.putExtra("trigger", fragTrigger);
                this.startService(intent);
            }
        }else if(!checkSSID(nowSSID)){
            if (connectStatus == 1) {
                Log.d("Progress Trigger", "指定Wi-fiの接続が切れたため、ダウンロードを終了しました。");
                connectStatus=0;
                Intent intent = new Intent(this, AutoDownloadService.class);
                this.stopService(intent);
            }
        }
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
        Log.d("Sample", AvailableSSID + " " + nowSSID + " " + status);
        //Toast.makeText(this, "SSID: "+ nowSSID + "(" + status + ")", Toast.LENGTH_SHORT).show();
        return bl;
    }

    private void showNetworkInfo(){
        Log.i("Sample", "----------------- Wifi-Status from here-------------------");
        Log.i("Sample", "SSID:" + w_info.getSSID());
        Log.i("Sample", "BSSID:" + w_info.getBSSID());
        Log.i("Sample", "IP Address:" + w_info.getIpAddress());
        Log.i("Sample", "Mac Address:" + w_info.getMacAddress());
        Log.i("Sample", "Network ID:" + w_info.getNetworkId());
        Log.i("Sample", "Link Speed:" + w_info.getLinkSpeed());
    }

}
