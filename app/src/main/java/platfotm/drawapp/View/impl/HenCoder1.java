package platfotm.drawapp.View.impl;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import platfotm.drawapp.R;
import platfotm.drawapp.View.BaseView;

import static android.media.CamcorderProfile.get;


/**
 * @author Ly
 * @date 2017/12/26
 */

public class HenCoder1 extends BaseView {
    /**
     * 宽度 高度
     */
    private int mWidth, mHeight;
    /**
     * 最大半径
     */
    private float mMaxRadius;
    /**
     * 最大边数
     */
    private int mEdgeCount;
    /**
     * 多少层
     */
    private int mLoopCount;
    /**
     * 角度
     */
    private int mAngle;

    /**
     * 存放x，y坐标
     */
    private List<Float> mFloatListX, mFloatListY;
    /**
     * 每个点的缩放
     */
    private List<Float> mPointRateValue = new ArrayList<>();
    /**
     * 每个点的名字
     */
    private List<String> mPointName = new ArrayList<>();

    /**
     * 画区域的画笔
     */
    private Paint mPaintForDrawArea;
    /**
     * 画点的画笔
     */
    private Paint mPaintForDrawAreaPoint;
    /**
     * 写字的画笔
     */
    private Paint mPaintForDrawText;


    private int mTextColor, mPointColor, mAreaColor, mEdgeColor;
    private float mTextSize;


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HenCoder1);
        mTextSize = typedArray.getDimension(R.styleable.HenCoder1_textSize, 30);
        mTextColor = typedArray.getColor(R.styleable.HenCoder1_textColor, Color.parseColor("#010101"));
        mAreaColor = typedArray.getColor(R.styleable.HenCoder1_areaColor, Color.parseColor("#d7f2ff"));
        mPointColor = typedArray.getColor(R.styleable.HenCoder1_pointColor, Color.parseColor("#87ddfd"));
        mEdgeColor = typedArray.getColor(R.styleable.HenCoder1_edgeColor, Color.parseColor("#87ddfd"));
        mEdgeCount = typedArray.getInt(R.styleable.HenCoder1_edgeCount, 6);
        mLoopCount = typedArray.getInt(R.styleable.HenCoder1_loopCount, 6);
        typedArray.recycle();
        initTools();
    }

    /**
     * 初始化工具
     */
    private void initTools() {
        mPaintForDrawArea = new Paint();
        mPaintForDrawArea.setAntiAlias(true);
        mPaintForDrawArea.setColor(mAreaColor);
        mPaintForDrawArea.setStyle(Paint.Style.FILL);

        mPaintForDrawAreaPoint = new Paint();
        mPaintForDrawAreaPoint.setAntiAlias(true);
        mPaintForDrawAreaPoint.setColor(mPointColor);

        mPaintForDrawText = new Paint();
        mPaintForDrawText.setTextSize(mTextSize);
        mPaintForDrawText.setAntiAlias(true);
        mPaintForDrawText.setColor(mTextColor);
        mAngle = 360 / mEdgeCount;
    }

    public void setPointRateValue(List<Float> pointRateValue) {
        mPointRateValue = pointRateValue;
        invalidate();
    }

    public void setPointName(List<String> pointName) {
        mPointName = pointName;
        invalidate();
    }

    public HenCoder1(Context context) {
        super(context);
    }

    public HenCoder1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);
    }


    public HenCoder1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mMaxRadius = (float) ((mWidth / 2) * 0.8);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        calculatePoint();
        drawRectangleLine(canvas);
        drawLineToTopPoint(canvas);
        drawAreaToPoint(canvas);
        drawText(canvas);
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < mPointName.size(); i++) {
            float currentAngle = mAngle * i;
            if (currentAngle == 180) {
                float x = mFloatListX.get(i) * 1.05f;
                float y = mFloatListY.get(i) * 1.05f;
                canvas.drawText(mPointName.get(i), x - (mPaintForDrawText.getTextSize() / 4)
                        * (mPointName.get(i).length()), y + mPaintForDrawText.getTextSize()
                        / 4 * (mPointName.get(i).length()), mPaintForDrawText);
            } else {
                float x = mFloatListX.get(0) * 1.05f;
                float y = mFloatListY.get(0) * 1.05f;
                canvas.save();
                canvas.rotate(currentAngle);
                canvas.drawText(mPointName.get(i), x - (mPaintForDrawText.getTextSize() / 4)
                        * mPointName.get(i).length(), y, mPaintForDrawText);
                canvas.restore();
            }
        }
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawAreaToPoint(Canvas canvas) {
        Path path = new Path();
        List<Float> floatXList = new ArrayList<>();
        List<Float> floatYList = new ArrayList<>();
        for (int i = 0; i < mEdgeCount; i++) {
            float x = mFloatListX.get(i) * mPointRateValue.get(i);
            float y = mFloatListY.get(i) * mPointRateValue.get(i);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            floatXList.add(x);
            floatYList.add(y);

        }
        mPaintForDrawArea.setStrokeWidth(3);
        mPaintForDrawArea.setColor(mEdgeColor);
        mPaintForDrawArea.setStyle(Paint.Style.STROKE);
        path.close();
        canvas.drawPath(path, mPaintForDrawArea);
        mPaintForDrawArea.setColor(mAreaColor);
        mPaintForDrawArea.setStyle(Paint.Style.FILL);
        mPaintForDrawArea.setAlpha(200);
        path.close();
        canvas.drawPath(path, mPaintForDrawArea);
        for (int i = 0; i < mEdgeCount; i++) {
            canvas.drawCircle(floatXList.get(i), floatYList.get(i), 10, mPaintForDrawAreaPoint);
        }
    }

    /**
     * 从中心画线到各个定点
     *
     * @param canvas
     */
    private void drawLineToTopPoint(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mEdgeCount; i++) {
            path.reset();
            path.lineTo(mFloatListX.get(i), mFloatListY.get(i));
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制多边形的线
     *
     * @param canvas
     */
    private void drawRectangleLine(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mLoopCount; i++) {
            path.reset();
            float rate = computeRate(i + 1, mLoopCount);
            for (int j = 0; j < mEdgeCount; j++) {
                float currentX = mFloatListX.get(j) * rate;
                float currentY = mFloatListY.get(j) * rate;
                if (j == 0) {
                    path.moveTo(currentX, currentY);
                } else {
                    path.lineTo(currentX, currentY);
                }
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 计算缩放
     *
     * @param value
     * @param max
     * @return
     */
    private float computeRate(float value, float max) {
        return value / max;
    }

    /**
     * 计算点
     */
    private void calculatePoint() {
        mFloatListX = new ArrayList<>();
        mFloatListY = new ArrayList<>();
        for (int i = 0; i < mEdgeCount; i++) {
            float currentAngle = i * mAngle - 90;
            float currentX = (float) (mMaxRadius * Math.cos((currentAngle / 180) * Math.PI));
            float currentY = (float) (mMaxRadius * Math.sin((currentAngle / 180) * Math.PI));
            mFloatListX.add(currentX);
            mFloatListY.add(currentY);
        }
    }

    /**
     * 用属性动画绘制组件
     */
    public void draw() {
        final Float[] trueValues = mPointRateValue.toArray(new Float[mPointRateValue.size()]);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = animation.getAnimatedFraction();
                for (int i = 0; i < mPointRateValue.size(); i++) {
                    mPointRateValue.set(i, trueValues[i] * rate);
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }


}
