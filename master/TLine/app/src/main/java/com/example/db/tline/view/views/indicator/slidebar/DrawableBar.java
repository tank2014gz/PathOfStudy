package com.example.db.tline.view.views.indicator.slidebar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 
 * @author试着飞
 * @date 2014年11月2日
 * @version 1.0
 * 通过图片来设置滑动块
 */
public class DrawableBar implements ScrollBar {
	protected Gravity gravity;
	protected View view;
	protected int drawableId;
	protected Drawable drawable;

	public DrawableBar(Context context, int drawableId) {
		this(context, drawableId, Gravity.BOTTOM);
	}

	public DrawableBar(Context context, int drawableId, Gravity gravity) {
		this(context, context.getResources().getDrawable(drawableId), gravity);
	}

	public DrawableBar(Context context, Drawable drawable) {
		this(context, drawable, Gravity.BOTTOM);
	}

	public DrawableBar(Context context, Drawable drawable, Gravity gravity) {
		view = new View(context);
		this.drawable = drawable;
		view.setBackgroundDrawable(drawable);
		this.gravity = gravity;
	}

	public int getColor() {
		return drawableId;
	}

	public void setColor(int color) {
		this.drawableId = color;
		view.setBackgroundColor(color);
	}

	@Override
	public int getHeight(int tabHeight) {
		return drawable.getIntrinsicHeight();
	}

	@Override
	public int getWidth(int tabWidth) {
		return drawable.getIntrinsicHeight();
	}

	@Override
	public View getSlideView() {
		return view;
	}

	@Override
	public Gravity getGravity() {
		return gravity;
	}

	public DrawableBar setGravity(Gravity gravity) {
		this.gravity = gravity;
		return this;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

}
