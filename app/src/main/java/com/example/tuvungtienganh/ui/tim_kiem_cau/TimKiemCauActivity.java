package com.example.tuvungtienganh.ui.tim_kiem_cau;

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
import com.example.tuvungtienganh.ui.cac_cau.CacCauAdapter;
import com.example.tuvungtienganh.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TimKiemCauActivity extends HoatDongCoTheTimKiemCau {
    private SearchView mTimKiem;
    private RecyclerView mRecView;
    private CacCauAdapter mAdapter;
    private Toolbar mToolbar;
    private List<CauModel> mDanhSachCacCauDuocNapTuDuLieuNguon;
    private List<CauModel> mDanhSachChuaCacCauDuocTraVeTuKetQuaTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_cau);
        bindViews();
        bindActionBar();
        taiDuLieu();

        mTimKiem.setQuery(getIntent().getStringExtra("TU_KHOA_CAN_TIM"), false);
    }

    private void bindActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void taiDuLieu() {
        try {
            mDanhSachCacCauDuocNapTuDuLieuNguon = new ArrayList<>();
            List<ChuDeModel> cacChuDe = JsonUtils.getAll(this);
            for (ChuDeModel chuDe : cacChuDe)
                mDanhSachCacCauDuocNapTuDuLieuNguon.addAll(chuDe.getCacCau());
            mDanhSachChuaCacCauDuocTraVeTuKetQuaTimKiem = new ArrayList<>(mDanhSachCacCauDuocNapTuDuLieuNguon);
            mAdapter = new CacCauAdapter(this, mDanhSachChuaCacCauDuocTraVeTuKetQuaTimKiem);
            mRecView.setAdapter(mAdapter);
            khoiTao(mTimKiem, mDanhSachCacCauDuocNapTuDuLieuNguon, mDanhSachChuaCacCauDuocTraVeTuKetQuaTimKiem, mAdapter);
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
        mTimKiem = findViewById(R.id.activity_tim_kiem_cau_tim_kiem);
        mRecView = findViewById(R.id.activity_tim_kiem_cau_danh_sach);
        mToolbar = findViewById(R.id.activity_tim_kiem_cau_toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}