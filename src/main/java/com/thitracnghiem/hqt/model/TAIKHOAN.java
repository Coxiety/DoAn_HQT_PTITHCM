package com.thitracnghiem.hqt.model;

public class TAIKHOAN {
    private String LOGINNAME;
    private String PASSWORD;
    private String ROLE;
    private String MAGV_REF;
    private String MASV_REF;
    
    public TAIKHOAN() {
    }
    
    public TAIKHOAN(String LOGINNAME, String PASSWORD, String ROLE, String MAGV_REF, String MASV_REF) {
        this.LOGINNAME = LOGINNAME;
        this.PASSWORD = PASSWORD;
        this.ROLE = ROLE;
        this.MAGV_REF = MAGV_REF;
        this.MASV_REF = MASV_REF;
    }
    
    public String getLOGINNAME() {
        return LOGINNAME;
    }
    
    public void setLOGINNAME(String LOGINNAME) {
        this.LOGINNAME = LOGINNAME;
    }
    
    public String getPASSWORD() {
        return PASSWORD;
    }
    
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    
    public String getROLE() {
        return ROLE;
    }
    
    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }
    
    public String getMAGV_REF() {
        return MAGV_REF;
    }
    
    public void setMAGV_REF(String MAGV_REF) {
        this.MAGV_REF = MAGV_REF;
    }
      public String getMASV_REF() {
        return MASV_REF;
    }
    
    public void setMASV_REF(String MASV_REF) {
        this.MASV_REF = MASV_REF;
    }
    
    // Alias methods để tương thích với AuthService
    public String getLoginname() {
        return LOGINNAME;
    }
    
    public void setLoginname(String loginname) {
        this.LOGINNAME = loginname;
    }
    
    public String getPassword() {
        return PASSWORD;
    }
    
    public void setPassword(String password) {
        this.PASSWORD = password;
    }
    
    public String getRole() {
        return ROLE;
    }
    
    public void setRole(String role) {
        this.ROLE = role;
    }
    
    public String getMagv() {
        return MAGV_REF;
    }
    
    public void setMagv(String magv) {
        this.MAGV_REF = magv;
    }
    
    public String getMasv() {
        return MASV_REF;
    }
      public void setMasv(String masv) {
        this.MASV_REF = masv;
    }
    
    @Override
    public String toString() {
        return "TAIKHOAN{" +
                "LOGINNAME='" + LOGINNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", ROLE='" + ROLE + '\'' +
                ", MAGV_REF='" + MAGV_REF + '\'' +
                ", MASV_REF='" + MASV_REF + '\'' +
                '}';
    }
}