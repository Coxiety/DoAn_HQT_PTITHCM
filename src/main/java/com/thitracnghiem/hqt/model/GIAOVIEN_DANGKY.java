package com.thitracnghiem.hqt.model;

import java.time.LocalDateTime;

public class GIAOVIEN_DANGKY {
    private String MAGV;
    private String MALOP;
    private String MAMH;
    private String TRINHDO;
    private LocalDateTime NGAYTHI;
    private int LAN;
    private int SOCAUTHI;
    private int THOIGIAN;
    
    public GIAOVIEN_DANGKY() {
    }
    
    public GIAOVIEN_DANGKY(String MAGV, String MALOP, String MAMH, String TRINHDO, LocalDateTime NGAYTHI, int LAN, int SOCAUTHI, int THOIGIAN) {
        this.MAGV = MAGV;
        this.MALOP = MALOP;
        this.MAMH = MAMH;
        this.TRINHDO = TRINHDO;
        this.NGAYTHI = NGAYTHI;
        this.LAN = LAN;
        this.SOCAUTHI = SOCAUTHI;
        this.THOIGIAN = THOIGIAN;
    }
    
    public String getMAGV() {
        return MAGV;
    }
    
    public void setMAGV(String MAGV) {
        this.MAGV = MAGV;
    }
    
    public String getMALOP() {
        return MALOP;
    }
    
    public void setMALOP(String MALOP) {
        this.MALOP = MALOP;
    }
    
    public String getMAMH() {
        return MAMH;
    }
    
    public void setMAMH(String MAMH) {
        this.MAMH = MAMH;
    }
    
    public String getTRINHDO() {
        return TRINHDO;
    }
    
    public void setTRINHDO(String TRINHDO) {
        this.TRINHDO = TRINHDO;
    }
    
    public LocalDateTime getNGAYTHI() {
        return NGAYTHI;
    }
    
    public void setNGAYTHI(LocalDateTime NGAYTHI) {
        this.NGAYTHI = NGAYTHI;
    }
    
    public int getLAN() {
        return LAN;
    }
    
    public void setLAN(int LAN) {
        this.LAN = LAN;
    }
    
    public int getSOCAUTHI() {
        return SOCAUTHI;
    }
    
    public void setSOCAUTHI(int SOCAUTHI) {
        this.SOCAUTHI = SOCAUTHI;
    }
    
    public int getTHOIGIAN() {
        return THOIGIAN;
    }
    
    public void setTHOIGIAN(int THOIGIAN) {
        this.THOIGIAN = THOIGIAN;
    }
    
    @Override
    public String toString() {
        return "GIAOVIEN_DANGKY{" +
                "MAGV='" + MAGV + '\'' +
                ", MALOP='" + MALOP + '\'' +
                ", MAMH='" + MAMH + '\'' +
                ", TRINHDO='" + TRINHDO + '\'' +
                ", NGAYTHI=" + NGAYTHI +
                ", LAN=" + LAN +
                ", SOCAUTHI=" + SOCAUTHI +
                ", THOIGIAN=" + THOIGIAN +
                '}';
    }
} 