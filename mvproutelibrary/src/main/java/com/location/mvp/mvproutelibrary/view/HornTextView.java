package com.location.mvp.mvproutelibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 项目:Mvp_Route_Demo
 *
 * @author：location time：2018/6/28 15:07
 * description：
 */

public class HornTextView extends android.support.v7.widget.AppCompatTextView {
     //角的宽度
	private int hornWidth = 50;
	//颜色
	private int hornColor;
	//path
	private Path bordPath;
	//虚线画笔
	private Paint mDffPaint;
	//虚线到实线的距离
	private int diffmargin = 10;


	public HornTextView(Context context) {
		super(context);
	}

	public HornTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HornTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setBackgroundDrawable(null);
		bordPath = new Path();
		mDffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDffPaint.setStyle(Paint.Style.STROKE);
		mDffPaint.setColor(Color.WHITE);
		mDffPaint.setStrokeWidth(3);
		mDffPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));

	}


	@Override
	protected void onDraw(Canvas canvas) {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		//重置原点
		bordPath.reset();
		//设置原点为最左边中间
		bordPath.moveTo(0, height / 2);
		//画左斜上
		bordPath.lineTo(hornWidth, 0);
		//画上直
		bordPath.lineTo(width - hornWidth, 0);
		//画右斜上
		bordPath.lineTo(width, height / 2);
		//画右斜下
		bordPath.lineTo(width - hornWidth, height);
		//画右直
		bordPath.lineTo(hornWidth, height);
		//画左下斜
		bordPath.lineTo(0, height / 2);
		//闭合
		bordPath.close();
		canvas.drawPath(bordPath, paint);
		bordPath.reset();
		bordPath.moveTo(0 + diffmargin, height / 2);
		bordPath.lineTo(hornWidth, 0 + diffmargin);
		bordPath.lineTo(width - hornWidth, 0 + diffmargin);
		bordPath.lineTo(width - diffmargin, height / 2);
		bordPath.lineTo(width - hornWidth, height - diffmargin);
		bordPath.lineTo(hornWidth, height - diffmargin);
		bordPath.lineTo(0 + diffmargin, height / 2);
		bordPath.close();
		canvas.drawPath(bordPath, mDffPaint);
		super.onDraw(canvas);
	}
}
