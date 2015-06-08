package com.example.rainbowapps.datacontroller01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;

public class MenuActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        View importButton = findViewById(R.id.button_import);
        importButton.setOnClickListener(this);
        View exportButton = findViewById(R.id.button_export);
        exportButton.setOnClickListener(this);
        View playButton = findViewById(R.id.button_play);
        playButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        final int id = v.getId();

        if (id == R.id.button_import) {
            Intent intent = new Intent(this, ImpActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_export) {
            Intent intent = new Intent(this, ExpActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_play) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        }
    }


}
