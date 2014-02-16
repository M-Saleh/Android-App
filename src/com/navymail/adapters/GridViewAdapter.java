package com.navymail.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.navymail.core.NavymailActivity;
import com.navymail.modules.Topic;
import com.navymail.ui.R;
import com.navymail.ui.TopicDetails;

public class GridViewAdapter extends BaseAdapter {

	NavymailActivity activityInstance;
	boolean isKhargy = true;

	public GridViewAdapter(NavymailActivity activityInstance, boolean isKhargy) {
		this.activityInstance = activityInstance;
		this.isKhargy = isKhargy;
		loadData();
	}

	@Override
	public int getCount() {
		if(isKhargy)
			return activityInstance.app.khargy_topics.size();
		else
			return activityInstance.app.da5ly_topics.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		Topic topic;
		if(isKhargy)
			topic = activityInstance.app.khargy_topics.get(arg0);
		else
			topic = activityInstance.app.da5ly_topics.get(arg0);
		
		LayoutInflater inflater = LayoutInflater.from(activityInstance);
		
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.topic_icon, null);
		
		RelativeLayout container = (RelativeLayout) layout.getChildAt(1);
		TextView title = (TextView) container.getChildAt(0);
		title.setText(topic.title);

		if (topic.eSigned) {
			FrameLayout fl = (FrameLayout) layout.getChildAt(0);
			ImageView imgV = (ImageView) fl.getChildAt(1);
			imgV.setVisibility(View.VISIBLE);
		}

		final int topicID = topic.id;
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Start showing topic details
				Intent topicDetails = new Intent(activityInstance, TopicDetails.class);
				topicDetails.putExtra("topicID", topicID);
				if (isKhargy)
					topicDetails.putExtra("topicType", "khargy");
				else
					topicDetails.putExtra("topicType", "da5ly");
				
				activityInstance.startActivity(topicDetails);
				activityInstance.app.isKhargy = isKhargy;
			}
		});
		return layout;
	}

	private void loadData() {
		String dirName = "khargy";
		if (isKhargy) {
			activityInstance.app.khargy_topics.clear();
		} else {
			activityInstance.app.da5ly_topics.clear();
			dirName = "da5ly";
		}

		File dir = new File(Environment.getExternalStorageDirectory(),activityInstance.app.getDirName());
		dir.mkdir();
		File photoDir = new File(dir, dirName);
		photoDir.mkdir();
		File[] topicFolders = photoDir.listFiles();
		Arrays.sort(topicFolders);
		for (int i = 0; i < topicFolders.length; i++) {
			ArrayList<String> imageUrls = new ArrayList<String>();
			int id = i;
			String title = topicFolders[i].getName();
			String conclusion = "";
			File[] urls = topicFolders[i].listFiles();
			Arrays.sort(urls);
			for (int j = 0; j < urls.length; j++) {
				if (urls[j].getPath().contains(".jp"))
					imageUrls.add(urls[j].getPath());
			}

			boolean eSigned = false;
			Boolean b = (Boolean) activityInstance.app.signedHash.get(dirName
					+ "_" + id);
			
			if (b != null && b == true)
				eSigned = true;

			Topic topic = new Topic(id, title, conclusion, imageUrls, eSigned);

			if (isKhargy)
				activityInstance.app.khargy_topics.add(topic);
			else
				activityInstance.app.da5ly_topics.add(topic);
		}
	}
}
