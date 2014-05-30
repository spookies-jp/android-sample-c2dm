package jp.co.spookies.android.c2dm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Main extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SharedPreferences pref = 
			PreferenceManager.getDefaultSharedPreferences(this);
		String emailKey = getString(R.string.pref_key_email);
		if (!pref.contains(emailKey)) {
			startActivity(new Intent(this, Config.class));
		}
	}
}
