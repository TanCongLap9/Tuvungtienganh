package com.example.tuvungtienganh.ui.nha;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.BuildConfig;
import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.ui.tim_kiem_cau.TimKiemCauActivity;
import com.example.tuvungtienganh.utils.Sql;

import org.json.JSONException;

import java.util.List;

public class NhaFragment extends Fragment {
    private TextView mTenPhanMem;
    private TextView mPhienBanUngDung;
    private SearchView mTimKiem;
    private RecyclerView mCacCauMoiRecView;
    private RecyclerView mCacCauDaThichRecView;
    private RecyclerView mCacCauCanOnLaiRecView;
    private CacCauAdapter mCacCauCanOnLaiAdapter;
    private CacCauAdapter mCacCauDaThichAdapter;
    private CacCauAdapter mCacCauMoiAdapter;
    private List<CauModel> mDanhSachCacCauDaThich;
    private List<CauModel> mDanhSachCacCauCanOnLai;
    private Sql mSql;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nha, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSql = new Sql(getContext(), "TuVungGiaoTiepAnhViet");
        bindViews();
        bindEvents();
        bindRecViews();
        mTenPhanMem.setText("Tên phần mềm: " + getContext().getApplicationInfo().loadLabel(getContext().getPackageManager()));
        mPhienBanUngDung.setText("Phiên bản: " + BuildConfig.VERSION_NAME);
    }

    public void bindViews() {
        mCacCauMoiRecView = getView().findViewById(R.id.fragment_home_danh_sach_cac_cau_moi);
        mCacCauDaThichRecView = getView().findViewById(R.id.fragment_home_danh_sach_cac_cau_da_thich);
        mCacCauCanOnLaiRecView = getView().findViewById(R.id.fragment_home_danh_sach_cac_cau_can_on_lai);
        mTimKiem = getView().findViewById(R.id.fragment_home_tim_kiem_cac_cau_theo_tu_khoa_duoc_chi_dinh);
        mTenPhanMem = getView().findViewById(R.id.fragment_home_chu_ghi_ten_phan_mem);
        mPhienBanUngDung = getView().findViewById(R.id.fragment_home_chu_ghi_phien_ban_cua_ung_dung);
    }

    public void bindEvents() {
        mTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTimKiem.clearFocus(); // Lỗi gọi 2 lần
                Intent duDinhMoHoatDongTimKiemCau = new Intent(getContext(), TimKiemCauActivity.class);
                duDinhMoHoatDongTimKiemCau.setAction(Intent.ACTION_VIEW);
                duDinhMoHoatDongTimKiemCau.putExtra("TU_KHOA_CAN_TIM", mTimKiem.getQuery().toString());
                startActivity(duDinhMoHoatDongTimKiemCau);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void bindRecViews() {
        try {
            mDanhSachCacCauDaThich = mSql.cacCauDaThich();
            mDanhSachCacCauCanOnLai = mSql.cacCauDuocGhiVaoLichSuTraCuu();
            mCacCauMoiAdapter = new CacCauAdapter(getActivity(), mSql.cacCauChuaDuocGhiVaoLichSuTraCuu(), R.id.nav_cac_chu_de, true);
            mCacCauDaThichAdapter = new CacCauAdapter(getActivity(), mDanhSachCacCauDaThich, R.id.nav_cac_cau_da_thich, false);
            mCacCauCanOnLaiAdapter = new CacCauAdapter(getActivity(), mDanhSachCacCauCanOnLai, R.id.nav_lich_su_tra_cuu, false);
            mCacCauMoiRecView.setAdapter(mCacCauMoiAdapter);
            mCacCauDaThichRecView.setAdapter(mCacCauDaThichAdapter);
            mCacCauCanOnLaiRecView.setAdapter(mCacCauCanOnLaiAdapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mDanhSachCacCauDaThich.clear();
            mDanhSachCacCauDaThich.addAll(mSql.cacCauDaThich());
            mDanhSachCacCauCanOnLai.clear();
            mDanhSachCacCauCanOnLai.addAll(mSql.cacCauDuocGhiVaoLichSuTraCuu());
            mCacCauDaThichAdapter.notifyDataSetChanged();
            mCacCauCanOnLaiAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSql.close();
    }
}