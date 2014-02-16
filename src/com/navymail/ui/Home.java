package com.navymail.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import com.navymail.core.ApplicationController;

/*
 TabActivity with 2 tabs ( dakhly and khargy)
 default is khargy activity
 */

public class Home extends TabActivity {
	public static TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		tabHost = getTabHost();
		TabSpec da5llySpec = tabHost.newTabSpec("المكاتبات الداخلية");
		da5llySpec.setIndicator("المكاتبات الداخلية");
		Intent showdakhlyTopics = new Intent(this, ShowTopicsActivity.class);
		
		//send which category with activity, used in adapter (loadData and getView functions)
		showdakhlyTopics.putExtra("khargy", false);
		da5llySpec.setContent(showdakhlyTopics);

		TabSpec khargySpec = tabHost.newTabSpec("المكاتبات الخارجية");
		khargySpec.setIndicator("المكاتبات الخارجية");
		Intent showkhargyTopics = new Intent(this, ShowTopicsActivity.class);
		showkhargyTopics.putExtra("khargy", true);
		khargySpec.setContent(showkhargyTopics);

		tabHost.addTab(khargySpec);
		tabHost.addTab(da5llySpec);
		
		if (ApplicationController.getInstance().isKhargy)
			tabHost.setCurrentTab(0);
		else
			tabHost.setCurrentTab(1);
		
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(23);
        }
		
		
	}
	
	
}