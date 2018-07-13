package com.location.mvp.mvp_route_demo.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/12 14:42
 * description：
 */

public class MyRealayout extends RelativeLayout {

	private boolean isOndraw = true;

	/**
	 * 父控件tag
	 */
	public String groupTag = "draw";
	/**
	 * 半圆画笔
	 */
	private Paint cricePaint;

	/**
	 * 圆心y点
	 */
	private List<Integer> y;


	private int radios;


	public MyRealayout(Context context) {
		this(context, null);
	}

	public MyRealayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyRealayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setBackgroundColor(Color.TRANSPARENT);
		cricePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		y = new ArrayList<>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		isOndraw = true;
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#ffffff"));
		//开启加速
		canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRect(new RectF(0, 0, width, height), paint);
		int offset = 90;
		//设置绘制模式为XOR
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
		for (int i = 0; i < y.size(); i++) {
			Integer yoff = y.get(i);
			LogUtils.d("yoff---" + yoff);
			canvas.drawCircle(0,yoff,offset,paint);
			canvas.drawCircle(width,yoff,offset,paint);

		}
		paint.setXfermode(null);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int childCount = getChildCount();
		if (isOndraw) {
			saveY(widthMeasureSpec, heightMeasureSpec, childCount);
			isOndraw = false;
		}
	}

	private synchronized void saveY(int widthMeasureSpec, int heightMeasureSpec, int childCount) {
		y.clear();
		for (int i = 0; i < childCount; i++) {
			final View childAt = getChildAt(i);
			if (childAt.getTag() != null && childAt.getTag() instanceof String) {
				String tag = (String) childAt.getTag();
				int top = 0;
				ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
				if (layoutParams instanceof MarginLayoutParams) {
					measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, MeasureSpec.getSize(heightMeasureSpec));
					top = ((MarginLayoutParams) layoutParams).topMargin;
				}
				if (groupTag.equals(tag)) {
					final int finalTop = top;
					childAt.post(new Runnable() {
						@Override
						public void run() {
							/**
							 * 圆心点= viewTop -martop/2
							 */
							int childAtTop = childAt.getTop();
							int offset = finalTop / 2;
							y.add(childAtTop - offset);
						}
					});

				}


			}
		}
	}

}
