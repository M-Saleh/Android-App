package com.navymail.ui;

import java.util.Iterator;
import java.util.TreeMap;
import com.navymail.core.ApplicationController ;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class UnitSelection extends Activity {
	
	ApplicationController app = ApplicationController.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		app.selectedUnits = "";
		setContentView(R.layout.activity_unit_selection);
		Button done = (Button)findViewById(R.id.done);
		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				app.selectedUnits = prepareStr();
			}
		});
	}
	
	private String prepareStr()
	{
		TreeMap<Integer, String> heap = new TreeMap<Integer, String>(); 
		String result = "";
		LinearLayout parent = (LinearLayout)findViewById(R.id.parent);
		for (int i = 0 ; i < 2 ; i ++){
			LinearLayout childLayout = (LinearLayout)parent.getChildAt(i);
			for(int j = 0 ; j < childLayout.getChildCount() ; j ++){
				View v = childLayout.getChildAt(j);
				String tag = (String)v.getTag();
				if(tag != null && tag.equalsIgnoreCase("label"))
					continue;
				CheckBox cb = (CheckBox)v;
				if(cb.isChecked()){
					heap.put(Integer.parseInt(cb.getTag()+""), cb.getText()+"");
				}
			}
		}
		
		Iterator<Integer> itr = heap.navigableKeySet().iterator();
		while(itr.hasNext()){
			result += heap.get(itr.next())+"\n";
		}
		
		return result; 
	}
	

}
