package com.example.tuvungtienganh.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.ui.cac_cau.CacCauAdapter;

import java.util.List;

public abstract class HoatDongCoTheTimKiemCau extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView mTimKiem;
    private List<CauModel> mDanhSachCacCauDuocNapTuDuLieuNguon;
    private List<CauModel> mDanhSachCacCauDuocTraVeTuKetQuaTimKiem;
    private CacCauAdapter mCacCauAdapter;

    protected void khoiTao(
            SearchView timKiem,
            List<CauModel> danhSachChuaCacCauDuocNapTuDuLieuNguon,
            List<CauModel> danhSachChuaCacCauDuocTraVeTuKetQuaTimKiem,
            CacCauAdapter cacCauAdapter) {
        this.mTimKiem = timKiem;
        this.mDanhSachCacCauDuocNapTuDuLieuNguon = danhSachChuaCacCauDuocNapTuDuLieuNguon;
        this.mDanhSachCacCauDuocTraVeTuKetQuaTimKiem = danhSachChuaCacCauDuocTraVeTuKetQuaTimKiem;
        this.mCacCauAdapter = cacCauAdapter;
        mTimKiem.setOnQueryTextListener(this);
    }

    protected boolean khoiTaoChua() {
        return mCacCauAdapter != null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mDanhSachCacCauDuocTraVeTuKetQuaTimKiem.clear();
        for (CauModel cau : mDanhSachCacCauDuocNapTuDuLieuNguon) {
            if (newText.isEmpty() ||
                    cau.getCauTiengAnh().toLowerCase().replace(newText.toLowerCase(), "").length() != cau.getCauTiengAnh().length() ||
                    cau.getCauTiengViet().toLowerCase().replace(newText.toLowerCase(), "").length() != cau.getCauTiengViet().length())
                mDanhSachCacCauDuocTraVeTuKetQuaTimKiem.add(cau);
        }
        mCacCauAdapter.notifyDataSetChanged();
        return true;
    }
}
