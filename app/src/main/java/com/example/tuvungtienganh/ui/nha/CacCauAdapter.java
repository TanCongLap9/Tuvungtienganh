package com.example.tuvungtienganh.ui.nha;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.ui.cau.CauActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CacCauAdapter extends RecyclerView.Adapter<CauView_Holder> {
    public static final int SO_LUONG_CAC_CAU_DAU_TIEN_DUOC_HIEN_THI = 3;
    private List<CauModel> mDanhSachCacCau;
    private Activity mActivity;
    private @IdRes int mNavView;

    public CacCauAdapter(Activity activity, List<CauModel> danhSachCacCau, @IdRes int navView, boolean shuffle) {
        this.mActivity = activity;
        this.mDanhSachCacCau = danhSachCacCau;
        this.mNavView = navView;
        if (shuffle) Collections.shuffle(danhSachCacCau);
    }

    @NonNull
    @Override
    public CauView_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.listitem_nha, parent, false);
            return new CauView_Holder(view, true);
        }
        else {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_xem_tat_ca, parent, false);
            return new CauView_Holder(view, false);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CauView_Holder holder, int position) {
        if (getItemViewType(position) == 0) {
            holder.cauTiengAnh.setText(mDanhSachCacCau.get(position).getCauTiengAnh());
            holder.cauTiengViet.setText(mDanhSachCacCau.get(position).getCauTiengViet());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cauIntent = new Intent(mActivity, CauActivity.class);
                    cauIntent.setAction(Intent.ACTION_VIEW);
                    cauIntent.putExtra(CauActivity.PHAN_BO_SUNG_CAU, mDanhSachCacCau.get(holder.getLayoutPosition()));
                    mActivity.startActivity(cauIntent);
                }
            });
        }
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavOptions.Builder thoXayDungCacTuyChinhDieuHuong = new NavOptions.Builder();
                    thoXayDungCacTuyChinhDieuHuong.setLaunchSingleTop(true);
                    Navigation
                            .findNavController(mActivity, R.id.nav_host_fragment_content_main)
                            .navigate(mNavView, null, thoXayDungCacTuyChinhDieuHuong.build());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < getItemCount() - 1 ? 0 : 1; // 0: Câu, 1: Xem tất cả
    }

    @Override
    public int getItemCount() {
        return Math.min(SO_LUONG_CAC_CAU_DAU_TIEN_DUOC_HIEN_THI, mDanhSachCacCau.size()) + 1;
    }
}
