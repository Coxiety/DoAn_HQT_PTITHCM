package com.thitracnghiem.hqt.service;

import com.thitracnghiem.hqt.model.MONHOC;
import java.util.List;

public interface MonHocService {
    List<MONHOC> getAllMonHoc();
    MONHOC getMonHocByMaMH(String maMH);
    MONHOC getMonHocByTenMH(String tenMH);
    void createMonHoc(MONHOC monHoc);
    void updateMonHoc(MONHOC monHoc);
    void deleteMonHoc(String maMH);
    List<MONHOC> searchMonHoc(String keyword);
}