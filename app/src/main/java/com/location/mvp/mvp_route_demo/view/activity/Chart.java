package com.location.mvp.mvp_route_demo.view.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.location.mvp.mvp_route_demo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TianBin on 2017/7/1 12:38.
 * Description :仿金融APP收益曲线图
 */

public class Chart extends View implements View.OnTouchListener {
    private static final String TAG = "Chart";

    /**
     * 图表的线的paint
     */
    private Paint linePaint = new Paint();
    /**
     * 填充的paint
     */
    private Paint fillPaint = new Paint();
    /**
     * x轴（横向）分割线
     */
    private Paint xSeparatePaint = new Paint();
    /**
     * 顶点
     */
    private Paint pointPaint=new Paint();
    /**
     * 图表的坐标点的值的paint
     */
    private TextPaint labelXPaint=new TextPaint();
    private TextPaint labelYPaint=new TextPaint();
    private TextPaint labelValuePaint=new TextPaint();

    /**
     * 绘制曲线的路径
     */
    private Path path = new Path();

    /**
     * 动画相关
     */
    private PathMeasure mPathMeasure;
    private ValueAnimator valueAnimator;
    private float mAnimatorValue;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;

    /**
     * 数据集合
     */
    private List<Model> datas=new ArrayList<>();

    /**
     * 最小最大值
     */
    private int min,max;
    /**
     * y坐标每个阶梯的高度
     */
    private float perHeight;

    /**
     * 坐标文本高度
     */
    private float labelXHeight,labelYHeight,labelValueHeight;

    /**
     * 负数的位置
     */
    private int negativePos=-1;

    /**
     * y轴坐标数
     */
    private static final int ySize=5;
    /**
     * x轴坐标数
     */
    private static final int xSize=7;

    /**
     * 坐标计算相关。。。
     */
    private float xPadding;
    private float perY;
    private float topY;
    private float bottomY;
    private float perPercent;
    private float xVertical;
    private float eachWidth;
    private DecimalFormat df;

    /**
     * 是否播放动画
     */
    private boolean playAnim=true;
    /**
     * 默认的动效周期 2s
     */
    private int defaultDuration = 1500;

    /**
     * 坐标点集合
     */
    private float[][] points;
    /**
     * 选中的value
     */
    private Model selectedValue;

    /**
     * 动画是否结束
     */
    private boolean isOver=false;
    /**
     * 是否充满曲线下面的区域
     */
    private boolean isFillArea=true;
    /**
     * 是否绘制顶点
     */
    private boolean drawPoints=true;

    /**
     * 填充区域随曲线一起运动
     */
    private boolean fillAreaHasAnim=true;

    /**
     * 横坐标跟point是否是一对一关系（point有几百个点，但坐标轴只取平均7个，曲线则全部绘制）
     */
    private boolean isOneToOne=true;
    private String xLabels[]=new String[7];

    public Chart(Context context) {
        this(context,null);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        setOnTouchListener(this);
        df = new DecimalFormat("#0.00");

        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        linePaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        linePaint.setDither(true);//防抖动
        linePaint.setShader(null);
        linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3f,getResources().getDisplayMetrics()));
        linePaint.setColor(Color.RED);

        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        fillPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        fillPaint.setDither(true);//防抖动
        fillPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3f,getResources().getDisplayMetrics()));
        fillPaint.setColor(Color.BLUE);

        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        pointPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        pointPaint.setDither(true);//防抖动
        pointPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3f,getResources().getDisplayMetrics()));
        pointPaint.setColor(Color.BLACK);

        xSeparatePaint.setAntiAlias(true);
        xSeparatePaint.setStyle(Paint.Style.STROKE);
        xSeparatePaint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        xSeparatePaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        xSeparatePaint.setDither(true);//防抖动
        xSeparatePaint.setShader(null);
        xSeparatePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,0.5f,getResources().getDisplayMetrics()));
        xSeparatePaint.setColor(Color.GRAY);

        labelXPaint.setAntiAlias(true);
        labelXPaint.setColor(Color.BLACK);
        labelXPaint.setTextAlign(Paint.Align.CENTER);
        labelXPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
        labelXHeight=labelXPaint.getFontMetrics().bottom-labelXPaint.getFontMetrics().top;

        labelYPaint.setAntiAlias(true);
        labelYPaint.setColor(Color.BLACK);
        labelYPaint.setTextAlign(Paint.Align.RIGHT);
        labelYPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
        labelYHeight=labelYPaint.getFontMetrics().bottom-labelYPaint.getFontMetrics().top;

        labelValuePaint.setAntiAlias(true);
        labelValuePaint.setColor(Color.BLUE);
        labelValuePaint.setTextAlign(Paint.Align.CENTER);
        labelValuePaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
//        Typeface font = Typeface.createFromAsset(getResources().getAssets(),"");
//        labelValuePaint.setTypeface( font );
        labelValueHeight=labelValuePaint.getFontMetrics().bottom-labelValuePaint.getFontMetrics().top;

    }

    public void setDatas(List<Model> datas){
//        Log.e(TAG, "setDatas: "+getHeight() );
        if(datas==null||datas.size()==0){
            return;
        }
        this.datas.clear();
        this.datas.addAll(datas);
        points=new float[datas.size()][2];
        if(datas.size()>xSize){
            isOneToOne=false;
        }

        //放大为整数，避免浮点运算
        min= (int) (datas.get(0).percent*100);
        max= (int) (datas.get(0).percent*100);
        if(datas.get(0).percent<0){
            negativePos=0;
        }

        for (int i = 1; i < datas.size(); i++) {
            int f= (int) (datas.get(i).percent*100);
            if(min>f){
                min=f;
            }
            if(max<f){
                max=f;
            }
            if(datas.get(i).percent<0){
                negativePos=i;
            }
        }

        //处理x轴坐标
        int average= (int) Math.ceil(datas.size()/7f);
        for (int i = 0; i < xLabels.length; i++) {
            for (int j=average*i+1;j<datas.size();j++) {
                if(j%average==0){
                    xLabels[i]=datas.get(j).date;
//                    Log.e(TAG, i+"setDatas: "+j );
                    break;
                }else{
                    if(j==datas.size()-1){
                        xLabels[i]=datas.get(j).date;
                    }
                }
            }
        }


        //转换比例，找出最大和最小进行换算每个梯度所占像素
        perPercent=(max-min)/(ySize-1);
//        Log.e(TAG, "onDraw: "+perPercent+"@"+min+"#"+max );
        perHeight=(getHeight()-getPaddingTop()-getPaddingBottom())/(ySize+1);

        //坐标右对齐========Y轴处理
        String str=negativePos==-1?datas.get(datas.size()-1).percent+"%":datas.get(negativePos).percent+"%";
        xVertical=getPaddingLeft()+labelYPaint.measureText(str);
        xPadding= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());

        //X轴处理和曲线绘制,居中对齐
        eachWidth=(getWidth()-getPaddingRight()-xVertical-xPadding)/xSize;
        bottomY=getHeight()-getPaddingBottom()-perHeight-labelYHeight/3f;//最低点坐标
        topY=perHeight+getPaddingTop()-labelYHeight/3f;//最高点坐标
        perY=(bottomY-topY)/((max-min)/100f);//每个刻度所占坐标像素

        float eachWidthTemp=eachWidth*xSize/datas.size();
        for (int i = 0; i < datas.size(); i++) {
            float x=i*eachWidth+xPadding+xVertical+eachWidth/2f;
            if(!isOneToOne){
                x=i*eachWidthTemp+xPadding+xVertical;
            }
            float y=bottomY-perY*(datas.get(i).percent-min/100f);
//            Log.e(TAG, "onDraw: "+y+"#"+perY+"$"+bottomY+"$"+topY );

            points[i][0]=x;
            points[i][1]=y;
//            if(i==0){
//                path.moveTo(x,y);
//            }else{
//                path.lineTo(x,y);
//            }
        }
        initPath();
        initListener();
        initAnimator();
        if(playAnim){
            valueAnimator.start();
        }

        fillPaint.setShader(new LinearGradient(0, 0, 0, getHeight(), fillPaint.getColor(),
                fillPaint.getColor() & 0x00ffffff, Shader.TileMode.MIRROR));

        invalidate();
    }

    private void initPath(){
        path.moveTo(points[points.length-1][0],points[points.length-1][1]);
        for (int i = points.length-1; i >=0; i--) {
            path.lineTo(points[i][0],points[i][1]);
        }
        mPathMeasure=new PathMeasure(path,false);
    }
    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
    }
    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defaultDuration);
        valueAnimator.addUpdateListener(mUpdateListener);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOver=true;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(datas==null||datas.size()==0){
            return;
        }

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG));
        for (int i = 0; i < ySize; i++) {
            float eachy=(min+i*perPercent)/100f;
            String label=df.format(eachy)+"%";
            if(i==ySize-1){
                label=df.format(max/100f)+"%";
            }
//            float y=getPaddingTop()+perHeight*(ySize-i)-getPaddingBottom();
            float y=bottomY-perHeight*i;
//            Log.e(TAG, "onDraw: "+y+"#"+label );
            //绘制y坐标轴
            canvas.drawText(label,xVertical,y,labelYPaint);
            //绘制横向分割线
            canvas.drawLine(xVertical+xPadding,y,getWidth()-getPaddingRight(),y,xSeparatePaint);
        }

        for (int i = 0; i < xSize; i++) {
            String label="";
            if(isOneToOne){
                label=datas.get(i).date;
            }else{
                label=xLabels[i];
            }
            float x=i*eachWidth+xPadding+xVertical+eachWidth/2f;
//            float y=bottomY-perY*(datas.get(i).percent-min/100f);
//            Log.e(TAG, "onDraw: "+y+"#"+perY+"$"+bottomY+"$"+topY );

            //绘制x坐标轴
            canvas.drawText(label,x,getHeight()-getPaddingBottom()-labelXHeight*1.8f,labelXPaint);
        }
        //绘制曲线图
        if(playAnim){
            Path dst = new Path();
            //根据动画值从线段总长度不断截取绘制造成动画效果
            mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimatorValue, mPathMeasure.getLength(), dst, true);
            canvas.drawPath(dst, linePaint);

            if(fillAreaHasAnim){
                float currX=(points[datas.size()-1][0]-points[0][0]) * (1-mAnimatorValue)+points[0][0];
                if(isFillArea){
                    dst.lineTo(points[0][0],bottomY);
                    dst.lineTo(currX,bottomY);
                    dst.close();
                    canvas.drawPath(dst,fillPaint);
                }
            }
        }else{
            canvas.drawPath(path,linePaint);
        }

        if(isOver||!playAnim){
            if(isFillArea){
                Path pa=new Path(path);
                pa.lineTo(points[0][0],bottomY);
                pa.lineTo(points[datas.size()-1][0],bottomY);
                pa.close();
                canvas.drawPath(pa,fillPaint);
            }
            //最后一个点上面绘制文本
            Bitmap bg= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            float lastX=points[datas.size()-1][0];
            float lastY=points[datas.size()-1][1];
            canvas.drawBitmap(bg,lastX-bg.getWidth(),lastY-bg.getHeight(),new Paint());
            String tip=df.format(datas.get(datas.size()-1).percent)+"%";
            canvas.drawText(tip,lastX-labelValuePaint.measureText(tip)/2f,lastY-labelValueHeight/2f,labelValuePaint);

            float pointRadius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());
            if(drawPoints){
                for (float[] f:points) {
                    canvas.drawCircle(f[0],f[1],pointRadius,pointPaint);
                }
            }
        }

    }


    public Chart setFillArea(boolean fillArea) {
        isFillArea = fillArea;
        return this;
    }

    public Chart setPlayAnim(boolean playAnim) {
        this.playAnim = playAnim;
        return this;
    }

    public Chart setDrawPoints(boolean drawPoints) {
        this.drawPoints = drawPoints;
        return this;
    }

    public Chart setFillAreaHasAnim(boolean fillAreaHasAnim) {
        this.fillAreaHasAnim = fillAreaHasAnim;
        return this;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(datas!=null&&datas.size()>0){
            if(isInArea(event.getX(),event.getY())){
                Toast.makeText(getContext(),df.format(selectedValue.percent)+"%", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private boolean isInArea(float x,float y){
        float radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        for (int i = 0; i < points.length; i++) {
            if((x<=points[i][0]+radius)&&x>=(points[i][0]-radius)&&(y<=points[i][1]+radius)&&y>=(points[i][1]-radius)){
                selectedValue=datas.get(i);
                return true;
            }
        }
        return false;
    }
}
