package com.thitracnghiem.hqt.repository;

import java.util.List;
import java.util.Optional;

import com.thitracnghiem.hqt.model.GIAOVIEN;

public interface GiaoVienRepository {
    
    /**
     * Lấy danh sách tất cả giáo viên
     * 
     * @return danh sách giáo viên
     */
    List<GIAOVIEN> findAll();
    
    /**
     * Tìm giáo viên theo mã
     * 
     * @param maGV mã giáo viên
     * @return Optional chứa giáo viên nếu tìm thấy
     */
    Optional<GIAOVIEN> findById(String maGV);
    
    /**
     * Thêm giáo viên mới
     * 
     * @param giaoVien thông tin giáo viên cần thêm
     * @return mã giáo viên đã thêm
     */
    String save(GIAOVIEN giaoVien);
    
    /**
     * Cập nhật thông tin giáo viên
     * 
     * @param giaoVien thông tin giáo viên cần cập nhật
     */
    void update(GIAOVIEN giaoVien);
    
    /**
     * Xóa giáo viên theo mã
     * 
     * @param maGV mã giáo viên cần xóa
     */
    void deleteById(String maGV);
} 