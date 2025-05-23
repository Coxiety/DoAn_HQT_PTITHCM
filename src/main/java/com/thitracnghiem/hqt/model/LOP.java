package com.thitracnghiem.hqt.model;

public class LOP {
    private String MALOP;
    private String TENLOP;
    
    public LOP() {
    }
    
    public LOP(String MALOP, String TENLOP) {
        this.MALOP = MALOP;
        this.TENLOP = TENLOP;
    }
    
    public String getMALOP() {
        return MALOP;
    }
    
    public void setMALOP(String MALOP) {
        this.MALOP = MALOP;
    }
    
    public String getTENLOP() {
        return TENLOP;
    }
    
    public void setTENLOP(String TENLOP) {
        this.TENLOP = TENLOP;
    }
    
    @Override
    public String toString() {
        return "LOP{" +
                "MALOP='" + MALOP + '\'' +
                ", TENLOP='" + TENLOP + '\'' +
                '}';
    }
} 