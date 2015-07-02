package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rainbowapps.datacontroller01.content.SamplePeriodicService;

public class SetActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);
        View onServiceButton = findViewById(R.id.button_onService);
        onServiceButton.setOnClickListener(this);
        View offServiceButton = findViewById(R.id.button_offService);
        offServiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.button_top) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else if (id == R.id.button_onService) {
            new SamplePeriodicService().startResident(this);
            Toast.makeText(this, "バックグラウンドサービスを開始", Toast.LENGTH_LONG).show();

        } else if (id == R.id.button_offService) {
            SamplePeriodicService.stopResidentIfActive(this);
            Toast.makeText(this, "バックグラウンドサービスを停止", Toast.LENGTH_LONG).show();
        }
    }
}