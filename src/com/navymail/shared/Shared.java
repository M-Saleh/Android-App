package com.navymail.shared;

import com.navymail.core.CustomView;
import com.navymail.ui.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Shared {

	public static Path mPath = new Path();
	public static Paint mPaint = new Paint();
	public static FrameLayout parent;
	public static FrameLayout customStamp;
	public static CustomView mView;
	public static Bitmap mBitmap;
	public static Canvas mCanvas;
	public static int screenWidth;
	public static int screenHeight;
	public static Context mContext;
	public static String imgPath;

	public static int OFFSET_X = 393;
	public static int OFFSET_Y = 489;

	public static int drawingMode = 0;
	public static int signatureMode = 0;

	public static void clear() {
		mPath = new Path();
		mPaint = new Paint();
		parent = null;
		customStamp = null;
		mView = null;
		mBitmap = null;
		mCanvas = null;
		drawingMode = 0;
		signatureMode = 0;
	}
	
	public static void saveCustomStamp() {
		if (customStamp == null)
				return;
		
		LinearLayout border = (LinearLayout) Shared.customStamp.findViewById(R.id.border);
		border.setBackgroundDrawable(null);
		Button agree = (Button) Shared.customStamp.findViewById(R.id.agree);
		agree.setVisibility(View.GONE);
		Button agree2 = (Button) Shared.customStamp.findViewById(R.id.agree2);
		agree2.setVisibility(View.GONE);
		
		Shared.mView.save(Shared.parent);
		
		if (Shared.parent.getChildCount() > 1)
			Shared.parent.removeViewAt(Shared.parent.getChildCount() - 1);
		
		Shared.mBitmap = BitmapFactory.decodeFile(Shared.imgPath);
		BitmapDrawable d = new BitmapDrawable(Shared.mBitmap);
		Shared.mView.setBackgroundDrawable(d);
		Shared.drawingMode = 0;
	}

}
