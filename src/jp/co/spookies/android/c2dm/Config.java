package jp.co.spookies.android.c2dm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Config extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accounts);

		// 端末に登録されているGoogleアカウントをラジオボタンとして一覧表示
		RadioGroup accounts = (RadioGroup) findViewById(R.id.accounts);
		AccountManager manager = AccountManager.get(this);
		for (Account account : manager.getAccountsByType("com.google")) {
			RadioButton radio = new RadioButton(this);
			radio.setText(account.name);
			accounts.addView(radio);
		}

		// 一番上のGoogleアカウントをチェック済みにする
		if (accounts.getChildCount() > 0) {
			RadioButton first = (RadioButton) accounts.getChildAt(0);
			first.setChecked(true);
		}
	}

	public void onRegisterButtonClicked(View view) {
		RadioGroup accounts = (RadioGroup) findViewById(R.id.accounts);
		RadioButton account = (RadioButton) findViewById(accounts
				.getCheckedRadioButtonId());
		SharedPreferences.Editor pref = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
		pref.putString(getString(R.string.pref_key_email), account.getText()
				.toString());
		pref.commit();

		// C2DMサーバへの登録
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		intent.putExtra("sender", getString(R.string.sender_id));
		startService(intent);

		finish();
	}
}
