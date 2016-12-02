package com.scnu.zhou.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by zhou on 16/11/22.
 */
public class LineGraph extends View {

    // 默认长宽，在设置wrap_content时使用
    private int mWidth = 400;
    private int mHeight = 400;

    private float maxValue, minValue;
    private List<GraphData> mData;

    private Paint mLinePaint;    // 坐标系画笔
    private Paint mArrowPaint;
    private Paint mTextPaint;   // 文本画笔
    private Paint mPointPaint;   // 数据点画笔

    private int lineColor = Color.parseColor("#2680ef");

    private int mOriginX = 50, mOriginY = 0;
    private int xDistance, yDistance;
    private int yValue;

    private Point[] points;

    public LineGraph(Context context) {
        this(context, null);
    }

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineGraph);
            lineColor = a.getColor(R.styleable.LineGraph_lineColor, Color.parseColor("#2680ef"));
            a.recycle();
        }
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setColor(Color.parseColor("#333333"));

        mArrowPaint = new Paint();
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setColor(Color.parseColor("#333333"));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextSize(16);

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(lineColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth, mHeight);
        }
        else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth, heightSpecSize);
        }
        else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, mHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateVaule();

        drawCoordinateSystem(canvas);
        drawText(canvas);
        drawDataPoint(canvas);
        drawDataLine(canvas);
    }


    /**
     * 计算相关数值
     */
    private void calculateVaule(){

        Log.e("minValue", minValue+"");
        yValue = (int)(maxValue-minValue)/mData.size();
        yDistance = mHeight/(mData.size()+3);

        if (minValue < 0){
            mOriginY = (int)(mHeight - 50 + (minValue/yValue * yDistance));
        }
        else{
            mOriginY = mHeight - 50;
        }

        xDistance = (mWidth - mOriginX)/(mData.size()+1);

        Log.e("yDistance", yDistance+"");
        Log.e("yValue", yValue+"");
    }

    /**
     * 绘制坐标系
     * @param canvas
     */
    private void drawCoordinateSystem(Canvas canvas){

        Path path = new Path();

        path.moveTo(0, mOriginY);
        path.lineTo(mWidth, mOriginY);    // 横坐标

        path.moveTo(mOriginX, mHeight);
        path.lineTo(mOriginX, 0);     // 纵坐标

        canvas.drawPath(path, mLinePaint);

        path.reset();

        path.moveTo(mWidth - 14, mOriginY - 8);
        path.lineTo(mWidth, mOriginY);
        path.lineTo(mWidth - 14, mOriginY + 8);
        path.close();    // 右箭头

        path.moveTo(mOriginX - 8, 14);
        path.lineTo(mOriginX, 0);
        path.lineTo(mOriginX + 8, 14);   // 上箭头
        path.close();

        canvas.drawPath(path, mArrowPaint);


        path.reset();      // 横坐标数据点
        for (int i=1; i<=mData.size(); i++){

            points[i-1].x = mOriginX + xDistance * i;
            path.moveTo(mOriginX + xDistance * i, mOriginY);
            path.lineTo(mOriginX + xDistance * i, mOriginY - 5);
        }
        canvas.drawPath(path, mLinePaint);


        path.reset();     // 纵坐标数据点

        int degree = mOriginY / yDistance;
        for (int i=0; i<=degree; i++){

            path.moveTo(mOriginX, mOriginY - yDistance * i);
            path.lineTo(mOriginX + 5, mOriginY - yDistance * i);
        }
        degree = (mHeight - mOriginY) / yDistance;
        for (int i=0; i<degree; i++){

            path.moveTo(mOriginX, mOriginY + yDistance * i);
            path.lineTo(mOriginX + 5, mOriginY + yDistance * i);
        }
        canvas.drawPath(path, mLinePaint);

    }


    /**
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas){

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;

        for (int i=0; i<mData.size(); i++){

            float x = points[i].x - mTextPaint.measureText(mData.get(i).getTitle())/2;
            float y = mOriginY + fontHeight + 5;
            canvas.drawText(mData.get(i).getTitle(), x, y, mTextPaint);
        }

        int degree = mOriginY / yDistance;
        for (int i=0; i<degree; i++){

            float m = mOriginX - mTextPaint.measureText(maxValue + "");
            float n = mOriginY - yDistance * (i + 1) + 5;
            canvas.drawText(yValue*(i+1) + "", m, n, mTextPaint);
        }
        degree = (mHeight - mOriginY) / yDistance;
        for (int i=0; i<degree-1; i++){

            float m = mOriginX - mTextPaint.measureText(maxValue + "");
            float n = mOriginY + yDistance * (i + 1) + 5;
            canvas.drawText(-yValue*(i+1) + "", m, n, mTextPaint);
        }
    }


    /**
     * 绘制数据点
     * @param canvas
     */
    private void drawDataPoint(Canvas canvas){

        mPointPaint.setStyle(Paint.Style.FILL);

        for (int i=0; i<points.length; i++){

            points[i].y = mOriginY - (mData.get(i).getValue()/yValue) * yDistance;
            canvas.drawCircle(points[i].x, points[i].y, 4, mPointPaint);
        }
    }


    /**
     * 绘制数据折线
     * @param canvas
     */
    private void drawDataLine(Canvas canvas){

        mPointPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(points[0].x, points[0].y);

        for (int i=1; i<points.length; i++){
            path.lineTo(points[i].x, points[i].y);
        }

        mPointPaint.setAlpha(187);
        canvas.drawPath(path, mPointPaint);
    }


    /**
     * 设置
     * @param mData
     */
    public void setGraphData(List<GraphData> mData){

        this.mData = mData;
        this.points = new Point[mData.size()];

        for (int i=0; i<mData.size(); i++){    // 统计最大值与最小值

            if (mData.get(i).getValue() > maxValue){
                maxValue = mData.get(i).getValue();
            }

            if (mData.get(i).getValue() < minValue){
                minValue = mData.get(i).getValue();
            }

            points[i] = new Point();
        }

        postInvalidate();
    }


    public static class GraphData{

        private String title;
        private float value;

        public GraphData(String title, float value){

            this.title = title;
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    private class Point{

        public float x;
        public float y;
    }
}
