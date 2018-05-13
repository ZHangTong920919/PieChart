package com.hezo.zhangtong.piechart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vpMain;
    private ArrayList<MonthBean> mData;

    private Button mBtnPre, mBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpMain = findViewById(R.id.vp_main);
        mBtnPre = findViewById(R.id.btn_pre);
        mBtnPre.setOnClickListener(this);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initData() throws JSONException {
        Gson gson = new Gson();
        mData = gson.fromJson(Data.getPieData(), new TypeToken<ArrayList<MonthBean>>() {
        }.getType());
    }

    private void initView() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
        upDateJumpText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                if (vpMain.getCurrentItem() != 0) {
                    vpMain.setCurrentItem(vpMain.getCurrentItem() - 1);
                }
                break;
            case R.id.btn_next:
                if (vpMain.getCurrentItem() != Objects.requireNonNull(vpMain.getAdapter()).getCount() - 1) {
                    vpMain.setCurrentItem(vpMain.getCurrentItem() + 1);
                }
                break;
            default:
                break;
        }

        upDateJumpText();

    }

    private void upDateJumpText() {
        if (vpMain.getCurrentItem() != 0) {
            mBtnPre.setText(handlerText(mData.get(vpMain.getCurrentItem() - 1).getDate()));
        } else {
            mBtnPre.setText("没有了！");
        }
        if (vpMain.getCurrentItem() != Objects.requireNonNull(vpMain.getAdapter()).getCount() - 1) {
            mBtnNext.setText(handlerText(mData.get(vpMain.getCurrentItem() + 1).getDate()));
        } else {
            mBtnNext.setText("没有了！");
        }
    }

    private String handlerText(String date) {
        return date.substring(date.indexOf("年") + 1);
    }
}
