package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class StocksActivity extends ActionBarActivity implements View.OnClickListener{

    private File[] files;
    private List<String> movieList = new ArrayList<String>();
    private ListView lv;
    private String path;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        path = Environment.getExternalStorageDirectory().getPath()+"/movies/";
        Log.d("PlayList",path);
        files = new File(path).listFiles();


        View playtestButton = findViewById(R.id.button_PlayTest);
        playtestButton.setOnClickListener(this);
        if(files != null){
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile() && files[i].getName().endsWith(".mp4")){
                    movieList.add(files[i].getName());
                    index = i;
                    Log.d("PlayList", files[i].getName());
                }
            }

            lv = (ListView) findViewById(R.id.list_stock);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, movieList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listView = (ListView) parent;
                    String item = (String) listView.getItemAtPosition(position);
                    showItem(item);
                    // インテントへのインスタンス生成
                    Intent intent = new Intent(StocksActivity.this, PlayStocksActivity.class);
                    intent.putExtra("filepath", path + files[index].getName());
                    startActivity(intent);
                }
            });
        }
    }
    public void showItem(String str){
        Toast.makeText(this, "ファイル名:" + str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stocks, menu);
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

        if (id == R.id.button_PlayTest) {
            Log.d("Play", "動画を再生します");
            Intent intent = new Intent(this, PlayStocksActivity.class);
            startActivity(intent);
        }
    }
}
