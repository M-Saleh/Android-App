package com.navymail.adapters;

import java.util.Date;
import android.app.Activity;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.navymail.core.ApplicationController;
import com.navymail.core.NavymailActivity;
import com.navymail.shared.Shared;
import com.navymail.ui.R;

public class PaintListener implements OnTouchListener, OnDragListener {

	ApplicationController app = ApplicationController.getInstance() ;
	public NavymailActivity activityInstance;
	public PaintListener(NavymailActivity activity) {
		this.activityInstance = activity;
	}

	@Override
	public boolean onDrag(View layoutview, DragEvent dragevent) {
		View view = (View) dragevent.getLocalState();
		if (view == null) {
			return true;
		}

		int action = dragevent.getAction();
		switch (action) {
		case DragEvent.ACTION_DROP:
			ViewGroup owner = (ViewGroup) view.getParent();
			owner.removeView(view);

			int w = view.getWidth() / 2;
			int h = view.getHeight() / 2;
			int x = (int) dragevent.getX() - w;
			int y = (int) dragevent.getY() - h;

			view.setX(x);
			view.setY(y);

			Shared.parent.addView(view);
			view.setVisibility(View.VISIBLE);
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		if (Shared.drawingMode == 2) {  // free hand mode
			Shared.mPaint.setColor(Color.parseColor(activityInstance.app.currentUser.signatureColor));
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Shared.mPath.moveTo(eventX, eventY);
				return true;

			case MotionEvent.ACTION_MOVE:

			case MotionEvent.ACTION_UP:

				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					Shared.mPath.lineTo(historicalX, historicalY);
				}
				Shared.mPath.lineTo(eventX, eventY);
				break;

			default:
				return false;
			}

			v.invalidate();

		} else if (Shared.drawingMode == 1) { // stamp
			// stamp signature mode
			Shared.mPaint.setColor(Color.TRANSPARENT);

			if (Shared.parent.getChildCount() > 1)
				Shared.parent.removeViewAt(Shared.parent.getChildCount() - 1);

			prepareSignature(Shared.signatureMode);

			Shared.customStamp.setX(eventX - 100);
			Shared.customStamp.setY(eventY - 100);
			Shared.parent.addView(Shared.customStamp);

			v.invalidate();
		}
		return true;
	}

	public void prepareSignature(int type) {
		
		LayoutInflater inflater = LayoutInflater.from(activityInstance);
		Shared.customStamp = (FrameLayout) inflater.inflate(R.layout.custom_tamp, null);
		LinearLayout border = (LinearLayout) Shared.customStamp.findViewById(R.id.border);
		ImageView signature = (ImageView) border.getChildAt(1); // signature (Image view)
		signature.setLayoutParams(new LinearLayout.LayoutParams(200, 100));
		TextView sigInfo = (TextView) border.getChildAt(0);  // (tasdeq, 3ard, et5az l lazm , ...)
		TextView sigDate = (TextView) border.getChildAt(2);
		
//		Setting margin for data under the sign
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams
				(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		
		if (activityInstance.isQa2ed())
			llp.setMargins(0, -10, 0, 0);
		else
			llp.setMargins(0, -30, 0, 0);
		
		sigDate.setLayoutParams(llp);
		
		ImageView sign = (ImageView) Shared.customStamp.findViewById(R.id.sign);
		if (activityInstance.isQa2ed())
			sign.setImageResource(R.drawable.sign_qa2ek);
		else
			sign.setImageResource(R.drawable.sign_arkan);
		
		sigDate.setTextColor(Color.parseColor(activityInstance.app.currentUser.signatureColor)) ;
		sigInfo.setTextColor(Color.parseColor(activityInstance.app.currentUser.signatureColor)) ;

		String preparedStr = "";
		String date = "";

		switch (type) {
		case 0: // (tasdeq, 3ard, et5az l lazm , ...)
			preparedStr = prepareSignatureText();
			Date d = new Date();
			date = d.getDate() + "-" + (d.getMonth() + 1) + "-"
					+ (d.getYear() + 1900);
			date = activityInstance.app.arabization(date);
			break;

		case 1: // save or 3ard
			if (activityInstance.isQa2ed())
			{
				signature.setImageResource(R.drawable.sa7);
				signature.setLayoutParams(new LinearLayout.LayoutParams(170, 170));
				d = new Date();
				String dateStr = d.getDate() + "-" + (d.getMonth() + 1) + "-"
						+ (d.getYear() + 1900);
				date = "";
				preparedStr = activityInstance.app.arabization(dateStr);
//				activityInstance.app.selectedUnits = "";
			}
			else // user is ra2es arkan and select commander 3ard
			{
				signature.setLayoutParams(new LinearLayout.LayoutParams(150, 75));
				signature.setImageResource(R.drawable.commander_3ard);
				preparedStr = "";
				app.isCommanderPreview = true;
			}
			break;
			
		case 2: // add units
			signature.setVisibility(View.GONE);
			preparedStr = activityInstance.app.selectedUnits;
			sigInfo.setTextSize(20);
			break;

		case 3: // Only in case of Qa2ed and i think it is not usable
			signature.setImageResource(R.drawable.tasadak_fareeq);
			signature.setLayoutParams(new LinearLayout.LayoutParams(250, 130));

			d = new Date();
			date = d.getDate() + "-" + (d.getMonth() + 1) + "-"
					+ (d.getYear() + 1900);
			date = activityInstance.app.arabization(date);
			preparedStr = "";
//			activityInstance.app.selectedUnits = "";
			break;

		default:
			break;
		}
		if (activityInstance.app.selectedUnits == null)
			activityInstance.app.selectedUnits = "";
		
		String st = preparedStr;
		int numLines = st.split("\n").length;
		int w = 300;
		int h = 235 + numLines * 35;
		Shared.customStamp.setLayoutParams(new LayoutParams(w, h));

		sigInfo.setText(preparedStr);
		sigDate.setText(date);

//		agree = tasbet l t2shera
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Shared.saveCustomStamp();
			}
		};
		
		Button agree = (Button) Shared.customStamp.findViewById(R.id.agree);
		agree.setOnClickListener(listener);
		Button agree2 = (Button) Shared.customStamp.findViewById(R.id.agree2);
		agree2.setOnClickListener(listener);
	}

	private String prepareSignatureText() {
		RelativeLayout tool_box = (RelativeLayout) ((Activity) activityInstance)
				.findViewById(R.id.tool_box);
		String str = "";
		for (int i = 0; i < 2; i++) {
			LinearLayout container = (LinearLayout) tool_box.getChildAt(i);
			for (int j = 0; j < container.getChildCount(); j++) {
				if (container.getChildAt(j).getClass() == CheckBox.class) {
					CheckBox c = (CheckBox) container.getChildAt(j);
					if (c.isChecked()) {
						str = str + c.getText() + "\n";
					}
				}
			}
		}
		activityInstance.app.cachedOrders = str;
		return str;
	}

}