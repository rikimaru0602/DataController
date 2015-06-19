package com.example.rainbowapps.datacontroller01.content;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.example.rainbowapps.datacontroller01.R;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * アラームを受け取るレシーバーです。
 */
public class ImportScheduleReceiver extends BroadcastReceiver {

	/** ノティフィケーションのアクション */
	public  static final String INTENT_ACTION_NOTIFICATION = "intent_action_notification";
	public  static ArrayList<String> initMovieList;
	private static ArrayList<String> arrayPath;
	private static ArrayList<String> arrayRev;
	private static Context mContext;
	public  static DropboxAPI<AndroidAuthSession> mDBApi;
	private static ArrayList<DropboxAPI.Entry> list;
	final static public String APP_KEY = "adttr9ats1zypbz";
	final static public String APP_SECRET = "t4d5tu2j1qy5pnw";
	public static String fnameDLMovieList = "DL_MovieList.txt";

	@Override
	public void onReceive(final Context context, final Intent intent) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
		final String action = intent.getAction();

		mContext = context;
		// 通知アクションの場合
		if (INTENT_ACTION_NOTIFICATION.equals(action)) {
			//仮のDL動画リストを作成する
			refleshMovieList();
			//リスト作成後、自動通知を行う
			execNotification();
		}
	}

	private static void execNotification() {
		// Notificationの設定
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
		// ①アイコン
		builder.setSmallIcon(R.drawable.ic_launcher);
		// ①Tickerテキスト(ステータスバーに表示される文字列)
		builder.setTicker(mContext.getString(R.string.notification_ticker));
		// ①通知内容のタイトル
		builder.setContentTitle(mContext.getString(R.string.notification_contents_title));
		// ①通知内容のテキスト
		builder.setContentText(mContext.getString(R.string.notification_contents_title));
		// ①タップすれば消える設定
		builder.setAutoCancel(true);

		Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		int notif_num=1;
		intent.putExtra("notif_num",notif_num);
		builder.setContentIntent(contentIntent);

		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

		final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
	}

	private static void refleshMovieList(){
		initMovieList = new ArrayList<>();
		arrayPath = new ArrayList<>();
		arrayRev = new ArrayList<>();
		//接続認証
		Login();
		setMovieList();
	}

	public static void setMovieList(){
		//ファイル一覧取得
		String path;
		String rev;
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
			mContext.deleteFile(fnameDLMovieList);

			FileOutputStream output;
			output = mContext.openFileOutput(fnameDLMovieList, Context.MODE_PRIVATE);
			output = new FileOutputStream(fnameDLMovieList);
			// 書き込み
			String str;
			for(int i=0; i < arrayPath.size() ; i++) {
				str = arrayPath.get(i) + "\n";
				output.write(str.getBytes());
			}
			// ストリームを閉じる
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i=0 ; i<arrayPath.size();i++){
			Log.d("Movies", arrayPath.get(i));
		}
	}

	public static void Login() {
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys);
		SharedPreferences prefs = mContext.getSharedPreferences("drop_box_auth", Context.MODE_PRIVATE);
		String accessToken = prefs.getString("access_token", null);
		session.setOAuth2AccessToken(accessToken);
		mDBApi = new DropboxAPI<>(session);
		mDBApi.getSession().setOAuth2AccessToken(accessToken);
	}

	public static ArrayList<DropboxAPI.Entry> getList(String path, int fileLimit, boolean list) throws DropboxException {
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
}

