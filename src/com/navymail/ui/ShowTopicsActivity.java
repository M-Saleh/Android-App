package com.navymail.ui;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import com.navymail.adapters.GridViewAdapter;
import com.navymail.core.NavymailActivity;

public class ShowTopicsActivity extends NavymailActivity {

	GridViewAdapter adapter;
	boolean isKhargy;
	
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_show_topics);
		isKhargy = getIntent().getExtras().getBoolean("khargy");
		adapter = new GridViewAdapter(this,	isKhargy);
		GridView gridView = (GridView) findViewById(R.id.grid_show_topics);
		gridView.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		app.isKhargy = isKhargy ;
		adapter.notifyDataSetChanged();
		
		int total = 0;
		int finished = 0;
		int remains = 0;
		
		if (app.isKhargy)
		{
			total = app.khargy_topics.size();
			for (int i = 0; i < app.khargy_topics.size(); i++) {
				if (app.khargy_topics.get(i).eSigned)
					finished++;
			}
		}
		else
		{
			total = app.da5ly_topics.size();
			for (int i = 0; i < app.da5ly_topics.size(); i++) {
				if (app.da5ly_topics.get(i).eSigned)
					finished++;
			}
		}
		remains = total - finished ;
		
		TextView total_counter = (TextView) findViewById(R.id.total_counter);
		total_counter.setText("إجمالــــــى : عدد "
				+ app.arabization(total + "") + " مكاتبة");
		TextView finished_counter = (TextView) findViewById(R.id.finished_counter);
		finished_counter.setText("تم مراجعة : عدد "
				+ app.arabization(finished + "") + " مكاتبة");

		TextView remain_counter = (TextView) findViewById(R.id.remain_counter);
		remain_counter.setText("المتبقــى : عدد "
				+ app.arabization(remains + "")
				+ " مكاتبة");
		
	}
}
