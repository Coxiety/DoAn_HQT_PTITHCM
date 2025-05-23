package com.thitracnghiem.hqt.model;

import java.time.LocalDate;

public class BANGDIEM {
    private String MASV;
    private String MAMH;
    private int LAN;
    private LocalDate NGAYTHI;
    private float DIEM;
    
    public BANGDIEM() {
    }
    
    public BANGDIEM(String MASV, String MAMH, int LAN, LocalDate NGAYTHI, float DIEM) {
        this.MASV = MASV;
        this.MAMH = MAMH;
        this.LAN = LAN;
        this.NGAYTHI = NGAYTHI;
        this.DIEM = DIEM;
    }
    
    public String getMASV() {
        return MASV;
    }
    
    public void setMASV(String MASV) {
        this.MASV = MASV;
    }
    
    public String getMAMH() {
        return MAMH;
    }
    
    public void setMAMH(String MAMH) {
        this.MAMH = MAMH;
    }
    
    public int getLAN() {
        return LAN;
    }
    
    public void setLAN(int LAN) {
        this.LAN = LAN;
    }
    
    public LocalDate getNGAYTHI() {
        return NGAYTHI;
    }
    
    public void setNGAYTHI(LocalDate NGAYTHI) {
        this.NGAYTHI = NGAYTHI;
    }
    
    public float getDIEM() {
        return DIEM;
    }
    
    public void setDIEM(float DIEM) {
        this.DIEM = DIEM;
    }
    
    @Override
    public String toString() {
        return "BANGDIEM{" +
                "MASV='" + MASV + '\'' +
                ", MAMH='" + MAMH + '\'' +
                ", LAN=" + LAN +
                ", NGAYTHI=" + NGAYTHI +
                ", DIEM=" + DIEM +
                '}';
    }
} 