package com.example.btlandroidnc;

public class QuanLyNCCClass {
    String MaNCC, TenNCC, DiaChi, Sdt;

    public QuanLyNCCClass() {
    }

    public QuanLyNCCClass(String maNCC, String tenNCC, String diaChi, String sdt) {
        MaNCC = maNCC;
        TenNCC = tenNCC;
        DiaChi = diaChi;
        Sdt = sdt;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    @Override
    public String toString() {
        return "QuanLyNCCClass{" +
                "MaNCC='" + MaNCC + '\'' +
                ", TenNCC='" + TenNCC + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", Sdt='" + Sdt + '\'' +
                '}';
    }
}
