package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;


public class PlayActivity extends ActionBarActivity implements  View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        View newarrivalButton = findViewById(R.id.button_NewArrival);
        newarrivalButton.setOnClickListener(this);
        View stocksButton = findViewById(R.id.button_Stocks);
        stocksButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
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

        if (id == R.id.button_NewArrival) {
            Intent intent = new Intent(this, NewArrivalActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_Stocks) {
            Intent intent = new Intent(this, StocksActivity.class);
            startActivity(intent);
        }
    }
}
