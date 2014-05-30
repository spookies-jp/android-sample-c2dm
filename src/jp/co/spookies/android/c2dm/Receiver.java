package jp.co.spookies.android.c2dm;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Receiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION")) {
			// 端末登録
			String id = intent.getStringExtra("registration_id");
			if (intent.getStringExtra("error") != null) {
				// エラー時
				resetRegister(context);
			} else if (intent.getStringExtra("unregistered") != null) {
				// 登録解除処理
			} else if (id != null) {
				// 登録処理
				sendToServer(context, id);
			}
		} else if (intent.getAction().equals(
				"com.google.android.c2dm.intent.RECEIVE")) {
			// メッセージ受信
			onMessage(context);
		}
	}

	private void sendToServer(Context context, String registrationId) {
		try {
			// 登録IDとGoogleアカウントをみつけてわんサーバに送信
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(context);
			String email = pref.getString(
					context.getString(R.string.pref_key_email), "");
			String param = "?email=" + email + "&registration_id="
					+ registrationId;
			String base = context.getString(R.string.app_url)
					+ context.getString(R.string.register_path);
			URL url = new URL(base + param);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			int code = http.getResponseCode();
			if (code != 200) {
				resetRegister(context);
			}
		} catch (Exception e) {
			resetRegister(context);
		}
	}

	private void resetRegister(Context context) {
		SharedPreferences.Editor pref = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		pref.remove(context.getString(R.string.pref_key_email));
	}

	private void onMessage(Context context) {
		context.startService(new Intent(context, AlarmService.class));
	}
}
