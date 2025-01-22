package com.example.tuvungtienganh.ui.cac_cau;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.ui.cau.CauActivity;
import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;

import java.util.List;

public class CacCauAdapter extends RecyclerView.Adapter<CauViewHolder> {
    private Context mContext;
    private List<CauModel> mDanhSachCacCau;

    public CacCauAdapter(Context context, List<CauModel> danhSachCacCau) {
        this.mContext = context;
        this.mDanhSachCacCau = danhSachCacCau;
    }

    @NonNull
    @Override
    public CauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(mContext).inflate(R.layout.listitem_cau, parent, false);
        return new CauViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull CauViewHolder holder, int position) {
        holder.cauTiengAnh.setText(mDanhSachCacCau.get(position).getCauTiengAnh());
        holder.cauTiengViet.setText(mDanhSachCacCau.get(position).getCauTiengViet());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cauIntent = new Intent(mContext, CauActivity.class);
                cauIntent.setAction(Intent.ACTION_VIEW);
                cauIntent.putExtra(CauActivity.PHAN_BO_SUNG_CAU, mDanhSachCacCau.get(holder.getLayoutPosition()));
                mContext.startActivity(cauIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDanhSachCacCau.size();
    }
}
