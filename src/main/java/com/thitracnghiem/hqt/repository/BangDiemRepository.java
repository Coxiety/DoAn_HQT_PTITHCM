package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.BANGDIEM;
import java.util.List;
import java.time.LocalDate;

public interface BangDiemRepository {
    List<BANGDIEM> findAll();
    BANGDIEM findByMaSVAndMaMHAndLan(String MASV, String MAMH, int LAN);
    List<BANGDIEM> findByMaSV(String MASV);
    List<BANGDIEM> findByMaMH(String MAMH);
    List<BANGDIEM> findByMaMHAndLan(String MAMH, int LAN);
    List<BANGDIEM> findByMaLopAndMaMHAndLan(String MALOP, String MAMH, int LAN);
    void save(BANGDIEM BANGDIEM);
    void update(BANGDIEM BANGDIEM);
    void delete(String MASV, String MAMH, int LAN);
    float getAverageDiemByMaLopAndMaMHAndLan(String MALOP, String MAMH, int LAN);
} 