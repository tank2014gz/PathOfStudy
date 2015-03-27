package com.example.db.slor.shadowlayout.shadow;

import android.graphics.Canvas;

import com.example.db.slor.shadowlayout.ZDepthParam;


public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);
    public void onDraw(Canvas canvas);
}
