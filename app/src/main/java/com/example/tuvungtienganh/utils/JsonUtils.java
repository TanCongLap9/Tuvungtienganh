package com.example.tuvungtienganh.utils;

import android.content.Context;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.models.ChuDeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonUtils {
    private static List<ChuDeModel> sInstance;
    public static List<ChuDeModel> getAll(Context context) throws JSONException {
        if (sInstance != null) return new ArrayList<>(sInstance);
        InputStream inputStream = context.getResources().openRawResource(R.raw.tdta);
        Scanner sc = new Scanner(inputStream).useDelimiter("\\A");
        String jsonStr = sc.next();
        JSONArray cacChuDeJson = new JSONArray(jsonStr);
        sInstance = new ArrayList<>();
        for (int i = 0; i < cacChuDeJson.length(); i++) {
            JSONObject chuDeJson = cacChuDeJson.getJSONObject(i);
            int maDinhDanhCuaChuDe = chuDeJson.getInt("id");
            String tenChuDe = chuDeJson.getString("name");
            JSONArray cacCauJson = chuDeJson.getJSONArray("sentences");
            List<CauModel> cacCau = new ArrayList<>();
            for (int j = 0; j < cacCauJson.length(); j++) {
                JSONObject cauJson = cacCauJson.getJSONObject(j);
                cacCau.add(new CauModel(
                        cauJson.getString("english"),
                        cauJson.getString("vietnamese"),
                        cauJson.getInt("id"),
                        maDinhDanhCuaChuDe
                ));
            }
            sInstance.add(new ChuDeModel(tenChuDe, cacCau, maDinhDanhCuaChuDe));
        }
        return new ArrayList<>(sInstance);
    }
}
