package com.example.rainbowapps.datacontroller01.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

/**
 * 動画ファイル自動削除を受け取るレシーバーです。
 */
public class AutoDeleteReceiver extends BroadcastReceiver {

	public  static final String INTENT_ACTION_DELETE = "intent_action_delete";
	private String filename;

	@Override
	public void onReceive(final Context context, final Intent intent) {
		filename = intent.getStringExtra("path");
		final String action = intent.getAction();
//		 Context mContext = context;
//		if (INTENT_ACTION_DELETE.equals(action)) {
			execDelete();
//		}
	}

	public void execDelete() {
//		filename = "file1";
		File newdir = new File(filename);
		if(newdir.delete()) {
			Log.d("Check(Delete)", filename + "は自動削除されました。");
		}
	}
}

