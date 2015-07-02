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

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class NewArrivalActivity extends ActionBarActivity  implements View.OnClickListener, AdapterView.OnItemClickListener{

    private File[] files;
    private List<String> movieList = new ArrayList<String>();
    private ListView lv;
    private String path;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrival);

        path = Environment.getExternalStorageDirectory().getPath()+"/Movies/";
        files = new File(path).listFiles();
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);
        View stockButton = findViewById(R.id.button_Stocks);
        stockButton.setOnClickListener(this);
        View backButton = findViewById(R.id.button_back_newarr);
        backButton.setOnClickListener(this);

        if(files != null){
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile()){
                    movieList.add(files[i].getName());
                    index = i;
                }
            }
            lv = (ListView) findViewById(R.id.newarrival_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, movieList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        } else if (id == R.id.button_back_newarr) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        } else if (id == R.id.button_Stocks) {
            Intent intent = new Intent(this, StocksActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) parent;
        String item = (String) listView.getItemAtPosition(position);
        Log.d("ClickTest", position + " : " + id);
        Intent intent = new Intent(this, PlayNewArrActivity.class);
        intent.putExtra("filepath", path + files[position].getName());
        startActivity(intent);
    }
}
