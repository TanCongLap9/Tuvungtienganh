package com.example.tuvungtienganh.ui.cac_cau;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;

public class CauViewHolder extends RecyclerView.ViewHolder {
    public TextView cauTiengAnh;
    public TextView cauTiengViet;
    public CauViewHolder(@NonNull View itemView) {
        super(itemView);
        cauTiengAnh = itemView.findViewById(R.id.listitem_cau_cau_tieng_anh);
        cauTiengViet = itemView.findViewById(R.id.listitem_cau_cau_tieng_viet);
    }
}
