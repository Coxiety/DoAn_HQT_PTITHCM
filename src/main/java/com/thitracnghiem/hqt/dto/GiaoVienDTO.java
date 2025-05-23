package com.thitracnghiem.hqt.dto;

public class GiaoVienDTO {
    private String maGV;
    private String ho;
    private String ten;
    private String hocvi;
    private String hocham;
    private String diachi;
    private String sodtll;
    
    // Thông tin tài khoản
    private String loginname;
    private String password;
    private String confirmPassword;
    
    public GiaoVienDTO() {
    }
    
    public GiaoVienDTO(String maGV, String ho, String ten, String hocvi, String hocham, String diachi, String sodtll,
            String loginname, String password, String confirmPassword) {
        this.maGV = maGV;
        this.ho = ho;
        this.ten = ten;
        this.hocvi = hocvi;
        this.hocham = hocham;
        this.diachi = diachi;
        this.sodtll = sodtll;
        this.loginname = loginname;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getter và Setter
    public String getMaGV() {
        return maGV;
    }
    
    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }
    
    public String getHo() {
        return ho;
    }
    
    public void setHo(String ho) {
        this.ho = ho;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getHocvi() {
        return hocvi;
    }
    
    public void setHocvi(String hocvi) {
        this.hocvi = hocvi;
    }
    
    public String getHocham() {
        return hocham;
    }
    
    public void setHocham(String hocham) {
        this.hocham = hocham;
    }
    
    public String getDiachi() {
        return diachi;
    }
    
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    
    public String getSodtll() {
        return sodtll;
    }
    
    public void setSodtll(String sodtll) {
        this.sodtll = sodtll;
    }
    
    public String getLoginname() {
        return loginname;
    }
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
} 