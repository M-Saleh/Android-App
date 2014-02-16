package com.navymail.core;

import com.navymail.modules.User;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;

public abstract class NavymailActivity extends Activity implements
		OnClickListener {

	private boolean default_user_qa2d = false ;
	
	protected Context mContext = this;
	protected LayoutInflater inflater;
	protected SharedPreferences prefs;
	public ApplicationController app = ApplicationController.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// common code goes here
		inflater = LayoutInflater.from(mContext);
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		customOnCreate();
	}

	public abstract void customOnCreate();
	// ===== Shared Prefs Functions
	public void setPassword(String password) {
		Editor editor = prefs.edit();
		if (isQa2ed())
			editor.putString(Constants.KA2D_PASSWORD, password);
		else
			editor.putString(Constants.ARKAN_PASSWORD, password);
		editor.commit();
	}

	public boolean checkPassword(String password) {
		String savedPassword ;
		if (isQa2ed())
			savedPassword = prefs.getString(Constants.KA2D_PASSWORD, "0000");
		else
			savedPassword = prefs.getString(Constants.ARKAN_PASSWORD, "0000");
		if (password.equalsIgnoreCase(savedPassword))
			return true;
		else
			return false;
	}
	
	public void setIsQa2ed(boolean isQa2ed) {
		Editor editor = prefs.edit();
		editor.putBoolean(Constants.IS_QA2ED, isQa2ed);
		editor.commit();
	}

	public boolean isQa2ed() {
		return prefs.getBoolean(Constants.IS_QA2ED, default_user_qa2d);
	}
	
	public void reInit(){
		app.currentUser = User.initializeUser(isQa2ed());
	}
	
}
