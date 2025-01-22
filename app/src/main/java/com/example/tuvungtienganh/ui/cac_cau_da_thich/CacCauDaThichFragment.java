package com.example.tuvungtienganh.ui.cac_cau_da_thich;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.ui.ManhVoCoTheTimKiemCau;
import com.example.tuvungtienganh.ui.cac_cau.CacCauAdapter;
import com.example.tuvungtienganh.utils.Sql;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CacCauDaThichFragment extends ManhVoCoTheTimKiemCau {
    private SearchView mTimKiem;
    private RecyclerView mRecView;
    private CacCauAdapter mCacCauAdapter;
    private List<CauModel> mDanhSachCacCauDuocNapTuDuLieuNguon;
    private List<CauModel> mDanhSachCacCauDuocTraVeTuKetQuaTimKiem;
    private Sql mSql;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cac_cau_da_thich, container, false);
    }

    public void taiDuLieu() {
        try {
            mDanhSachCacCauDuocNapTuDuLieuNguon.clear();
            mDanhSachCacCauDuocNapTuDuLieuNguon.addAll(mSql.cacCauDaThich());
            mDanhSachCacCauDuocTraVeTuKetQuaTimKiem.clear();
            mDanhSachCacCauDuocTraVeTuKetQuaTimKiem.addAll(new ArrayList<>(mDanhSachCacCauDuocNapTuDuLieuNguon));
            if (mCacCauAdapter == null) mCacCauAdapter = new CacCauAdapter(getContext(), mDanhSachCacCauDuocTraVeTuKetQuaTimKiem);
            else mCacCauAdapter.notifyDataSetChanged();
            if (mRecView.getAdapter() == null) mRecView.setAdapter(mCacCauAdapter);
            khoiTao(mTimKiem, mDanhSachCacCauDuocNapTuDuLieuNguon, mDanhSachCacCauDuocTraVeTuKetQuaTimKiem, mCacCauAdapter);
        } catch (JSONException e) {
            new AlertDialog.Builder(getContext())
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        mSql = new Sql(getContext(), Sql.DATABASE_NAME);
        mDanhSachCacCauDuocNapTuDuLieuNguon = new ArrayList<>();
        mDanhSachCacCauDuocTraVeTuKetQuaTimKiem = new ArrayList<>();
        taiDuLieu();
    }

    public void bindViews() {
        mTimKiem = getView().findViewById(R.id.fragment_cac_cau_da_thich_tim_kiem);
        mRecView = getView().findViewById(R.id.fragment_cac_cau_da_thich_danh_sach);
    }

    @Override
    public void onResume() {
        super.onResume();
        taiDuLieu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSql != null) mSql.close();
    }
}