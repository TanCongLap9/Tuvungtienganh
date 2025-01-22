package com.example.tuvungtienganh.models;

import java.io.Serializable;

public class CauModel implements Serializable {
    private String cauTiengAnh;
    private String cauTiengViet;
    private int maDinhDanh;
    private int maDinhDanhCuaChuDe;

    public CauModel(String cauTiengAnh, String cauTiengViet, int maDinhDanh, int maDinhDanhCuaChuDe) {
        this.cauTiengAnh = cauTiengAnh;
        this.cauTiengViet = cauTiengViet;
        this.maDinhDanh = maDinhDanh;
        this.maDinhDanhCuaChuDe = maDinhDanhCuaChuDe;
    }

    public String getCauTiengAnh() {
        return cauTiengAnh;
    }

    public String getCauTiengViet() {
        return cauTiengViet;
    }

    public int getMaDinhDanh() {
        return maDinhDanh;
    }

    public int getMaDinhDanhCuaChuDe() {
        return maDinhDanhCuaChuDe;
    }
}
