package com.thitracnghiem.hqt.service;

import java.util.List;

import com.thitracnghiem.hqt.model.GIAOVIEN;

public interface GiaoVienService {
    
    /**
     * Lấy danh sách tất cả giáo viên
     * 
     * @return danh sách giáo viên
     */
    List<GIAOVIEN> getAllGiaoVien();
    
    /**
     * Lấy thông tin giáo viên theo mã
     * 
     * @param maGV mã giáo viên
     * @return thông tin giáo viên
     */
    GIAOVIEN getGiaoVienByMaGV(String maGV);
    
    /**
     * Thêm giáo viên mới
     * 
     * @param giaoVien thông tin giáo viên cần thêm
     * @return mã giáo viên đã thêm
     */
    String addGiaoVien(GIAOVIEN giaoVien);
    
    /**
     * Cập nhật thông tin giáo viên
     * 
     * @param giaoVien thông tin giáo viên cần cập nhật
     */
    void updateGiaoVien(GIAOVIEN giaoVien);
    
    /**
     * Xóa giáo viên theo mã
     * 
     * @param maGV mã giáo viên cần xóa
     */
    void deleteGiaoVien(String maGV);
} 