package com.zmm.dashboardview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.zmm.dashboardview.R;

/**
 * Description: 自定义仪表盘
 * Author:zhangmengmeng
 * Date:2017/9/28
 * Time:上午10:50
 */
public class DashboardView extends View {
    private int mWidth;
    private int mHeight;

    private int mPercent;

    //刻度宽度
    private float mTikeWidth;

    //第二个弧的宽度
    private int mScendArcWidth;

    //文字矩形的宽
    private int mRectWidth;

    //文字矩形的高
    private int mRectHeight;

    //文字类型
    private String mTextType = "";

    //文字单位
    private String mTextUnit = "";

    //文字数值
    private String mTextValue = "";

    //文字的大小
    private int mTextSize;

    //设置文字颜色
    private int mTextColor;
    //刻度颜色
    private int mArcColor;
    //底部粗弧颜色
    private int mRadianColorIn;
    //上部粗弧颜色
    private int mRadianColorOut;


    //渐变颜色
    private int[] doughnutColors = new int[]{Color.GREEN, Color.YELLOW, Color.RED};

    //刻度的个数
    private int mTikeCount;

    private Context mContext;
    private SweepGradient mSweepGradient;
    private Matrix mMatrix;
    private BitmapFactory.Options mOptions;
    private Bitmap mBitmap;
    private Paint mPaint;

    public DashboardView(Context context) {
        this(context, null);

    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardView,defStyleAttr,0);
        mArcColor = a.getColor(R.styleable.DashboardView_arcColor, getResources().getColor(R.color.colorArc));
        mRadianColorIn = a.getColor(R.styleable.DashboardView_radianColorIn, getResources().getColor(R.color.colorIn));
        mRadianColorOut = a.getColor(R.styleable.DashboardView_radianColorOut, getResources().getColor(R.color.colorOut));
        mTikeCount = a.getInt(R.styleable.DashboardView_tikeCount,10);
        mTextSize = a.getDimensionPixelSize(PxUtils.spToPx(R.styleable.DashboardView_android_textSize,mContext),32);
        mTextType = a.getString(R.styleable.DashboardView_textType);
        mTextUnit = a.getString(R.styleable.DashboardView_textUnit);
        mTextValue = a.getString(R.styleable.DashboardView_textValue);
        System.out.println("type = "+mTextType+",unit = "+mTextUnit+",value = "+mTextValue);
        mScendArcWidth = 50;
        a.recycle();

        //对图片进行缩放
        mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bpmeter_heart_icon, mOptions);
        int be = (int)(mOptions.outHeight / (float)20);

        mOptions.inJustDecodeBounds = false;
        mOptions.inSampleSize = be;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bpmeter_heart_icon, mOptions);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else {
            mWidth = PxUtils.dpToPx(280,mContext);
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            mHeight = PxUtils.dpToPx(280,mContext);
        }
        System.out.println("width = "+mWidth+",height = "+mHeight);
        setMeasuredDimension(mWidth, mHeight);

        //因为渐变颜色，默认是从0，也就是3点开始，所以顺时针旋转90度，从6点开始进行渐变
        mSweepGradient = new SweepGradient(mWidth/2, mHeight/2, doughnutColors, null);
        mMatrix = new Matrix();
        mMatrix.setRotate(90, mWidth/2, mHeight/2);
        mSweepGradient.setLocalMatrix(mMatrix);
        mPaint = new Paint();

    }


    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.reset();
        int strokeWidth = 3;
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRadianColorOut);
        mPaint.setStrokeWidth(mScendArcWidth);

        RectF secondRectF = new RectF(strokeWidth + 50, strokeWidth + 50, mWidth - strokeWidth - 50, mHeight - strokeWidth - 50);
        float percent = mPercent / 100f;

        //充满的圆弧的度数    -5是大小弧的偏差
        float fill = 250 * percent ;

        //空的圆弧的度数
        float empty = 250 - fill;

        //画外部分粗弧
//        p.setColor(ColorUtils.getCurrentColor(mPercent/100.0f,doughnutColors));

        mPaint.setShader(mSweepGradient);

        if(percent != 0){
            canvas.drawArc(secondRectF, 145, fill, false, mPaint);
        }

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mScendArcWidth);
        //内部分圆弧颜色
//        if(percent==0){
//            p.setColor(Color.WHITE);
//            canvas.drawArc(secondRectF, 145, fill, false, p);
//        }
        //内部分圆弧颜色
        mPaint.setColor(mRadianColorIn);
        //画弧胡的未充满部分
        canvas.drawArc(secondRectF, 145 + fill, empty, false, mPaint);

        //---------------------------------------------------
        int offsetX = 2;
        //决定刻度，结束Y坐标
        int startWidthY = 100;

        //绘制刻度！
        mPaint.setColor(mArcColor);
        //绘制第一条最上面的刻度

        //决定刻度，起始Y坐标
        mTikeWidth = 80;
        mPaint.setStrokeWidth(3);
        canvas.drawLine(mWidth / offsetX, startWidthY, mWidth / offsetX, mTikeWidth, mPaint);

        //旋转的角度
        float rAngle = 250f / mTikeCount;


        //通过旋转画布 绘制右面的刻度
        for (int i = 0; i < mTikeCount / 2; i++) {
            canvas.rotate(rAngle, mWidth / offsetX, mHeight / offsetX);
            canvas.drawLine(mWidth / offsetX, startWidthY, mWidth / offsetX, mTikeWidth, mPaint);
        }

        //现在需要将将画布旋转回来
        canvas.rotate(-rAngle * mTikeCount / 2, mWidth / offsetX, mHeight / offsetX);

        //通过旋转画布 绘制左面的刻度
        for (int i = 0; i < mTikeCount / 2; i++) {
            canvas.rotate(-rAngle, mWidth / offsetX, mHeight / offsetX);
            canvas.drawLine(mWidth / offsetX, startWidthY, mWidth / offsetX, mTikeWidth, mPaint);
        }

        //现在需要将将画布旋转回来
        canvas.rotate(rAngle * mTikeCount / 2, mWidth / 2, mHeight / 2);


        //---------------------------------------------------
        //绘制矩形
        mPaint.setStyle(Paint.Style.FILL);
//        p.setColor(Color.WHITE);
        mRectWidth = 100;
        mRectHeight = 300;
        //距离上面高度  加上了刻度线高度
        float topDis = 60+mTikeWidth;

        //文字矩形的最底部坐标
//        canvas.drawRect(mWidth/2-mRectWidth/2,topDis,mWidth/2+mRectWidth/2,mRectHeight+topDis,p);


        canvas.drawBitmap(mBitmap,(mWidth-mBitmap.getWidth())/2,topDis+20, mPaint);

        mTextSize = 32;
        mTextColor = getResources().getColor(R.color.colorTextType);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        float textType = mPaint.measureText(mTextType);
        canvas.drawText(mTextType,(mWidth-textType)/2,topDis+120, mPaint);

        mTextSize = 85;
        mTextColor = getResources().getColor(R.color.colorTextValue);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        float textValue = mPaint.measureText(mTextValue);
        canvas.drawText(mTextValue,(mWidth-textValue)/2,topDis+220, mPaint);

        mTextSize = 47;
        mTextColor = getResources().getColor(R.color.colorTextType);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        float textUnit = mPaint.measureText(mTextUnit);
        canvas.drawText(mTextUnit,(mWidth-textUnit)/2,topDis+mRectHeight, mPaint);

        super.onDraw(canvas);
    }


    /**
     * 设置百分比
     * @param percent
     */
    public void setPercent(int percent) {
        mPercent = percent;
        invalidate();
    }

    public void setPowerValue(float value){
        mTextValue = value+"";
        invalidate();
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(String text){
        mTextType = text;
        invalidate();
    }

    /**
     * 设置圆弧颜色
     * @param color
     */

    public void setArcColor(int color){
        mArcColor = color;

        invalidate();
    }


    /**
     * 设置文字大小
     * @param size
     */
    public void setTextSize(int size){
        mTextSize = size;

        invalidate();
    }

    /**
     * 设置粗弧的宽度
     * @param width
     */
    public void setArcWidth(int width){
        mScendArcWidth = width;

        invalidate();
    }
}
