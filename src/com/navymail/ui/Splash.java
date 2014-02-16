// First Activity (Screen) in App

package com.navymail.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.navymail.core.ApplicationController;
import com.navymail.core.NavymailActivity;
import com.navymail.shared.Shared;

public class Splash extends NavymailActivity {

	final int delayMillis = 3000 ;
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_splash);
		reInit();
		TextView name = (TextView) findViewById(R.id.textView_name);
		name.setText(name.getText()+ApplicationController.getInstance().currentUser.jobName);
		
		Handler handler = new Handler();
//		wait for s ms then goes to login page
		handler.postDelayed(loginProcess, delayMillis);
	}

	Runnable loginProcess = new Runnable() {
		@Override
		public void run() {
//			Finish current (splash) activity
			finish();
			Intent loginIntent = new Intent(mContext, Login.class);
			startActivity(loginIntent);
		};
	};

	@Override
	public void onClick(View arg0) {
	}

	
}
