package com.example.btlandroidnc;

public class QuanLyMatHangClass {
    String MaMH, TenMH, Dvt, XuatXu, MoTa, TenNCC;
    Float DonGia, SoLuong;

    public QuanLyMatHangClass() {
    }

    public QuanLyMatHangClass(String maMH, String tenMH, String dvt, String xuatXu, String moTa, String tenNCC, Float donGia, Float soLuong) {
        MaMH = maMH;
        TenMH = tenMH;
        Dvt = dvt;
        XuatXu = xuatXu;
        MoTa = moTa;
        TenNCC = tenNCC;
        DonGia = donGia;
        SoLuong = soLuong;
    }

    public String getMaMH() {
        return MaMH;
    }

    public void setMaMH(String maMH) {
        MaMH = maMH;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String tenMH) {
        TenMH = tenMH;
    }

    public String getDvt() {
        return Dvt;
    }

    public void setDvt(String dvt) {
        Dvt = dvt;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String xuatXu) {
        XuatXu = xuatXu;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }

    public Float getDonGia() {
        return DonGia;
    }

    public void setDonGia(Float donGia) {
        DonGia = donGia;
    }

    public Float getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Float soLuong) {
        SoLuong = soLuong;
    }

    @Override
    public String toString() {
        return "QuanLyMatHangClass{" +
                "MaMH='" + MaMH + '\'' +
                ", TenMH='" + TenMH + '\'' +
                ", Dvt='" + Dvt + '\'' +
                ", XuatXu='" + XuatXu + '\'' +
                ", MoTa='" + MoTa + '\'' +
                ", TenNCC='" + TenNCC + '\'' +
                ", DonGia=" + DonGia +
                ", SoLuong=" + SoLuong +
                '}';
    }
}
