package com.example.rainbowapps.datacontroller01.content;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutoDownloadService extends IntentService {

    final static public String APP_KEY = "adttr9ats1zypbz";
    final static public String APP_SECRET = "t4d5tu2j1qy5pnw";
    private ArrayList<String> arrayPath = new ArrayList<>();
    private ArrayList<String> arrayPathRef = new ArrayList<>();
    private String filename;
    private int fragTrigger;
    private String destination;
    public DropboxAPI<AndroidAuthSession> mDBApi;
    public String fnameDLMovieList = "DL_MovieList.txt";
    private long last_l, full_l;
    private boolean bl;

    public AutoDownloadService() {
        super("AutoDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        fragTrigger = intent.getIntExtra("trigger",fragTrigger);
        Login();
        downloadMovieList();
//        return;
    }

    public void Login() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        SharedPreferences prefs = getSharedPreferences("drop_box_auth", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        System.out.println("Login:auth:" + accessToken);
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

    public void downloadMovieList() {
        //リストをファイルから取得し、アレイに格納
        BufferedReader br;
        String line;
        try {
            FileInputStream file = openFileInput(fnameDLMovieList);
            br = new BufferedReader(new InputStreamReader(file));
            while ((line = br.readLine()) != null) {
                arrayPath.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        destination = Environment.getExternalStorageDirectory().getPath();

        for (int i = 0; i < arrayPath.size(); i++) {
            last_l=0;
            full_l=0;
            try {
                filename = arrayPath.get(i);
                File file = new File(destination + filename);
                FileOutputStream outputStream = new FileOutputStream(file);
                DropboxAPI.DropboxFileInfo info = mDBApi.getFile(filename, null, outputStream, new ProgressListener() {
                    @Override
                    public void onProgress(long l, long l1) {
                        System.out.println("Progress: " + filename + " : " + l + "/" + l1);
                        full_l = l1;
                    }
                });
                last_l = info.getFileSize();
            } catch (DropboxException e2) {
                e2.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bl = checkComplete();
            refreshList();
            if(!checkComplete())break;
        }
    }

    public boolean checkComplete(){
        boolean complete=false;
        if(last_l == full_l){
            complete = true;
            Log.i("Progress", filename + "のダウンロードを正常に終了しました。");
        }else{
            Log.i("Progress", filename + "のダウンロードは正常に終了しませんでした。");
        }
        return complete;
    }

    public void refreshList(){
        Log.i("Progress refresh", " "+ bl);
        if (bl) {
            //リストをファイルから取得し、アレイに格納
            BufferedReader br;
            String line;
            try {
                FileInputStream file = openFileInput(fnameDLMovieList);
                br = new BufferedReader(new InputStreamReader(file));
                arrayPathRef.clear();
                br.readLine(); // 一行目読み飛ばし
                while ((line = br.readLine()) != null) {
                    arrayPathRef.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //一行目以外を再びwrite
            try {
//                System.out.println("Test: " + deleteFile(fnameDLMovieList));
                FileOutputStream output = openFileOutput(fnameDLMovieList, MODE_PRIVATE);
                // 書き込み
                String str;
                for(int i=0; i < arrayPathRef.size() ; i++) {
                    str = arrayPathRef.get(i) + "\n";
                    output.write(str.getBytes());
                }
                output.close();
                Log.i("Progress", filename + "を更新リストから削除しました。");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            Log.i("Progress", filename + ":ファイルパス");
            File file = new File(destination+filename);
            file.delete();
            Log.i("Progress", filename + "の未完全ファイルを削除しました。");
        }
    }
}