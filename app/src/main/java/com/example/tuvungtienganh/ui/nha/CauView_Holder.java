package com.example.tuvungtienganh.ui.nha;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;

public class CauView_Holder extends RecyclerView.ViewHolder {
    public TextView cauTiengAnh;
    public TextView cauTiengViet;
    public CauView_Holder(@NonNull View itemView, boolean bind) {
        super(itemView);
        if (!bind) return;
        cauTiengAnh = itemView.findViewById(R.id.listitem_main_cau_tieng_anh);
        cauTiengViet = itemView.findViewById(R.id.listitem_main_cau_tieng_viet);
    }
}