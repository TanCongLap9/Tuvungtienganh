package com.example.tuvungtienganh.ui;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.tuvungtienganh.models.ChuDeModel;
import com.example.tuvungtienganh.ui.cac_chu_de.ChuDeAdapter;

import java.util.List;

public abstract class ManhVoCoTheTimKiemChuDe extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mTimKiem;
    private List<ChuDeModel> mDanhSachCacChuDeDuocNapTuDuLieuNguon;
    private List<ChuDeModel> mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem;
    private ChuDeAdapter mChuDeAdapter;

    protected void khoiTao(
            SearchView timKiem,
            List<ChuDeModel> danhSachChuaCacCauDuocNapTuDuLieuNguon,
            List<ChuDeModel> danhSachChuaCacCauDuocTraVeTuKetQuaTimKiem,
            ChuDeAdapter chuDeAdapter) {
        this.mTimKiem = timKiem;
        this.mDanhSachCacChuDeDuocNapTuDuLieuNguon = danhSachChuaCacCauDuocNapTuDuLieuNguon;
        this.mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem = danhSachChuaCacCauDuocTraVeTuKetQuaTimKiem;
        this.mChuDeAdapter = chuDeAdapter;
        mTimKiem.setOnQueryTextListener(this);
    }

    protected boolean khoiTaoChua() {
        return mChuDeAdapter != null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem.clear();
        for (ChuDeModel chuDe : mDanhSachCacChuDeDuocNapTuDuLieuNguon) {
            if (newText.isEmpty() ||
                    chuDe.getTenChuDe().toLowerCase().replace(newText.toLowerCase(), "").length() != chuDe.getTenChuDe().length())
                mDanhSachCacChuDeDuocTraVeTuKetQuaTimKiem.add(chuDe);
        }
        mChuDeAdapter.notifyDataSetChanged();
        return true;
    }
}
