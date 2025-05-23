package com.thitracnghiem.hqt.service;

import com.thitracnghiem.hqt.model.SINHVIEN;
import java.util.List;

public interface SinhVienService {
    List<SINHVIEN> getAllSinhVien();
    SINHVIEN getSinhVienByMaSV(String maSV);
    List<SINHVIEN> getSinhVienByHoTen(String ho, String ten);
    List<SINHVIEN> getSinhVienByMaLop(String maLop);
    void createSinhVien(SINHVIEN sinhVien);
    void updateSinhVien(SINHVIEN sinhVien);
    void deleteSinhVien(String maSV);
} 