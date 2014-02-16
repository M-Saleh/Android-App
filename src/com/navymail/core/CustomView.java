package com.navymail.core;

import java.io.File;
import java.io.FileOutputStream;
import com.navymail.adapters.PaintListener;
import com.navymail.shared.Shared ;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class CustomView extends View {
	
	private static final float STROKE_WIDTH = 2f;
	
	public CustomView() {
		super(Shared.mContext);
		Shared.mPaint.setAntiAlias(true);
		Shared.mPaint.setStyle(Paint.Style.STROKE);
		Shared.mPaint.setStrokeJoin(Paint.Join.ROUND);
		Shared.mPaint.setStrokeWidth(STROKE_WIDTH);
	
		Shared.mPaint.setColor(Color.TRANSPARENT);
		
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		
		BitmapDrawable d = new BitmapDrawable(Shared.mBitmap);
		setBackgroundDrawable(d);
		
		PaintListener pListener = new PaintListener((NavymailActivity)Shared.mContext);
		this.setOnTouchListener(pListener);
		this.setId(808);
	}

	public void save(View v) {
		try {
				Shared.parent.setDrawingCacheEnabled(true);
				Shared.parent.buildDrawingCache();
				Bitmap bmp = Bitmap.createBitmap(Shared.parent.getWidth(),
						Shared.parent.getHeight(), Bitmap.Config.ARGB_8888);
				
				Canvas canvas = new Canvas(bmp);
				v.draw(canvas);
				// Bitmap bm = parent.getDrawingCache();
				File newImage = new File(Shared.imgPath);
				FileOutputStream out = new FileOutputStream(newImage);
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
				Shared.parent.destroyDrawingCache();
		} catch (Exception e) {
			Log.d("exception", e.getMessage());
		}
	}

	public void clear() {
		Shared.mPath.reset();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(Shared.mPath, Shared.mPaint);
	}

}
