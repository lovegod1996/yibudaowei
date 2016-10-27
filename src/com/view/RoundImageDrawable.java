package com.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class RoundImageDrawable extends Drawable {
	private Paint mPaint;  
	private Bitmap mBitmap;  
	private RectF rectF;  

	public RoundImageDrawable(Bitmap bitmap)  
	{  
	    mBitmap = bitmap;  
	    BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP,  TileMode.CLAMP);  
	    mPaint = new Paint();  
	    mPaint.setAntiAlias(true);  
	    mPaint.setShader(bitmapShader);  
	}  
	
	@Override  
	public void setBounds(int left, int top, int right, int bottom) {  
	    super.setBounds(left, top, right, bottom);  
	    rectF = new RectF(left, top, right, bottom);  
	}  
		
	@Override
	public void draw(Canvas canvas) {	
		canvas.drawRoundRect(rectF, 20, 20, mPaint); 
	}

	@Override  
	public int getIntrinsicWidth() {  
	    return mBitmap.getWidth();  
	}  

	@Override  
	public int getIntrinsicHeight() {  
	    return mBitmap.getHeight();  
	}  	

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		mPaint.setAlpha(alpha); 
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		   mPaint.setColorFilter(cf); 
	}
	@Override  
	public int getOpacity() {  
	    return PixelFormat.TRANSLUCENT;  
	}  
}


