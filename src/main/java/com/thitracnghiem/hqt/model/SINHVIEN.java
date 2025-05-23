package com.thitracnghiem.hqt.model;

import java.time.LocalDate;

public class SINHVIEN {
    private String MASV;
    private String HO;
    private String TEN;
    private LocalDate NGAYSINH;
    private String DIACHI;
    private String MALOP;
    
    public SINHVIEN() {
    }
    
    public SINHVIEN(String MASV, String HO, String TEN, LocalDate NGAYSINH, String DIACHI, String MALOP) {
        this.MASV = MASV;
        this.HO = HO;
        this.TEN = TEN;
        this.NGAYSINH = NGAYSINH;
        this.DIACHI = DIACHI;
        this.MALOP = MALOP;
    }
    
    public String getMASV() {
        return MASV;
    }
    
    public void setMASV(String MASV) {
        this.MASV = MASV;
    }
    
    public String getHO() {
        return HO;
    }
    
    public void setHO(String HO) {
        this.HO = HO;
    }
    
    public String getTEN() {
        return TEN;
    }
    
    public void setTEN(String TEN) {
        this.TEN = TEN;
    }
    
    public LocalDate getNGAYSINH() {
        return NGAYSINH;
    }
    
    public void setNGAYSINH(LocalDate NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }
    
    public String getDIACHI() {
        return DIACHI;
    }
    
    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }
    
    public String getMALOP() {
        return MALOP;
    }
    
    public void setMALOP(String MALOP) {
        this.MALOP = MALOP;
    }
    
    @Override
    public String toString() {
        return "SINHVIEN{" +
                "MASV='" + MASV + '\'' +
                ", HO='" + HO + '\'' +
                ", TEN='" + TEN + '\'' +
                ", NGAYSINH=" + NGAYSINH +
                ", DIACHI='" + DIACHI + '\'' +
                ", MALOP='" + MALOP + '\'' +
                '}';
    }
} 