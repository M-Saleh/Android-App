package com.navymail.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.navymail.core.Constants;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.navymail.core.ApplicationController;
import com.navymail.core.CustomView;
import com.navymail.core.NavymailActivity;
import com.navymail.shared.Shared;

public class EditTopic extends NavymailActivity {

	ToggleButton manualEdit;
	CheckBox suggestTasdek;
	CheckBox quickStudy ;
	CheckBox tansekAnndlazem ;
	Button showQa2d;
	Button save;
	Button t2shera;
	Button showHide;
	Button addUnits;
	Button removeAll;
	String topicType = "";
	String topicName = "" ;
	int topicID;
	public static RelativeLayout toolBox;
	
	static ApplicationController app = ApplicationController.getInstance() ;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_edit);

		quickStudy = (CheckBox) findViewById(R.id.btn7);
		tansekAnndlazem = (CheckBox) findViewById(R.id.btn8);
		showQa2d = (Button) findViewById(R.id.commander_3ard);
		save = (Button) findViewById(R.id.commander_save);
		suggestTasdek = (CheckBox) findViewById(R.id.suggest_tasdek);
		showHide = (Button) findViewById(R.id.show_hide);
		t2shera = (Button) findViewById(R.id.t2hera_button);
		t2shera.setText(t2shera.getText() + app.currentUser.jobName);
		addUnits = (Button) findViewById(R.id.add_units);
		removeAll = (Button) findViewById(R.id.remove_all);

		if (isQa2ed()) {
			suggestTasdek.setVisibility(CheckBox.GONE);
			showQa2d.setVisibility(Button.GONE);
			quickStudy.setVisibility(CheckBox.GONE);
			tansekAnndlazem.setVisibility(Button.GONE);
		} else
			save.setVisibility(Button.GONE);

		Shared.clear();
		Shared.screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		Shared.screenHeight = getWindowManager().getDefaultDisplay()
				.getHeight();

		Shared.mContext = mContext;
		Shared.imgPath = (String) getIntent().getExtras().get("path");
		topicName = (String) getIntent().getExtras().get("topicName");
		topicType = (String) getIntent().getExtras().get("topicType");
		topicID = (Integer) getIntent().getExtras().get("topicID");
		Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
		Shared.mView = new CustomView();
		Shared.parent = (FrameLayout) findViewById(R.id.parent);
		Shared.parent.addView(Shared.mView);

		setUpClickListners();
	}

	private void setUpClickListners() {
		manualEdit = (ToggleButton) findViewById(R.id.manual);
		manualEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton btn, boolean isOn) {
//				Shared.saveCustomStamp();
				String key = topicType + "_" + topicID;
				app.signedHash.put(key, true);
				app.currentTopic.eSigned = true;

				if (isOn) {
					Shared.drawingMode = 2;
				} else {
					Shared.drawingMode = 0;
				}
			}
		});
		
		save.setOnClickListener(this);
		addUnits.setOnClickListener(this);
		t2shera.setOnClickListener(this);
		showQa2d.setOnClickListener(this);
		showHide.setOnClickListener(this);
		removeAll.setOnClickListener(this);
	}

	private void setSignatureMode(int mode) {
		Shared.signatureMode = mode;
		String key = topicType + "_" + topicID;
		
		app.signedHash.put(key, true);
		app.currentTopic.eSigned = true;
	}

	@Override
	public void onClick(View btn) {
		switch (btn.getId()) {
		case R.id.t2hera_button:
			manualEdit.setChecked(false);
			Shared.drawingMode = 1;
			app.handFreeMode = false;
			Shared.mView.save(Shared.parent);
			/*if (Shared.parent.getChildCount() > 1) {
				Shared.saveCustomStamp();
			}*/
			
			Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
			BitmapDrawable d = new BitmapDrawable(Shared.mBitmap);
			Shared.mView.setBackgroundDrawable(d);
			setSignatureMode(0);
			break;

		case R.id.commander_3ard:
			manualEdit.setChecked(false);
			ApplicationController.getInstance().handFreeMode = false;
			Shared.drawingMode = 1;
			Shared.mView.save(Shared.parent);
			if (Shared.parent.getChildCount() > 1) {
				Shared.saveCustomStamp();
			}
			
			Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
			d = new BitmapDrawable(Shared.mBitmap);
			Shared.mView.setBackgroundDrawable(d);
			setSignatureMode(1);
			break;

		case R.id.commander_save:
			manualEdit.setChecked(false);
			ApplicationController.getInstance().handFreeMode = false;
			Shared.drawingMode = 1;
			Shared.mView.save(Shared.parent);
			if (Shared.parent.getChildCount() > 1) {
				Shared.saveCustomStamp();
			}
			Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
			d = new BitmapDrawable(Shared.mBitmap);
			Shared.mView.setBackgroundDrawable(d);
			setSignatureMode(1);
			break;
			
		case R.id.show_hide:
			toolBox = (RelativeLayout) findViewById(R.id.tool_box);
			if (toolBox.getVisibility() == RelativeLayout.GONE) {
				toolBox.setVisibility(RelativeLayout.VISIBLE);
				btn.setBackgroundResource(R.drawable.down);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn
						.getLayoutParams();

				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
				params.addRule(RelativeLayout.ABOVE, R.id.tool_box);
				Constants.toolBooxHieght_assumption = 250 ;
			} else {
				toolBox.setVisibility(RelativeLayout.GONE);
				btn.setBackgroundResource(R.drawable.up);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn
						.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
						RelativeLayout.TRUE);
				
				Constants.toolBooxHieght_assumption = 0 ;
			}
			break;

		case R.id.add_units:
			Shared.drawingMode = 1;
			Shared.signatureMode = 2;
			Intent intent = new Intent(mContext, UnitSelection.class);
			startActivity(intent);
			break;

		case R.id.remove_all:
			Reverter reverter = new Reverter();
			reverter.execute();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Shared.saveCustomStamp(); // in case put signature and didn't agree button
		Shared.mView.save(Shared.parent);
		
		File currentImge= new File(Shared.imgPath) ;
		if (app.currentTopic.eSigned)
		{
			try {
				copyCheckedPaper(currentImge);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		finish();
	}
	
	private void copyCheckedPaper (File currentImge) throws Exception {
		
		File dir = new File(Environment.getExternalStorageDirectory(),
				Constants.printingFolder);
		dir.mkdir();
		File PhotoDir = new File(dir, topicType);
		PhotoDir.mkdir();
		PhotoDir.setWritable(true);
		File topicFolder = new File(PhotoDir, topicName);
		topicFolder.mkdir();
		topicFolder.setWritable(true);

		copy(currentImge, topicFolder);
	}
	
	private void copy(File source, File dst) throws IOException 
	{
		File destination = new File (dst, source.getName());
		InputStream in = new FileInputStream(source);
		OutputStream out = new FileOutputStream(destination);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
	private class Reverter extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			String key = topicType + "_" + topicID;
			app.signedHash.put(key, false);
			app.currentTopic.eSigned = false;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			File dir = new File(Environment.getExternalStorageDirectory(),
					app.getDirName());
			dir.mkdir();
			File PhotoDir = new File(dir, topicType);
			PhotoDir.mkdir();
			PhotoDir.setWritable(true);
			File topicFolder = new File(PhotoDir, topicName);
			topicFolder.mkdir();
			topicFolder.setWritable(true);
			while (topicFolder.list().length > 0)
				topicFolder.listFiles()[0].delete();

			File backup_dir = new File(
					Environment.getExternalStorageDirectory(), app.currentUser.backUpDir);
			backup_dir.mkdir();
			File backup_PhotoDir = new File(backup_dir, topicType);
			backup_PhotoDir.mkdir();
			backup_PhotoDir.setWritable(true);
			File backup_topicFolder = new File(backup_PhotoDir, topicName);
			backup_topicFolder.mkdir();

			try {
				app.copy(backup_topicFolder, topicFolder);
			} catch (Exception e) {
				Log.d("helal", e.getMessage());
			}

			Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
			BitmapDrawable d = new BitmapDrawable(Shared.mBitmap);
			Shared.mView.clear();
			Shared.mView.setBackgroundDrawable(d);

			// remove commander preview 
			if (! isQa2ed()) // delete suggest commander (only in arkan user)
				removecommanderPreview();
			
			// remove printing folder
			removePrinting();
		}
	}
	
	
	
	private void removePrinting()
	{
		File dir = new File(Environment.getExternalStorageDirectory(),Constants.printingFolder);
		dir.mkdir();
		File PhotoDir = new File(dir, topicType);
		PhotoDir.mkdir();
		PhotoDir.setWritable(true);
		File topicFolder = new File(PhotoDir, topicName);
		topicFolder.mkdir();
		topicFolder.setWritable(true);
		
		while (topicFolder.list().length > 0)
			topicFolder.listFiles()[0].delete() ;
		
		topicFolder.delete() ;
	}
	
	private void removecommanderPreview ()
	{
		File dir = new File(Environment.getExternalStorageDirectory(),Constants.commander3rdDir);
		dir.mkdir();
		File PhotoDir = new File(dir, topicType);
		PhotoDir.mkdir();
		PhotoDir.setWritable(true);
		File topicFolder = new File(PhotoDir, topicName);
		topicFolder.mkdir();
		topicFolder.setWritable(true);
		
		while (topicFolder.list().length > 0)
			topicFolder.listFiles()[0].delete() ;
		
		topicFolder.delete() ;
		
	}
	
}
