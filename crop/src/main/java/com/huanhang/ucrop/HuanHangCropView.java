package com.huanhang.ucrop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;

/**
 * Created by Cao-Human on 2017/4/11
 */

public class HuanHangCropView extends OverlayView {
    public HuanHangCropView(Context context) {
        super(context);
    }

    public HuanHangCropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HuanHangCropView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchEnableInner || getCurrentTouchIndex(event.getX(), event.getY()) < 4) {
            return super.onTouchEvent(event);
        }
        // 内部手势不可用，手势不处于四角则不处理
        return false;
    }

    private boolean isTouchEnableInner = true;

    public void setTouchEnableInner(boolean enable) {
        isTouchEnableInner = enable;
    }

    public void onRotateRight(GestureCropImageView view) {
        float originWidth = mCropViewRect.width();
        float originHeight = mCropViewRect.height();
        float originCenterX = mCropViewRect.centerX();
        float originCenterY = mCropViewRect.centerY();

        mTargetAspectRatio = mCropViewRect.height() / originWidth;
        int height = (int) (mThisWidth / mTargetAspectRatio);
        if (height > mThisHeight) {
            int width = (int) (mThisHeight * mTargetAspectRatio);
            int halfDiff = (mThisWidth - width) / 2;
            mCropViewRect.set(getPaddingLeft() + halfDiff, getPaddingTop(),
                    getPaddingLeft() + width + halfDiff, getPaddingTop() + mThisHeight);
        } else {
            int halfDiff = (mThisHeight - height) / 2;
            mCropViewRect.set(getPaddingLeft(), getPaddingTop() + halfDiff,
                    getPaddingLeft() + mThisWidth, getPaddingTop() + height + halfDiff);
        }

        view.postScale(mCropViewRect.width() / originHeight);
        view.postTranslate((int) (mCropViewRect.centerX() - originCenterX), (int) (mCropViewRect.centerY() - originCenterY));
        updateGridPoints();
        postInvalidate();
    }
}