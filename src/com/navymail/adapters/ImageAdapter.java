package com.navymail.adapters;

import java.util.HashMap;
import com.navymail.modules.Ta2shera;
import com.navymail.modules.Topic;
import com.navymail.ui.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ImageAdapter extends PagerAdapter {
	
	Context context;
	Topic topic;
	public FrameLayout currentView = null;
	HashMap<Integer, Ta2shera> hash;
	public ImageAdapter(Context context, Topic topic) {
		this.context = context;
		this.topic = topic;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// super.setPrimaryItem(container, position, object);
		currentView = (FrameLayout) object;
	}

	@Override
	public int getCount() {
		return topic.imageUrls.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((FrameLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		LayoutInflater inflater = LayoutInflater.from(context);
		FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.custom_frame, null);
		TextView tView = (TextView) frame.getChildAt(0);
		int padding = 10;
		tView.setPadding(padding, padding, padding, padding);
		Bitmap bmp = BitmapFactory.decodeFile(topic.imageUrls.get(position));
		BitmapDrawable d = new BitmapDrawable(bmp);
		tView.setBackgroundDrawable(d);		
		((ViewPager) container).addView(frame, 0);

		return frame;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((FrameLayout) object);
	}

	
	

}
