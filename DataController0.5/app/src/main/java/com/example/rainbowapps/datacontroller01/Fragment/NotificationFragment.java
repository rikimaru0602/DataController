package com.example.rainbowapps.datacontroller01.Fragment;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rainbowapps.datacontroller01.ImplistActivity;
import com.example.rainbowapps.datacontroller01.R;

/**
 * 通知テストのフラグメントです。
 */
public class NotificationFragment extends Fragment implements View.OnClickListener {

	/**
	 * 通知ボタンの処理を定義します。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notification, null);
		rootView.findViewById(R.id.button_notification).setOnClickListener(this);
		return rootView;
	}

	/**
	 * クリックした際に通信をして、通知メソッドを呼び出します。
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		// 天気予報の通知を表示
		if (id == R.id.button_notification) {
			execWeatherNotification(getActivity());
		}
	}

	private void execWeatherNotification(Context context) {
		// Notificationの設定
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		// ①アイコン
		builder.setSmallIcon(R.drawable.ic_launcher);
		// ①Tickerテキスト(ステータスバーに表示される文字列)
		builder.setTicker(context.getString(R.string.notification_ticker));
		// ①通知内容のタイトル
		builder.setContentTitle(context.getString(R.string.notification_contents_title));
		// ①通知内容のテキスト
		builder.setContentText(context.getString(R.string.notification_description));
		// ①タップすれば消える設定
		builder.setAutoCancel(true);

		// ②通知内容のテキスト
		// ②タップされた際に発行するIntent
		Intent intent = new Intent(getActivity(), ImplistActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		startActivity(intent);

		// ②音とバイブレーション
		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

		// NotificationManagerの生成
		final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
	}
}