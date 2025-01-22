package com.example.tuvungtienganh.ui.cac_cau;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.models.ChuDeModel;
import com.example.tuvungtienganh.ui.HoatDongCoTheTimKiemCau;
import com.example.tuvungtienganh.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CacCauActivity extends HoatDongCoTheTimKiemCau {
    public static final String PHAN_BO_SUNG_CHU_DE = "CHU_DE";
    private SearchView mTimKiem;
    private RecyclerView mRecView;
    private CacCauAdapter mCacCauAdapter;
    private Toolbar mThanhCongCu;
    private ChuDeModel mChuDe;
    private List<CauModel> mDanhSachCacCauDuocNapTuDuLieuNguon;
    private List<CauModel> mDanhSachCacCauDuocTraVeTuKetQuaTimKiem;

    @Override
    protected void onCreate(Bundle trangThaiTheHienDuocLuu) {
        super.onCreate(trangThaiTheHienDuocLuu);
        setContentView(R.layout.activity_cac_cau);
        bindViews();
        bindActionBar();
        taiDuLieu();
    }

    private void bindActionBar() {
        setSupportActionBar(mThanhCongCu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void taiDuLieu() {
        try {
            mChuDe = JsonUtils.getAll(this)
                    .get(getIntent().getIntExtra(PHAN_BO_SUNG_CHU_DE, 0));
            setTitle(mChuDe.getTenChuDe());
            mDanhSachCacCauDuocNapTuDuLieuNguon = mChuDe.getCacCau();
            mDanhSachCacCauDuocTraVeTuKetQuaTimKiem = new ArrayList<>(mDanhSachCacCauDuocNapTuDuLieuNguon);
            mCacCauAdapter = new CacCauAdapter(this, mDanhSachCacCauDuocTraVeTuKetQuaTimKiem);
            mRecView.setAdapter(mCacCauAdapter);
            khoiTao(mTimKiem, mDanhSachCacCauDuocNapTuDuLieuNguon, mDanhSachCacCauDuocTraVeTuKetQuaTimKiem, mCacCauAdapter);
        } catch (JSONException e) {
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("Lỗi tải dữ liệu")
                    .setMessage("Không thể tải dữ liệu. Bạn có muốn thử lại không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taiDuLieu();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        }
    }

    public void bindViews() {
        mTimKiem = findViewById(R.id.activity_cac_cau_tim_kiem);
        mRecView = findViewById(R.id.activity_cac_cau_danh_sach);
        mThanhCongCu = findViewById(R.id.activity_cac_cau_toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}