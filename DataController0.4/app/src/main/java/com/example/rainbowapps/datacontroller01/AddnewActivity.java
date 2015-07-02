package com.example.rainbowapps.datacontroller01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.example.rainbowapps.datacontroller01.Values.ContentsData;
import com.example.rainbowapps.datacontroller01.adapter.ImportListAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddnewActivity extends ActionBarActivity implements View.OnClickListener , AdapterView.OnItemClickListener{

    final static public String APP_KEY = "adttr9ats1zypbz";
    final static public String APP_SECRET = "t4d5tu2j1qy5pnw";
    private ArrayList<ContentsData> contentsDataList;
    private ArrayList<String> arrayPath = new ArrayList<>();
    private ArrayList<String> arrayPathOther = new ArrayList<>();
    private ArrayList<String> arrayRevOther = new ArrayList<>();
    private ListView mListView;
    public String fnameDLMovieList = "DL_MovieList.txt";
    public String fnameDLMovieList_conf =" DL_MovieList_Confirm.txt";
    public DropboxAPI<AndroidAuthSession> mDBApi;
    private ArrayList<String> DBPathArray = new ArrayList<>();
    private String DBBasePath = "/Movies/";
    private ArrayList<ArrayList> DBAllArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        contentsDataList = new ArrayList<>();

        View confirmButton = findViewById(R.id.button_confirm_addnew);
        confirmButton.setOnClickListener(this);
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);
        View makeButton = findViewById(R.id.button_makeList);
        makeButton.setOnClickListener(this);
        View backButton = findViewById(R.id.button_back3);
        backButton.setOnClickListener(this);

        DBPathArray.add("GamePlay/");
        DBPathArray.add("GameTrailer/");
        DBPathArray.add("MusicVideo/");
        DBPathArray.add("GoPro/");

        setMovieList();

        ImportListAdapter mAdapter;
        mListView = (ListView) findViewById(R.id.contents_list);
        mAdapter = new ImportListAdapter(this, createContentsDataList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(mListView.CHOICE_MODE_MULTIPLE);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox);
        chk.setChecked(!chk.isChecked());
        SparseBooleanArray checkedItemPositions = mListView.getCheckedItemPositions();
        Log.d("", String.format("position:%d checked:%b", position, !checkedItemPositions.get(position)));
    }

    @Override
    public void onClick(View v){
        final int id = v.getId();

        if (id == R.id.button_confirm_addnew) {
            modifyMovieList();
            Intent intent = new Intent(this, AddnewActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_top) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_makeList) {
            //makeConfirmList();
            Intent intent = new Intent(this, ImpActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_back3) {
            Intent intent = new Intent(this, ImplistActivity.class);
            startActivity(intent);
        }
    }

    private List<ContentsData> createContentsDataList() {
        contentsDataList = new ArrayList<>();
        for(int i =0;i<arrayPathOther.size();i++) {
            contentsDataList.add(new ContentsData(arrayPathOther.get(i)));
        }
        return contentsDataList;
    }

    public void setMovieList() {
        //リストをファイルから取得し、アレイに格納
        BufferedReader br;
        String line;
        try {
            FileInputStream file = openFileInput(fnameDLMovieList);
            br = new BufferedReader(new InputStreamReader(file));
            arrayPath.clear();
            while ((line = br.readLine()) != null) arrayPath.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //接続認証
        Login();
        //DBからファイル一覧取得
        ArrayList<DropboxAPI.Entry> list = new ArrayList<>();
        ArrayList<DropboxAPI.Entry> allList = new ArrayList<>();
        int fileLimit = 100;
        try {
            int j=0;
            DBAllArray.clear();
            while (DBPathArray.size() > j) {
                list = getList(DBBasePath + DBPathArray.get(j), fileLimit, true);
                Log.d("PathTest",""+DBBasePath + DBPathArray.get(j));
                DBAllArray.add(list);
                j++;
            }
//            list = getList("/Movies/", fileLimit, true);
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        DropboxAPI.Entry child;
        //要素ごとに詰め直し
        for(int j =0 ; j< DBAllArray.size() ; j++) {
            ArrayList<DropboxAPI.Entry> tmpArray = new ArrayList<>();
            tmpArray = DBAllArray.get(j);
            for (int i = 0; i < list.size(); i++) {
                allList.add(tmpArray.get(i));
            }
        }
        for(int i=0; i<allList.size();i++){
            child = allList.get(i);
            String path = child.path;
            String rev = child.rev;
            arrayPathOther.add(path);
            arrayRevOther.add(rev);
            Log.d("PathTest", "" + path);
        }
        //DBからの取得リストと既存のMovieListとでpathを照らし合わせ、ないものを抽出
        Iterator<String> i = arrayPathOther.iterator();
        while(i.hasNext()){
            String pathOther = i.next();
            for(int k=0; k<arrayPath.size();k++){
                if(pathOther.equals(arrayPath.get(k))) {
                    i.remove();
                }
            }
        }
//        Toast.makeText(this, "DropBoxからリストを取得", Toast.LENGTH_SHORT).show();
    }

    public void Login() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        SharedPreferences prefs = getSharedPreferences("drop_box_auth", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        System.out.println("Login:auth:"+accessToken);
        if (accessToken == null) {
            mDBApi = new DropboxAPI<>(session);
            mDBApi.getSession().startOAuth2Authentication(this);

            Log.d("DB Login", "Token saved.");
        } else {
            session.setOAuth2AccessToken(accessToken);
            mDBApi = new DropboxAPI<>(session);
            mDBApi.getSession().setOAuth2AccessToken(accessToken);
            Log.d("DB Login:Implist", "Login Automatically.");
        }

    }

    public ArrayList<DropboxAPI.Entry> getList(String path, int fileLimit, boolean list) throws DropboxException {
        ArrayList<DropboxAPI.Entry> entryList = new ArrayList<>();
        DropboxAPI.Entry entry = mDBApi.metadata(path, fileLimit, null, list, null);
        System.out.println("##################: " + entry.path + " - " + entry.rev);
        if (!list) {
            System.out.println("リスト取得を行わない設定です。");
            return entryList;
        }
        for (final DropboxAPI.Entry child : entry.contents) {
            entryList.add(child);
            System.out.println(child.path + " - " + child.rev);
        }
        return entryList;
    }


    public void modifyMovieList() {
        SparseBooleanArray checkedItemPositions = mListView.getCheckedItemPositions();
        ArrayList<Boolean> bl = new ArrayList<>();
        //選択ステータスの表示
        for (int position = 0; position < arrayPathOther.size(); position++) {
            Log.d("",String.format(" position: %d   checked: %b", position, !checkedItemPositions.get(position)));
            bl.add(!checkedItemPositions.get(position));
        }
        //falseの項目をファイルから削除
        Iterator<String> is = arrayPathOther.iterator();
        Iterator<Boolean> ib = bl.iterator();
        int addCount=0;
        while (is.hasNext()){
            is.next();
            boolean valb = ib.next();
            if(!valb) {
                is.remove();
                ib.remove();
                addCount++;
            }
        }
        // ファイルに追加書き込み
        try {
            FileOutputStream output = openFileOutput(fnameDLMovieList, MODE_PRIVATE | MODE_APPEND);
            // 書き込み
            String str;
            for(int i=0; i < arrayPathOther.size() ; i++) {
                str = arrayPathOther.get(i) + "\n";
                output.write(str.getBytes());
            }
            // ストリームを閉じる
            output.close();
            Toast.makeText(this, "取得する動画リストを更新しました("+addCount+"件を追加)", Toast.LENGTH_LONG).show();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}