package com.example.tuvungtienganh.ui.cac_chu_de;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.ChuDeModel;
import com.example.tuvungtienganh.ui.ManhVoCoTheTimKiemChuDe;
import com.example.tuvungtienganh.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CacChuDeFragment extends ManhVoCoTheTimKiemChuDe {
    private SearchView mTimKiem;
    private RecyclerView mRecView;
    private ChuDeAdapter mChuDeAdapter;
    private List<ChuDeModel> mDanhSachCacChuDeDuocNapTuDuLieuNguon;
    private List<ChuDeModel> mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cac_chu_de, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        taiDuLieu();
    }

    public void bindViews() {
        mTimKiem = getView().findViewById(R.id.fragment_cac_chu_de_tim_kiem);
        mRecView = getView().findViewById(R.id.fragment_cac_chu_de_danh_sach);
    }

    public void taiDuLieu() {
        try {
            mDanhSachCacChuDeDuocNapTuDuLieuNguon = JsonUtils.getAll(getContext());
            mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem = new ArrayList<>(mDanhSachCacChuDeDuocNapTuDuLieuNguon);
            mChuDeAdapter = new ChuDeAdapter(getContext(), mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem);
            mRecView.setAdapter(mChuDeAdapter);
            khoiTao(mTimKiem, mDanhSachCacChuDeDuocNapTuDuLieuNguon, mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem, mChuDeAdapter);
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
}