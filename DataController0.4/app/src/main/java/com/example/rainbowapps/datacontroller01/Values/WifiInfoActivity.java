package com.example.rainbowapps.datacontroller01.Values;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rainbowapps.datacontroller01.R;

public class WifiInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo w_info = wifiManager.getConnectionInfo();
        Log.i("Sample", "SSID:"+w_info.getSSID());
        Log.i("Sample", "BSSID:"+w_info.getBSSID());
        Log.i("Sample", "IP Address:"+w_info.getIpAddress());
        Log.i("Sample", "Mac Address:"+w_info.getMacAddress());
        Log.i("Sample", "Network ID:" + w_info.getNetworkId());
        Log.i("Sample", "Link Speed:" + w_info.getLinkSpeed());
    }
}
