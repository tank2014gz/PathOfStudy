package com.example.db.messagewall.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;

import java.util.Collection;

/**
 * Created by db on 3/6/15.
 */
public class MyViewFinderView extends View {
    private static final String TAG = "log";
    /**
     * 刷新界面的时间
     */
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;
    /**
     * 四个绿色边角对应的长度
     */
    private int ScreenRate;

    /**
     * 四个绿色边角对应的宽度
     */
    private static final int CORNER_WIDTH = 10;
    /**
     * 扫描框中的中间线的宽度
     */
    private static final int MIDDLE_LINE_WIDTH = 6;

    /**
     * 扫描框中的中间线的与扫描框左右的间隙
     */
    private static final int MIDDLE_LINE_PADDING = 5;

    /**
     * 中间那条线每次刷新移动的距离
     */
    private static final int SPEEN_DISTANCE = 5;
    /**
     * 手机的屏幕密度
     */
    private static float density;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 16;
    /**
     * 字体距离扫描框下面的距离
     */
    private static final int TEXT_PADDING_TOP = 30;

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 中间滑动线的最顶端位置
     */
    private int slideTop;

    /**
     * 中间滑动线的最底端位置
     */
    private int slideBottom;
    private Bitmap resultBitmap;
    private final int maskColor=0;
    private final int resultColor=0;

    private final int resultPointColor=0;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;
    boolean isFirst;

    public MyViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        //将像素转换成dp
        ScreenRate = (int)(20 * density);


    }
}
