package com.example.masir.utility.util;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by alirezarabiei on 5/7/15.
 */
public class AnimationUtil {


    public static int getCenterX(View v) {
        return (v.getLeft() + v.getRight()) / 2;
    }

    public static int getCenterY(View v) {
        return (v.getTop() + v.getBottom()) / 2;
    }


    public static int getRelativeX(View v, float r) {
        return (int)(v.getLeft() + ( v.getRight() - v.getLeft()) * r);
    }

    public static int getRelativeY(View v, float r) {
        return (int)(v.getTop()+( v.getBottom()-v.getTop()) * r);
    }


    public static synchronized void runRippleAnimFromSelf(final View target, final View position, final float x, final float y, final boolean isReverse, final Animator.AnimatorListener listener) {

        runRippleAnim(target, getRelativeX(position,x), getRelativeY(position,y), isReverse, listener);
    }

    public static synchronized void runRippleAnim(final View target, final View position, final boolean isReverse, final Animator.AnimatorListener listener) {

        runRippleAnim(target, getCenterX(position), getCenterY(position), isReverse, listener);
    }

    public static final long defaultDuration = 500;

    public static synchronized void runRippleAnim(final View target, final int cx, final int cy, final boolean isReverse, final Animator.AnimatorListener listener) {


        int sdk = Build.VERSION.SDK_INT;

        if (sdk >= Build.VERSION_CODES.LOLLIPOP) {
            target.post(new Runnable() {
                @SuppressLint("SuspiciousIndentation")
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {

                    int finalRadius = Math.max(target.getWidth(), target.getHeight());
                    final Animator animator =
                            isReverse ?
                                    ViewAnimationUtils.createCircularReveal(target, cx, cy, finalRadius, 0) :
                                    ViewAnimationUtils.createCircularReveal(target, cx, cy, 0, finalRadius);
                    animator.setDuration(defaultDuration);
                    if(listener!=null)
                    animator.addListener(listener);
                    animator.start();
                }
            });
        } else {
            AnimationSet anim = new AnimationSet(true);
            //anim.setFillEnabled(true);
            if (isReverse) {
                anim.addAnimation(new ScaleAnimation(
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        Animation.ABSOLUTE, cx,
                        Animation.RELATIVE_TO_SELF, 0));
/*
                anim.addAnimation( new RotateAnimation(
                        0.0f,
                        90.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f) );*/
            } else {
                anim.addAnimation(new ScaleAnimation(
                        1.0f, 1.0f,
                        0.0f, 1.0f,
                        Animation.ABSOLUTE, cx,
                        Animation.RELATIVE_TO_SELF, 0));
/*
                anim.addAnimation( new RotateAnimation(
                        90.0f,
                        0.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f) );*/
            }

            anim.setDuration(defaultDuration);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if(listener!=null)
                        listener.onAnimationStart(null);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(listener!=null)
                        listener.onAnimationEnd(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if(listener!=null)
                        listener.onAnimationRepeat(null);
                }
            });
            target.startAnimation(anim);
        }
    }




    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.MATCH_PARENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }



}
