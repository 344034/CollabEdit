package com.umn.mcc.collabedit.paintapp;

import java.io.Serializable;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;

public class Edit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 589633473141403042L;

	private int type;

	//Drawing edit features
	private int mPaintColor;
	private boolean mIsAntiAlias;
	private float mStrokeWidth;
	private Join mStrokeJoin;
	private Cap mStrokeCap;
	private Style mStyle;
	private float mXPos;
	private float mYPos;
	private int mMotionEvent;
	private boolean erase = false;


	Edit(int type){
		this.type= type;
	}

	public int getType(){
		return type;
	}

	public void setType(int type){
		this.type = type;
	}

	public void deepCopyPaint(Paint paint){
		mIsAntiAlias = paint.isAntiAlias();
		mPaintColor = paint.getColor();
		mStrokeWidth = paint.getStrokeWidth();
		mStrokeJoin = paint.getStrokeJoin();
		mStrokeCap = paint.getStrokeCap();
		mStyle = paint.getStyle();
	}

	public void positionValues(float x, float y){
		mXPos = x;
		mYPos = y;
	}

	public void setEventType(int motion){
		mMotionEvent = motion;
	}
	public void setEraseValue(boolean erase){
		this.erase = erase;
	}

	public Paint getPaintObject(){
		if(type != Constants.Type.DRAWING)
			return null;
		Paint paint = new Paint();
		paint.setStrokeCap(mStrokeCap);
		paint.setStrokeJoin(mStrokeJoin);
		paint.setStrokeWidth(mStrokeWidth);
		paint.setAntiAlias(mIsAntiAlias);
		paint.setColor(mPaintColor);
		paint.setStyle(mStyle);
		setErase(erase, paint);
		return paint;
	}
	public float[] getPositionValues(){
		float[] pos = {mXPos, mYPos};
		return pos;
	}

	public int getEventType(){
		return mMotionEvent;
	}

	public boolean getErase(){
		return erase;
	}
	
	public void setErase(boolean isErase, Paint drawPaint){
		erase=isErase;
		if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		else drawPaint.setXfermode(null);
	}
}
