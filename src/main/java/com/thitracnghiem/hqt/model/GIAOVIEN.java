package com.thitracnghiem.hqt.model;

public class GIAOVIEN {
    private String MAGV;
    private String HO;
    private String TEN;
    private String SODTLL;
    private String DIACHI;
    
    public GIAOVIEN() {
    }
    
    public GIAOVIEN(String MAGV, String HO, String TEN, String SODTLL, String DIACHI) {
        this.MAGV = MAGV;
        this.HO = HO;
        this.TEN = TEN;
        this.SODTLL = SODTLL;
        this.DIACHI = DIACHI;
    }
    
    public String getMAGV() {
        return MAGV;
    }
    
    public void setMAGV(String MAGV) {
        this.MAGV = MAGV;
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
    
    public String getSODTLL() {
        return SODTLL;
    }
    
    public void setSODTLL(String SODTLL) {
        this.SODTLL = SODTLL;
    }
    
    public String getDIACHI() {
        return DIACHI;
    }
    
    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }
    
    @Override
    public String toString() {
        return "GIAOVIEN{" +
                "MAGV='" + MAGV + '\'' +
                ", HO='" + HO + '\'' +
                ", TEN='" + TEN + '\'' +
                ", SODTLL='" + SODTLL + '\'' +
                ", DIACHI='" + DIACHI + '\'' +
                '}';
    }
} 