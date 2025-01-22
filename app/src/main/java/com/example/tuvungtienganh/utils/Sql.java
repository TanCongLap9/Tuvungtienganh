package com.example.tuvungtienganh.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.models.ChuDeModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Sql extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TuVungGiaoTiepAnhViet";
    private Context mContext;
    public Sql(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
        this.mContext = context;
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS CacCauDaThich (ChuDe int, Cau int, ThoiGian int, PRIMARY KEY (ChuDe, Cau))");
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS LichSuTraCuu (ChuDe int, Cau int, ThoiGian int, PRIMARY KEY (ChuDe, Cau))");
    }

    public void thichCau(CauModel cau) {
        getWritableDatabase().execSQL(
                "INSERT OR REPLACE INTO CacCauDaThich VALUES (?, ?, ?)",
                new Object[] {cau.getMaDinhDanhCuaChuDe(), cau.getMaDinhDanh(), System.currentTimeMillis()});
    }

    public void hongThichCau(CauModel cau) {
        getWritableDatabase().execSQL(
                "DELETE FROM CacCauDaThich WHERE ChuDe = ? AND Cau = ?",
                new Object[] {cau.getMaDinhDanhCuaChuDe(), cau.getMaDinhDanh()});
    }

    public boolean coThichHong(CauModel cau) {
        boolean coThichHong = false;
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM CacCauDaThich WHERE ChuDe = ? AND Cau = ?", new String[] {
                Integer.toString(cau.getMaDinhDanhCuaChuDe()),
                Integer.toString(cau.getMaDinhDanh())});
        if (c.getCount() != 0) coThichHong = true;
        c.close();
        return coThichHong;
    }

    public void themCauNayVaoLichSuTraCuu(int chuDe, int cau) {
        getWritableDatabase().execSQL("INSERT OR REPLACE INTO LichSuTraCuu VALUES (?, ?, ?)", new Object[] {chuDe, cau, System.currentTimeMillis()});
    }

    public List<CauModel> cacCauDaThich() throws JSONException {
        List<ChuDeModel> cacChuDe = JsonUtils.getAll(mContext);
        List<CauModel> cacCauDaThich = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM CacCauDaThich ORDER BY ThoiGian DESC", new String[0]);
        while (c.moveToNext())
            cacCauDaThich.add(cacChuDe.get(c.getInt(0)).getCacCau().get(c.getInt(1)));
        c.close();
        return cacCauDaThich;
    }

    public List<CauModel> cacCauDuocGhiVaoLichSuTraCuu() throws JSONException {
        List<ChuDeModel> cacChuDe = JsonUtils.getAll(mContext);
        List<CauModel> cacCauDuocGhiVaoLichSuTraCuu = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM LichSuTraCuu ORDER BY ThoiGian DESC", new String[0]);
        while (c.moveToNext())
            cacCauDuocGhiVaoLichSuTraCuu.add(cacChuDe.get(c.getInt(0)).getCacCau().get(c.getInt(1)));
        c.close();
        return cacCauDuocGhiVaoLichSuTraCuu;
    }

    public List<CauModel> cacCauChuaDuocGhiVaoLichSuTraCuu() throws JSONException {
        List<ChuDeModel> cacChuDe = JsonUtils.getAll(mContext);
        List<int[]> cacMaDinhDanhDuocGhiVaoLichSuTraCuu = new ArrayList<>();
        List<CauModel> cacCauChuaDuocGhiVaoLichSuTraCuu = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM LichSuTraCuu", new String[0]);
        while (c.moveToNext())
            cacMaDinhDanhDuocGhiVaoLichSuTraCuu.add(
                    new int[] {c.getInt(0),
                            c.getInt(1)
                    }
            );
        c.close();

        for (int i = 0; i < cacChuDe.size(); i++) {
            ChuDeModel chuDe = cacChuDe.get(i);
            for (int j = 0; j < chuDe.getCacCau().size(); j++) {
                CauModel cau = chuDe.getCacCau().get(j);
                boolean coTrongLichSu = false;
                for (int k = 0; k < cacMaDinhDanhDuocGhiVaoLichSuTraCuu.size(); k++) {
                    int[] maDinhDanhDuocGhiVaoLichSuTraCuu = cacMaDinhDanhDuocGhiVaoLichSuTraCuu.get(k);
                    if (i == maDinhDanhDuocGhiVaoLichSuTraCuu[0] && j == maDinhDanhDuocGhiVaoLichSuTraCuu[1]) { coTrongLichSu = true; break; }
                }
                if (!coTrongLichSu) cacCauChuaDuocGhiVaoLichSuTraCuu.add(cau);
            }
        }
        return cacCauChuaDuocGhiVaoLichSuTraCuu;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
