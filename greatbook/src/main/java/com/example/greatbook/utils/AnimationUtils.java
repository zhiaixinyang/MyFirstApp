package com.example.greatbook.utils;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by MBENBEN on 2016/11/3.
 */

public class AnimationUtils {

    public static void giftAnimation(final RelativeLayout rootView, final ImageView view, Context context) {
        int[] parent = new int[2];
        rootView.getLocationInWindow(parent);
        final int[] pointPosition=new int[2];
        view.getLocationInWindow(pointPosition);
        final ImageView iv=new ImageView(context);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        params.leftMargin=pointPosition[0];
        params.topMargin=pointPosition[1];
        iv.setImageDrawable(view.getDrawable());
        rootView.addView(iv,params);


        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {
            //此方法会在每一帧刷新时，调用。
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                //fraction,从0-1逐渐增加。
                PointF point = new PointF();
                if (fraction>0&&fraction<0.25){
                    point.x = -100*fraction;
                    iv.setAlpha(fraction);
                }else if(fraction>0.25&&fraction<0.5){
                    point.x = 100*fraction-50;
                    iv.setAlpha(fraction);
                }else if(fraction>0.5&&fraction<0.75){
                    point.x = 100*fraction-50;
                    iv.setAlpha(fraction);
                }else if (fraction>0.75){
                    iv.setAlpha(fraction);
                    point.x = -100*fraction+100;
                }
                point.y = -300 * fraction;
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                iv.setTranslationX(point.x);
                iv.setTranslationY(point.y);

            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.removeView(iv);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }
}
