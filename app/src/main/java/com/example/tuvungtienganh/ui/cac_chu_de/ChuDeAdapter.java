package com.example.tuvungtienganh.ui.cac_chu_de;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.models.ChuDeModel;
import com.example.tuvungtienganh.ui.cac_cau.CacCauActivity;

import java.util.List;

public class ChuDeAdapter extends RecyclerView.Adapter<ChuDeViewHolder> {
    private Context mContext;
    private List<ChuDeModel> mDanhSachCacChuDe;

    public ChuDeAdapter(Context context, List<ChuDeModel> danhSachCacChuDe) {
        this.mContext = context;
        this.mDanhSachCacChuDe = danhSachCacChuDe;
    }

    @NonNull
    @Override
    public ChuDeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.listitem_chu_de, parent, false);
        return new ChuDeViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuDeViewHolder holder, int position) {
        holder.tenChuDe.setText(mDanhSachCacChuDe.get(position).getTenChuDe());
        StringBuilder thoXayDungChuoi = new StringBuilder();
        List<CauModel> danhSachCau = mDanhSachCacChuDe.get(position).getCacCau();
        for (int i = 0; i < danhSachCau.size(); i++) {
            thoXayDungChuoi.append(i != 0 ? ", " : "");
            thoXayDungChuoi.append(danhSachCau.get(i).getCauTiengAnh());
            if (i == 2) {
                thoXayDungChuoi.append("...");
                break;
            }
        }
        holder.motSoCauTiengAnhDauTien.setText(thoXayDungChuoi.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cacCauIntent = new Intent(mContext, CacCauActivity.class);
                cacCauIntent.setAction(Intent.ACTION_VIEW);
                cacCauIntent.putExtra(CacCauActivity.PHAN_BO_SUNG_CHU_DE, holder.getLayoutPosition());
                mContext.startActivity(cacCauIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDanhSachCacChuDe.size();
    }
}
