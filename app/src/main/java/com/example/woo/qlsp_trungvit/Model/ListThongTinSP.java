package com.example.woo.qlsp_trungvit.Model;

/**
 * Created by Woo on 3/17/2018.
 */

public class ListThongTinSP {
    private int soLuong;
    private int donGia;
    private String loai;
    private String thoiGian;
    private String tenKhachHang;

    public ListThongTinSP() {
        soLuong = 0;
        donGia = 0;
        loai = "So";
        thoiGian = "22-3-2018";
        tenKhachHang = "NVA";
    }

    public ListThongTinSP(int soLuong, int donGia, String loai, String thoiGian, String tenKhachHang) {
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.loai = loai;
        this.thoiGian = thoiGian;
        this.tenKhachHang = tenKhachHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
}
