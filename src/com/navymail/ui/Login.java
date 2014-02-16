/* Login Activity has 2 layouts 
 Layout0 : Enter password
 Layout1 : Welcome (Photo with name)

 Then goes to Home Activity (Activity with 2 tabs)

 */

package com.navymail.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.navymail.core.ApplicationController;
import com.navymail.core.NavymailActivity;

public class Login extends NavymailActivity {

	EditText passField;
	LinearLayout numPad;
	ImageView clear;
	Button loginButoButton;
	ProgressBar progressBar;
	LinearLayout verification_panel;
	LinearLayout layout0;
	LinearLayout layout1;
	TextView changePass;
	Handler handler = new Handler();
	ImageView navyLogo;

	// animation
	private Animation mFade;
	private Animation mSlideOutTop;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_login);
		navyLogo = (ImageView) findViewById(R.id.imageView1);
		passField = (EditText) findViewById(R.id.password_field);
		numPad = (LinearLayout) findViewById(R.id.num_pad);
		clear = (ImageView) findViewById(R.id.clear_t2shera);
		loginButoButton = (Button) findViewById(R.id.change_pass_button);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		verification_panel = (LinearLayout) findViewById(R.id.verification_panel);
		layout0 = (LinearLayout) findViewById(R.id.layout0);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		changePass = (TextView) findViewById(R.id.change_pas);
		
		setTextViews();
		setUpClickListerns();
		initAnimation();
	}

	private void setUpClickListerns() {
		for (int i = 0; i < 4; i++) {
			LinearLayout child = (LinearLayout) numPad.getChildAt(i);
			for (int j = 0; j < 3; j++) {
				Button btn = (Button) child.getChildAt(j);
				btn.setOnClickListener(this);
			}
		}

		clear.setOnClickListener(this);
		loginButoButton.setOnClickListener(this);
		changePass.setOnClickListener(this);
		passField.setFocusable(false);

		registerForContextMenu(navyLogo);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			setIsQa2ed(true);
			break;

		case R.id.item2:
			setIsQa2ed(false);
			break;
		default:
			break;
		}
		
		reInit();
		recreate();

		return super.onContextItemSelected(item);
	}

	private void setTextViews() {
		TextView textViewWord = (TextView) findViewById(R.id.textViewWord);
		textViewWord.setText(textViewWord.getText()
				+ ApplicationController.getInstance().currentUser.jobName);

		TextView textView_tmam = (TextView) findViewById(R.id.textView_tmam);
		textView_tmam.setText(textView_tmam.getText()
				+ ApplicationController.getInstance().currentUser.jobName);

		TextView textView_name_job = (TextView) findViewById(R.id.textView_name_job);
		textView_name_job.setText(textView_name_job.getText()
				+ ApplicationController.getInstance().currentUser.rank + " / "
				+ ApplicationController.getInstance().currentUser.name);

		ImageView photo = (ImageView) findViewById(R.id.photo);
		photo.setBackgroundResource(ApplicationController.getInstance().currentUser.photo);

	}

	private void initAnimation() {
		// animation
		mFade = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
		mSlideOutTop = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_top);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.clear_t2shera:
			passField.setText("");
			break;

		case R.id.change_pass_button:
			loginBehaviour();
			break;

		case R.id.change_pas:
			Intent changePassScreen = new Intent(mContext, ChangePass.class);
			startActivity(changePassScreen);
			break;

		default:
			Button btn = (Button) v;
			String num = btn.getText().toString();
			passField.append(num);
			break;
		}
	}

	private void loginBehaviour() {
		String password = passField.getEditableText().toString();
		boolean passValid = checkPassword(password);
		final Runnable r1 = new Runnable() {
			@Override
			public void run() {

				Intent homeScreen = new Intent(mContext, Home.class);
				finish();
				startActivity(homeScreen);
				overridePendingTransition(R.anim.slide_in_top,
						R.anim.slide_out_bottom);
			}
		};

		Runnable r = new Runnable() {
			@Override
			public void run() {
				int progress = progressBar.getProgress();
				if (progress < 99) {
					progressBar.setProgress(progress + 33);
					handler.postDelayed(this, 750);
				} else {
					progressBar.setProgress(100);

					layout0.setVisibility(View.GONE);
					layout0.startAnimation(mSlideOutTop);

					layout1.setVisibility(View.VISIBLE);
					layout1.startAnimation(mFade);
					handler.postDelayed(r1, 3000);
				}
			}
		};

		if (passValid) {
			changePass.setVisibility(View.GONE);
			verification_panel.setVisibility(LinearLayout.VISIBLE);
			handler.postDelayed(r, 750);
		} else {
			Toast.makeText(mContext, "كلمة المرور غير صحيحة", Toast.LENGTH_LONG)
					.show();
		}
	}
}
