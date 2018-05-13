package com.hezo.zhangtong.piechart;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class Data {
    private static JSONArray pieData;

    public static String getPieData() throws JSONException {
        if (pieData == null) {
            synchronized (Data.class) {
                if (pieData == null) {
                    pieData = new JSONArray();
                    HashMap<String, Integer> map1 = new HashMap<>();
                    map1.put("外卖", 34);
                    map1.put("娱乐", 21);
                    map1.put("其他", 45);
                    pieData.put(getItem("2018年1月", map1));

                    HashMap<String, Integer> map2 = new HashMap<>();
                    map2.put("外卖", 42);
                    map2.put("娱乐", 65);
                    map2.put("其他", 12);
                    pieData.put(getItem("2018年2月", map2));

                    HashMap<String, Integer> map3 = new HashMap<>();
                    map3.put("外卖", 34);
                    map3.put("娱乐", 123);
                    map3.put("其他", 24);
                    pieData.put(getItem("2018年3月", map3));

                    HashMap<String, Integer> map4 = new HashMap<>();
                    map4.put("外卖", 56);
                    map4.put("娱乐", 45);
                    map4.put("其他", 90);
                    pieData.put(getItem("2018年4月", map4));
                }
            }
        }

        return pieData.toString();
    }


    @SuppressLint("NewApi")
    private static JSONObject getItem(String date, HashMap<String, Integer> map) throws JSONException {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        Set<String> set = map.keySet();
        String[] keys = new String[set.size()];
        set.toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            JSONObject object = new JSONObject();
            object.put("title", keys[i]);
            object.put("value", map.get(keys[i]));
            arr.put(object);
        }
        obj.put("date", date);
        obj.put("obj", arr);
        return obj;
    }


}
