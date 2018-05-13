package com.hezo.zhangtong.piechart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PieFragment extends Fragment implements OnChartValueSelectedListener {

    private static final String DATA_KEY = "DATA_KEY";
    private MonthBean mData;
    private PieChart mChart;
    private TextView tvDes;

    public static PieFragment newInstance(MonthBean data) {
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mData = args.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_pie, null);
        mChart = inflate.findViewById(R.id.pc_chart);
        tvDes = inflate.findViewById(R.id.tv_des);
        initView();

        return inflate;
    }

    private void initView() {
        setData();
        mChart.setCenterText(getCenterText());
        mChart.setDrawEntryLabels(false);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.setRotationEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
    }

    private CharSequence getCenterText() {

        String centerText = String.format("总支出\n%s元", mData.getSum());
        SpannableString s = new SpannableString(centerText);
        s.setSpan(new ForegroundColorSpan(Color.rgb(178, 178, 178)),
                0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        s.setSpan(new AbsoluteSizeSpan(64, true), 3,
                centerText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return s;
    }

    private void setData() {

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < mData.getObj().size(); i++) {
            entries.add(new PieEntry(mData.getObj().get(i).getValue(), mData.getObj().get(i).getTitle()));
        }
        PieDataSet dataSet = new PieDataSet(entries, mData.getDate());
        dataSet.setColors(Color.rgb(216, 77, 79)
                , Color.rgb(183, 56, 63), Color.rgb(247, 85, 47));
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(0);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(22);
        mChart.setData(pieData);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float proportion = 360f / mData.getSum();
        float sum = 0;
        int index = (int) h.getX();
        for (int i = 0; i < index; i++) {
            sum += mData.getObj().get(i).getValue();
        }
        sum += mData.getObj().get(index).getValue() / 2;
        float angle = 90 - sum * proportion;
        mChart.setRotationAngle(angle);

        upDataDesText(index);
    }

    private void upDataDesText(int index) {

        tvDes.setText(String.format("%s:%s", mData.getObj().get(index).getTitle(),
                mData.getObj().get(index).getValue()));

    }

    @Override
    public void onNothingSelected() {

    }
}
