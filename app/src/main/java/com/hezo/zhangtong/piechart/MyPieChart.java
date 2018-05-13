package com.hezo.zhangtong.piechart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;

public class MyPieChart extends PieChart {
    public MyPieChart(Context context) {
        super(context);
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(min, min);
    }
}
