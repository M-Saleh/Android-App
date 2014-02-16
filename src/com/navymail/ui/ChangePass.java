package com.navymail.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.navymail.core.ApplicationController;
import com.navymail.core.NavymailActivity;
import com.navymail.shared.Shared;

public class ChangePass extends NavymailActivity
{
	
	TextView textView_jobName ;
	Button changePass ;
	LinearLayout numPad;
	TextView passField ;
	ImageView clear ;
	
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_change_pass);
		
		textView_jobName = (TextView) findViewById(R.id.textView_jobname);
		textView_jobName.setText(textView_jobName.getText() + ApplicationController.getInstance().currentUser.jobName);

		passField = (TextView) findViewById(R.id.password_field);
		changePass = (Button) findViewById(R.id.change_pass_button);
		numPad = (LinearLayout) findViewById(R.id.num_pad);
		clear = (ImageView) findViewById(R.id.clear_t2shera);

		setUpClickListerns();
	}
	
	private void setUpClickListerns ()
	{
		
//		set lickListerns to numbers buttons
		LinearLayout temp_layout ;
		Button temp_button ;
		for (int i = 0 ; i<numPad.getChildCount() ; i++)
		{
			temp_layout = (LinearLayout) numPad.getChildAt(i);
			for (int j = 0;j< temp_layout.getChildCount() ; j++)
			{
				temp_button = (Button) temp_layout.getChildAt(j);
				temp_button.setOnClickListener(this);
			}
		}
		
		changePass.setOnClickListener(this);
		clear.setOnClickListener(this);
		passField.setFocusable(false);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId())
		{
			case R.id.clear_t2shera:
				passField.setText("");
			break ;
		
			case R.id.change_pass_button :
				changePass();
			break ;
			
			default :
				Button btn = (Button) v ;
				passField.append(btn.getText().toString());
			break ;
		}
	}

	private void changePass ()
	{
		String password = passField.getText().toString() ;
		if (password != null && password.length() > 3)
		{
			setPassword(password);
			finish();
		}
		else
			Toast.makeText(this, "من فضلك أدخل 3 أرقم على اﻷقل", Toast.LENGTH_LONG).show();
	}
}