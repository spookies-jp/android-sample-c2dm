package jp.co.spookies.android.c2dm;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

public class Received extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.received);
		setVolumeControlStream(AudioManager.STREAM_ALARM);
	}

	public void onClick(View view) {
		stopService(new Intent(this, AlarmService.class));
	}
}
