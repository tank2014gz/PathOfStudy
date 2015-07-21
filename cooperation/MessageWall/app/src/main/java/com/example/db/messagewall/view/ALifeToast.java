package com.example.db.messagewall.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.support.android.designlibdemo.R;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by db on 7/8/15.
 */
public class ALifeToast extends LinearLayout {

    private final int ACTIONBAR_STATUSBAR_HEIGHT = (int) getResources().getDimension(R.dimen.actionbar_height);

    public final Context context;

    private final float mToastTranslationX = getResources().getDimension(R.dimen.alifetoast_translationX);

    private final float mToastHeight = getResources().getDimension(R.dimen.alifetoast_height);

    public static enum ToastType {
        COMMENT, MENTION, FOLLOWERS, SUCCESS, FAIL, NO_MESSAGE
    }

    public static final long LENGTH_SHORT = 2000;

    public static final long LENGTH_LONG = 5000;

    private final int DURATION_ANIMATION = 500;

    private static ALifeToast sALifeToast;

    private Queue<Runnable> mTaskQueue = new LinkedList<>();

    private ViewGroup.LayoutParams mParams;

    private ViewGroup mContentView;

    private ImageView mToastIcon;

    private TextView mToastText;

    private Activity mActivity;

    private boolean isToasting;

    private View mToastView;

    public ALifeToast(Context context) {

        super(context);
        this.context = context;

        mToastView = LayoutInflater.from(context).inflate(R.layout.alife_toast,null);
        mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mToastView.setLayoutParams(mParams);

        mToastIcon = (ImageView)mToastView.findViewById(R.id.img);
        mToastText = (TextView)mToastView.findViewById(R.id.text);

    }
    public static ALifeToast makeText(Activity activity, CharSequence text, ToastType toastType) {
        return makeText(activity, text, toastType, LENGTH_SHORT, null);
    }

    public static ALifeToast makeText(Activity activity, int stringRes, ToastType toastType) {
        return makeText(activity, String.valueOf(stringRes), toastType, LENGTH_SHORT, null);
    }

    public static ALifeToast makeText(Activity activity, CharSequence text, ToastType toastType,
                                      long duration) {
        return makeText(activity, text, toastType, duration, null);
    }

    public static ALifeToast makeText(Activity activity, ViewGroup contentView, CharSequence text,
                                      ToastType toastType, long duration) {
        return makeText(activity, contentView, text, toastType, duration, null);
    }

    public static ALifeToast makeText(Activity activity, int stringRes, ToastType toastType,
                                      long duration) {
        return makeText(activity, String.valueOf(stringRes), toastType, duration, null);
    }

    public static ALifeToast makeText(Activity activity, ViewGroup contentView, int stringRes,
                                      ToastType toastType, long duration) {
        return makeText(activity, contentView, String.valueOf(stringRes), toastType, duration,
                null);
    }

    public static ALifeToast makeText(Activity activity, int stringRes, ToastType toastType,
                                      long duration, OnToastClickListener toastClickListener) {
        return makeText(activity, String.valueOf(stringRes), toastType, duration,
                toastClickListener);
    }

    public static ALifeToast makeText(Activity activity, CharSequence text, ToastType toastType,
                                      long duration, OnToastClickListener toastClickListener) {
        return makeText(activity, null, text, toastType, duration, toastClickListener);
    }

    public static ALifeToast makeText(Activity activity, ViewGroup contentView, CharSequence text,
                                      ToastType toastType, long duration, OnToastClickListener toastClickListener) {
        if (sALifeToast == null) {
            if (activity == null) {
                return null;
            }
            sALifeToast = new ALifeToast(activity);
        }
        sALifeToast.setFuuboToast(activity, contentView, text, toastType, duration,
                toastClickListener);
        return sALifeToast;
    }
    public static void cancelAll() {
        if (null != sALifeToast) {
            sALifeToast.clearTaskQueue();
        }
    }

    private void setFuuboToast(Activity activity, ViewGroup contentView, CharSequence text,
                               ToastType toastType, long duration, final OnToastClickListener toastClickListener) {
        if (activity == null && contentView == null) {
            return;
        }
        mActivity = activity;

        if (contentView != null) {
            mContentView = contentView;
            mToastView.setPadding(
                    (int) getResources().getDimension(R.dimen.alifetoast_paddingLeft),
                    (int) getResources().getDimension(R.dimen.alifetoast_paddingTop), 0, 0);
        } else {
            mContentView = (ViewGroup) mActivity.findViewById(android.R.id.content);
            mToastView.setPadding(
                    (int) getResources().getDimension(R.dimen.alifetoast_paddingLeft),
                    ACTIONBAR_STATUSBAR_HEIGHT
                            + (int) getResources().getDimension(
                            R.dimen.alifetoast_paddingTop), 0, 0);
        }
        mToastText.setTextColor(getResources().getColor(R.color.actionbar0));

        mTaskQueue
                .offer(new ToastTask(mContentView, text, toastType, duration, toastClickListener));

            mToastView.findViewById(R.id.toast_container).setBackgroundResource(R.drawable.border_pressed);
    }

    public void show() {
        if (isToasting) {
            return;
        }
        isToasting = true;
        Runnable task = mTaskQueue.poll();
        if (task != null) {
            post(task);
        } else {
            isToasting = false;
        }
    }

    public void hide(ViewGroup contentView) {
        contentView.removeView(mToastView);
    }

    private void animateShow(final ViewGroup contentView, Drawable drawable, CharSequence text) {
        mToastIcon.setImageDrawable(drawable);
        mToastText.setText(text);
        ObjectAnimator show = ObjectAnimator.ofFloat(mToastView, "translationX",
                -mToastTranslationX, 0);
        show.setDuration(DURATION_ANIMATION);
        show.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                hide(contentView);
                contentView.addView(mToastView);
            }
        });
        show.start();
    }

    private void animateHide(final ViewGroup contentView, long delay) {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator hide = ObjectAnimator.ofFloat(mToastView, "translationX", 0,
                        -mToastTranslationX);
                hide.setDuration(DURATION_ANIMATION);
                hide.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isToasting = false;
                        hide(contentView);
                        show();
                    }
                });
                hide.start();
            }
        }, delay);
    }

    private void animateHide(final ViewGroup contentView) {
        ObjectAnimator hide = ObjectAnimator.ofFloat(mToastView, "translationX", 0,
                -mToastTranslationX);
        hide.setDuration(DURATION_ANIMATION);
        hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isToasting = false;
                hide(contentView);
                show();
            }
        });
        hide.start();
    }

    private void clearTaskQueue() {
        mTaskQueue.clear();
    }

    private class ToastTask implements Runnable {
        private ViewGroup mViewGroup;

        private CharSequence mText;

        private ToastType mToastType;

        private long mDuration;

        private Drawable mDrawable;

        private OnToastClickListener mToastClickListener;

        public ToastTask(ViewGroup viewGroup, CharSequence text, ToastType toastType,
                         long duration, OnToastClickListener toastClickListener) {
            mViewGroup = viewGroup;
            mText = text;
            mToastType = toastType;
            mDuration = duration;
            mToastClickListener = toastClickListener;
        }

        @Override
        public void run() {
            switch (mToastType) {
                case COMMENT:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
                case MENTION:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
                case FOLLOWERS:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
                case SUCCESS:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
                case FAIL:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
                case NO_MESSAGE:
                    mDrawable = getResources().getDrawable(R.drawable.cashier_tip_icon);
                    break;
            }
            if (null != mToastView.getParent()) {
                ((ViewGroup) mToastView.getParent()).removeView(mToastView);
            }
            animateShow(mViewGroup, mDrawable, mText);
            animateHide(mViewGroup, mDuration);
            mToastView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mToastClickListener != null) {
                        mToastClickListener.onClick();
                    }
                    animateHide(mViewGroup);
                }
            });
        }
    }

    public interface OnToastClickListener {
        void onClick();
    }
}
