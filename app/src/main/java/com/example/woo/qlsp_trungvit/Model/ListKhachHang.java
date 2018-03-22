package com.example.woo.qlsp_trungvit.Model;

/**
 * Created by Woo on 3/22/2018.
 */

public class ListKhachHang {
    private String tenKH;
    private String sdtKH;
    private String diachiKH;

    public ListKhachHang() {
        tenKH = "Mac Tu Tu";
        sdtKH = "0125469873";
        diachiKH = "Hong Dan - Bac Lieu";
    }

    public ListKhachHang(String tenKH, String sdtKH, String diachiKH) {
        this.tenKH = tenKH;
        this.sdtKH = sdtKH;
        this.diachiKH = diachiKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public String getDiachiKH() {
        return diachiKH;
    }

    public void setDiachiKH(String diachiKH) {
        this.diachiKH = diachiKH;
    }
}
