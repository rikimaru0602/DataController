package com.example.rainbowapps.datacontroller01;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;

import java.security.KeyStore;
import java.util.List;


public class FileActionMenuActivity extends Activity {

//    private DropboxAPI<AndroidAuthSession> mDBApi;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_file_action_menu);
//    }
//
//    public void getFileInfo(){
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    KeyStore.Entry diEntry = mDBApi.metadata("/", 10, null, true, null);
//                    List<KeyStore.Entry> fileList = diEntry.contents;
//                    for (final KeyStore.Entry entry : fileList) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(FileActionMenuActivity.this, entry.path, Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                } catch (DropboxException e) {
//                    Log.e(FileActionMenuActivity.this.getPackageName(), e.toString());
//                }
//            }
//        }).start();
//    }
//
//    public void downloadFile(){
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    mDBApi.getFile("/test.txt", null, outputStream, null);
//                    mHandler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            String fileText = new String(outputStream.toByteArray());
//                            Toast.makeText(FileActionMenuActivity.this, fileText, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                } catch (DropboxException e) {
//                    Log.e(FileActionMenuActivity.this.getPackageName(), e.toString());
//                }
//            }
//        }).start();
//    }

}
