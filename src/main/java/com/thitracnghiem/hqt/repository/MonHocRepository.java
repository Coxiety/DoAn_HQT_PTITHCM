package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.MONHOC;
import java.util.List;

public interface MonHocRepository {
    List<MONHOC> findAll();
    MONHOC findByMaMH(String maMH);
    MONHOC findByTenMH(String tenMH);
    void save(MONHOC monHoc);
    void update(MONHOC monHoc);
    void delete(String maMH);
    List<MONHOC> search(String keyword);
}