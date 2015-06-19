package com.example.rainbowapps.datacontroller01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.example.rainbowapps.datacontroller01.Fragment.TimePickerFragment;
import com.example.rainbowapps.datacontroller01.Values.ContentsData;
import com.example.rainbowapps.datacontroller01.adapter.ImportListAdapter;
import com.example.rainbowapps.datacontroller01.utility.ImportScheduleUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ImplistActivity extends ActionBarActivity
        implements View.OnClickListener , TimePickerFragment.OnTimeSetListener , AdapterView.OnItemClickListener{

    final static public String APP_KEY = "adttr9ats1zypbz";
    final static public String APP_SECRET = "t4d5tu2j1qy5pnw";

    private static final String TAG_SETTING_SCHEDULE = "settings_schedule";
    private ArrayList<ContentsData> contentsDataList;
    private String path;
    private String rev;
    private ArrayList<DropboxAPI.Entry> list;
    private ArrayList<String> arrayPath = new ArrayList<>();
    private ArrayList<String> arrayRev = new ArrayList<>();
    private ListView mListView;
    private ImportListAdapter mAdapter;

    public String fnameDLMovieList = "DL_MovieList.txt";
    public DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implist);

        contentsDataList = new ArrayList<>();

        View addnewButton = findViewById(R.id.button_addnew);
        addnewButton.setOnClickListener(this);
        View confirmButton = findViewById(R.id.button_confirm_imp);
        confirmButton.setOnClickListener(this);
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);

        //動画リスト取得
        setMovieList();

        mListView = (ListView) findViewById(R.id.contents_list);
        mAdapter = new ImportListAdapter(this, createContentsDataList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(mListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_implist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notification_schedule) {
            TimePickerFragment timePicker
                    = TimePickerFragment.newInstance(R.string.schedule_setting_title, Calendar.getInstance());
            timePicker.show(getFragmentManager(), TAG_SETTING_SCHEDULE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox);
        chk.setChecked(!chk.isChecked());
        SparseBooleanArray checkedItemPositions = mListView.getCheckedItemPositions();
        Log.d("", String.format("position:%d checked:%b", position, !checkedItemPositions.get(position)));
    }

    private List<ContentsData> createContentsDataList() {
        contentsDataList = new ArrayList<>();
        for(int i =0;i<arrayPath.size();i++) {
            contentsDataList.add(new ContentsData(arrayPath.get(i)));
        }
        return contentsDataList;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.button_addnew) {
            Intent intent = new Intent(this, AddnewActivity.class);
            startActivity(intent);

        } else if (id == R.id.button_confirm_imp) {
            Intent intent = new Intent(this, ImplistActivity.class);
            modifyMovieList();
            startActivity(intent);
        } else if (id == R.id.button_top) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTimeSet(int hour, int minute) {
        ImportScheduleUtils.setRepeatAlarm(this, hour, minute);
        Toast.makeText(this, getString(R.string.schedule_message, hour, minute), Toast.LENGTH_LONG).show();
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
        StringBuilder sb = new StringBuilder();
        ArrayList<Boolean> bl = new ArrayList<>();
       //選択ステータスの表示
        for (int position = 0; position < arrayPath.size(); position++) {
            Log.d("",String.format(" position: %d   checked: %b", position, !checkedItemPositions.get(position)));
            bl.add(!checkedItemPositions.get(position));
        }
        //falseファイル削除
        Iterator<String> is = arrayPath.iterator();
        Iterator<Boolean> ib = bl.iterator();
        int rmCount=0;
        while (is.hasNext()){
            String vals  = is.next();
            boolean valb = ib.next();
            if(valb == false) {
                is.remove();
                ib.remove();
                rmCount++;
            }
        }
        // ファイルにリスト書き込み
        try {
            System.out.println("Test: " + deleteFile(fnameDLMovieList));
            FileOutputStream output = openFileOutput(fnameDLMovieList, MODE_PRIVATE);
            // 書き込み
            String str;
            for(int i=0; i < arrayPath.size() ; i++) {
                str = arrayPath.get(i) + "\n";
                output.write(str.getBytes());
            }
            // ストリームを閉じる
            output.close();
            Toast.makeText(this, "ダウンロードリストを更新しました("+rmCount+"件を削除)", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void setMovieList(){

        //リストをファイルから取得し、アレイに格納
        BufferedReader br = null;
        String line = null;
        try {
            FileInputStream file = openFileInput(fnameDLMovieList);
            br = new BufferedReader(new InputStreamReader(file));
            int i=0;
            while ((line = br.readLine()) != null) {
                arrayPath.add(line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //リストの中身がない場合、その場でDropBoxから取得しアレイに格納
        if(arrayPath.size() == 0){
//            if(true){
            //接続認証
            Login();
            //ファイル一覧取得
            list = new ArrayList<>();
            int fileLimit = 100;
            try {
                list = getList("/Movies/", fileLimit, true);
            } catch (DropboxException e) {
                e.printStackTrace();
            }
            DropboxAPI.Entry child;
            //要素ごとに詰め直し
            for (int i = 0; i < list.size(); i++) {
                child = list.get(i);
                path = child.path;
                rev = child.rev;
                arrayPath.add(path);
                arrayRev.add(rev);
            }

            // ファイルにリスト書き込み
            try {
                System.out.println("Test: " + deleteFile(fnameDLMovieList));
                FileOutputStream output = openFileOutput(fnameDLMovieList, MODE_PRIVATE);
                // 書き込み
                String str;
                for(int i=0; i < arrayPath.size() ; i++) {
                    str = arrayPath.get(i) + "\n";
                    output.write(str.getBytes());
                }
                // ストリームを閉じる
                output.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Toast.makeText(this, "DropBoxからリストを取得", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "ファイルからリストを取得", Toast.LENGTH_SHORT).show();
        }
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
}