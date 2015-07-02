package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;


public class PlayStocksActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stocks);

//        TextView title = findViewById(R.id.text_title);
        TextView title = new TextView(this);
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);
        View backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        Intent intent = getIntent();
        String absPath = intent.getStringExtra("filepath");
        title.setText(absPath);

        File file =new File(absPath);
        Log.d("Play",file.getPath());

        VideoView videoView = (VideoView) findViewById(R.id.video);
        videoView.setMediaController(new MediaController(this));

        if (file.exists()) {
            videoView.setVideoPath(file.getPath());
            videoView.start();
        }else{
            Log.d("Play","cannot find file path.");
        }

        RatingBar rb = new RatingBar(this);
        // 星の数を７に設定
        rb.setNumStars(5);
        // レートの変更を可能にする
        rb.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.5に設定
        rb.setStepSize((float) 0.5);
        // レートの初期値を2.0に設定
        rb.setRating((float) 3.0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if (id == R.id.button_top) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_back) {
            Intent intent = new Intent(this, StocksActivity.class);
            startActivity(intent);
        }
    }
}
