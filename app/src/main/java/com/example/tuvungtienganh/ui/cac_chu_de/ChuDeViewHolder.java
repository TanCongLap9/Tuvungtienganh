package com.example.tuvungtienganh.ui.cac_chu_de;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuvungtienganh.R;

public class ChuDeViewHolder extends RecyclerView.ViewHolder {
    public TextView tenChuDe;
    public TextView motSoCauTiengAnhDauTien;
    public ChuDeViewHolder(@NonNull View itemView) {
        super(itemView);
        tenChuDe = itemView.findViewById(R.id.listitem_chu_de_ten_chu_de);
        motSoCauTiengAnhDauTien = itemView.findViewById(R.id.listitem_chu_de_mau_cau);
    }
}