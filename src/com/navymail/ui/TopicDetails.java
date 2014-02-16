package com.navymail.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.navymail.adapters.ImageAdapter;
import com.navymail.core.ApplicationController;
import com.navymail.core.Constants;
import com.navymail.core.NavymailActivity;
import com.navymail.shared.Shared;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ZoomControls;

public class TopicDetails extends NavymailActivity {

	ApplicationController controller = ApplicationController.getInstance();
	int topicID;
	ViewPager pager;
	FrameLayout imgFrame;
	String topicType = "";
	Button editBtn;
	Button revert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_details);
		
		editBtn = (Button)findViewById(R.id.edit_btn);
		editBtn.setText(editBtn.getText() + app.currentUser.jobName) ;
		
		app.cachedOrders = "";
//		app.selectedUnits = "";
		imgFrame = (FrameLayout) findViewById(R.id.img_frame);
		pager = (ViewPager) findViewById(R.id.view_pager);
		setUpTopic();
		setUpClickListeners();
		ImageAdapter adapter = new ImageAdapter(mContext, app.currentTopic);
		pager.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		int currentItem = pager.getCurrentItem();
		ImageAdapter adapter = new ImageAdapter(mContext, app.currentTopic);
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentItem);
	}

	private void setUpTopic() {
		topicID = (Integer) getIntent().getExtras().get("topicID");
		topicType = (String) getIntent().getExtras().getString("topicType");

		if (topicType.equalsIgnoreCase("khargy"))
			app.currentTopic = app.khargy_topics.get(topicID);
		else
			app.currentTopic = app.da5ly_topics.get(topicID);
	}

	private void setUpClickListeners(){	
		
//		editBtn = t2shera
		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				File dir = new File(Environment.getExternalStorageDirectory(),app.getDirName());
				dir.mkdir();
				File PhotoDir = new File(dir, topicType);
				PhotoDir.mkdir();
				PhotoDir.setWritable(true);
				File topicFolder = new File(PhotoDir, app.currentTopic.title);
				topicFolder.mkdir();
				topicFolder.setWritable(true);
				
				String fileName = ApplicationController.getInstance().getFileName(pager.getCurrentItem() + 1);
				File newImage = new File(topicFolder, fileName);
				
				Intent editIntent = new Intent(mContext, EditTopic.class);
				editIntent.putExtra("path", newImage.getPath());
				editIntent.putExtra("topicName", app.currentTopic.title);
				editIntent.putExtra("topicType", topicType);
				editIntent.putExtra("topicID", topicID);
				
				startActivity(editIntent);
			}
		});

		revert = (Button) findViewById(R.id.clear);
		revert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
					Reverter r = new Reverter();
					r.execute();
			}
		});
		
		ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoom_control);
		zoomControls.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// zoom in
				pager.setScaleX(pager.getScaleX()+.2f);
				pager.setScaleY(pager.getScaleY()+.2f);
			}
		});

		zoomControls.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// zoom out
				pager.setScaleX(pager.getScaleX()-.2f);
				pager.setScaleY(pager.getScaleY()-.2f);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		if(app.currentTopic.eSigned)
		{
			addFirstPagePrinting();
		}
		//exportImage();
		finish();
		// copy to commander 3ard
		if(app.isCommanderPreview)
		{
			app.isCommanderPreview = false;
			new Commander3rd().execute();
		}
	}

	@Override
	public void onClick(View arg0) {
	}

	@Override
	public void customOnCreate() {
	}
	
	private void addFirstPagePrinting()
	{
		File dir = new File(Environment.getExternalStorageDirectory(),
				Constants.printingFolder);
		dir.mkdir();
		File PhotoDir = new File(dir, topicType);
		PhotoDir.mkdir();
		PhotoDir.setWritable(true);
		File topicFolder = new File(PhotoDir, app.currentTopic.title);
		topicFolder.mkdir();
		topicFolder.setWritable(true);
		
		File dir2 = new File(Environment.getExternalStorageDirectory(),app.getDirName());
		dir2.mkdir();
		File firstImg = new File(dir2, topicType);
		firstImg.mkdir();
		firstImg.setWritable(true);
		File firstImgFolder = new File(firstImg, app.currentTopic.title);
		firstImgFolder.mkdir();
		
		firstImg = firstImgFolder.listFiles()[0];
		
		try {
			copy(firstImg, topicFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			controller.currentTopic.eSigned = false;
			String key = topicType + "_" + topicID;
			controller.signedHash.put(key, false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			File dir = new File(Environment.getExternalStorageDirectory(),app.getDirName());
			dir.mkdir();
			File PhotoDir = new File(dir, topicType);
			PhotoDir.mkdir();
			PhotoDir.setWritable(true);
			File topicFolder = new File(PhotoDir, controller.currentTopic.title);
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
			File backup_topicFolder = new File(backup_PhotoDir,
					controller.currentTopic.title);
			backup_topicFolder.mkdir();

			try {
				controller.copy(backup_topicFolder, topicFolder);
			} catch (Exception e) {
				Log.d("helal", e.getMessage());
			}

			int currentItem = pager.getCurrentItem();
			ImageAdapter adapter = new ImageAdapter(mContext, controller.currentTopic);
			pager.setAdapter(adapter);
			pager.setCurrentItem(currentItem);
		
			// remove commander preview 
			if (! isQa2ed()) // delete suggest commander (only in arkan user)
				removecommanderPreview();
			
			// remove printing folder
			removePrinting();
		}
	}

	private class Commander3rd extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			File dir = new File(Environment.getExternalStorageDirectory(),app.getDirName());
			dir.mkdir();
			File PhotoDir = new File(dir, topicType);
			PhotoDir.mkdir();
			PhotoDir.setWritable(true);
			File topicFolder = new File(PhotoDir, app.currentTopic.title);
			topicFolder.mkdir();
			topicFolder.setWritable(true);
			
			File backup_dir = new File(Environment.getExternalStorageDirectory(), Constants.commander3rdDir);
			backup_dir.mkdir();
			File backup_PhotoDir = new File(backup_dir, topicType);
			backup_PhotoDir.mkdir();
			backup_PhotoDir.setWritable(true);
			File topicFolder_3ard = new File(backup_PhotoDir, app.currentTopic.title);
			topicFolder_3ard.mkdir();

			try {
				app.copy(topicFolder, topicFolder_3ard);
			} catch (Exception e) {
				Log.d("Navy Mail", e.getMessage());
			}
			
		}
	}
	private void removePrinting()
	{
		File dir = new File(Environment.getExternalStorageDirectory(),Constants.printingFolder);
		dir.mkdir();
		File PhotoDir = new File(dir, topicType);
		PhotoDir.mkdir();
		PhotoDir.setWritable(true);
		File topicFolder = new File(PhotoDir, app.currentTopic.title);
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
		File topicFolder = new File(PhotoDir, app.currentTopic.title);
		topicFolder.mkdir();
		topicFolder.setWritable(true);
		
		while (topicFolder.list().length > 0)
			topicFolder.listFiles()[0].delete() ;
		
		topicFolder.delete() ;
		
	}
}
