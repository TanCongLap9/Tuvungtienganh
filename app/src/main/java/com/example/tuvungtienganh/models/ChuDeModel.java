package com.example.tuvungtienganh.models;

import java.io.Serializable;
import java.util.List;

public class ChuDeModel implements Serializable {
    private String tenChuDe;
    private int maDinhDanh;
    private List<CauModel> cacCau;

    public ChuDeModel(String tenChuDe, List<CauModel> cacCau, int maDinhDanh) {
        this.tenChuDe = tenChuDe;
        this.cacCau = cacCau;
        this.maDinhDanh = maDinhDanh;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public List<CauModel> getCacCau() {
        return cacCau;
    }

    public int getMaDinhDanh() {
        return maDinhDanh;
    }
}
