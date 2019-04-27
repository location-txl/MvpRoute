package com.location.mvp.mvproutelibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author tianxiaolong
 * time：19-4-27 下午5:16
 * description：
 */
public class SlideLayout extends ViewGroup {

	private static final boolean DEBUG  = true;
	private static final int TYPE_DEFAULT = 0x001;
	private static final int TYPE_SCROLL = 0x002;
	private static final int TYPE_NEXT = 0x003;
	private static final String TAG  = "SlideLayout-help";
	private boolean layoutView = false;
	private int offset;
	private int type = TYPE_DEFAULT;
	private int paddingTop;
	private int paddingBottom;
	private int paddingLeft;
	private int paddingRight;
	private int oldX,oldY;
	private int nextWidth;



	public SlideLayout(Context context) {
		this(context,null);
	}

	public SlideLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
		if(!layoutView)return;
		log("onLayout");
		View childAt = getChildAt(0);
		View nextView = getChildAt(1);
		switch (type){
			   case TYPE_DEFAULT:
			   		log("layout-default");
				   childAt.layout(paddingLeft,paddingTop,paddingLeft+childAt.getMeasuredWidth(),paddingTop+childAt.getMeasuredHeight());
				   nextView.layout(0,0,0,0);
				   break;

			   case TYPE_SCROLL:
			   	   log("layout-scroll");
			   	   int left = paddingLeft-offset;
			   	   int top = paddingTop;
			   	   int right = left+childAt.getMeasuredWidth();
			   	   int bottom = top+childAt.getMeasuredHeight();
			   	   childAt.layout(left,top,right,bottom);
			   	   nextView.layout(right,top,right+nextWidth,top+nextView.getMeasuredHeight());
			    	break;
			   case TYPE_NEXT:
				   log("layout-next");
			   	     if(getChildCount()>=2){
			   	     	//have next view
						 int measuredHeight = nextView.getMeasuredHeight();
						 int measuredWidth = nextView.getMeasuredWidth();
						 int defaultWidth = childAt.getMeasuredWidth();
						 int defaultHeight = childAt.getMeasuredHeight();
						 int measuredGroupWidth = getMeasuredWidth()-paddingLeft-paddingRight;
						 int dRight = measuredGroupWidth - measuredWidth;
						 int dleft = dRight - defaultWidth;
						 childAt.layout(dleft+paddingLeft,paddingTop,dRight,defaultHeight+paddingTop);
						 nextView.layout(dRight,paddingTop,dRight+measuredWidth,paddingTop+measuredHeight);
					 }else{
			   	     	// no have child no nothing
					 }
			   	    break;

			   	    default:
			   		      log("default type:"+type);

		   }
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		log("onMeasure");
		int count = getChildCount();
		if(count<1){
			setMeasuredDimension(0,0);
		}
		int rootW = 0;
		int rootH = 0;
		for(int i=0;i<count;i++){
			View childView = getChildAt(i);
			measureChild(childView,widthMeasureSpec,heightMeasureSpec);
			int measuredHeight = childView.getMeasuredHeight();
			int measuredWidth = childView.getMeasuredWidth();
			if(i==0){
				rootW = measuredWidth;
				rootH = measuredHeight;
			}else if(i==1){
				nextWidth  = measuredWidth;
			}
		}
		paddingTop = getPaddingTop();
		paddingBottom = getPaddingBottom();
		paddingLeft = getPaddingLeft();
		paddingRight = getPaddingRight();
		layoutView = true;
		setMeasuredDimension(rootW+ paddingLeft + paddingRight,rootH+ paddingTop + paddingBottom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action){
			case  MotionEvent.ACTION_DOWN:
				log("down");
				oldX = x;
				oldY = y;
				break;
			case MotionEvent.ACTION_MOVE:
					log("move");
					if(offset<=0&&oldX<=x)return true;
					if(x>oldX){
						offset-=x-oldX;
					}else {
						offset+=oldX-x;
					}

					if(offset<0){
						offset = 0;
					}
					type = TYPE_SCROLL;
					requestLayout();
					log("offset:"+offset);
					oldX = x;
					oldY = y;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				log("cancle");
				if(offset>nextWidth/2){
					offset = nextWidth;
					type= TYPE_NEXT;
				}else{
					offset=  0;
					type = TYPE_DEFAULT;
				}
				oldY = oldX = -1;
				requestLayout();
				break;

		}
		return true;
	}

	private void log(String msg){
		if(DEBUG) Log.d(TAG, msg);
	}
}
