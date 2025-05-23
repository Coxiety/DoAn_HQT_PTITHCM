package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.SINHVIEN;
import java.util.List;
import java.util.Date;

public interface SinhVienRepository {
    List<SINHVIEN> findAll();
    SINHVIEN findByMaSV(String maSV);
    List<SINHVIEN> findByHoTen(String ho, String ten);
    List<SINHVIEN> findByMaLop(String maLop);
    void save(SINHVIEN sinhVien);
    void update(SINHVIEN sinhVien);
    void delete(String maSV);
} 