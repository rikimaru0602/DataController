package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rainbowapps.datacontroller01.Values.ContentsData;
import com.example.rainbowapps.datacontroller01.adapter.ImportListAdapter2;
import com.example.rainbowapps.datacontroller01.content.AutoDownloadService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImpActivity extends ActionBarActivity implements View.OnClickListener{

    private ArrayList<String> arrayPath = new ArrayList<>();
    public String fnameDLMovieList_conf ="DL_MovieList.txt";
//    public String fnameDLMovieList_conf ="DL_MovieList_Confirm.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imp);
        View implistButton = findViewById(R.id.button_implist);
        implistButton.setOnClickListener(this);
        View settingsButton = findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(this);
        View nowButton = findViewById(R.id.button_nowDL);
        nowButton.setOnClickListener(this);
        View backButton = findViewById(R.id.button_back_imp);
        backButton.setOnClickListener(this);

        setMovieList();
        ListView mListView;
        ImportListAdapter2 mAdapter;
        mListView = (ListView) findViewById(R.id.contents_list2);
        mAdapter = new ImportListAdapter2(this, createContentsDataList());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v){
        final int id = v.getId();

        if (id == R.id.button_implist) {
            Intent intent = new Intent(this, ImplistActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_settings) {
            Intent intent = new Intent(this, SetActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_back_imp) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_nowDL) {
            int fragTrigger =1;
            Intent intent = new Intent(this, AutoDownloadService.class);
            intent.putExtra("trigger", fragTrigger);

            Toast.makeText(this, "動画のダウンロードを開始しました。", Toast.LENGTH_LONG).show();
            this.startService(intent);
            Toast.makeText(this, "動画のダウンロードを終了しました。", Toast.LENGTH_LONG).show();
        }
    }

    private List<ContentsData> createContentsDataList() {
        ArrayList<ContentsData> contentsDataList;
        contentsDataList = new ArrayList<>();
        contentsDataList.clear();
        for(int i =0;i<arrayPath.size();i++) {
            contentsDataList.add(new ContentsData(arrayPath.get(i)));
        }
        return contentsDataList;
    }

    public void setMovieList(){
        //リストをファイルから取得し、アレイに格納
        BufferedReader br;
        String line;
        try {
            FileInputStream file = openFileInput(fnameDLMovieList_conf);
            br = new BufferedReader(new InputStreamReader(file));
            arrayPath.clear();
            int i =0;
            //TODO ファイルの有無で判断
//            if(br.readLine() == null){
//                Toast.makeText(this,
//                        "次の更新リストが作成されていません。,更新リストを編集からリストを作成しましょう。",
//                        Toast.LENGTH_LONG).show();
//            }else {
                Log.d("MovieList","-------------from here-------------");
                while ((line = br.readLine()) != null) {
                    arrayPath.add(line);
                    Log.d("MovieList", arrayPath.get(i) + " : "+ i);
                    i++;
                }
                Log.d("MovieList","-------------till here-------------");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}