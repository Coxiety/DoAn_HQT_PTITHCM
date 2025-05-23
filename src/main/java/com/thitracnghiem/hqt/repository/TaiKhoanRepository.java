package com.thitracnghiem.hqt.repository;

import java.util.List;
import java.util.Optional;

import com.thitracnghiem.hqt.model.TAIKHOAN;

public interface TaiKhoanRepository {
    
    /**
     * Lấy danh sách tất cả tài khoản
     * 
     * @return danh sách tài khoản
     */
    List<TAIKHOAN> findAll();
    
    /**
     * Tìm tài khoản theo tên đăng nhập
     * 
     * @param loginname tên đăng nhập
     * @return Optional chứa tài khoản nếu tìm thấy
     */
    Optional<TAIKHOAN> findByUsername(String loginname);
    
    /**
     * Tìm tài khoản theo mã giáo viên
     * 
     * @param maGV mã giáo viên
     * @return Optional chứa tài khoản nếu tìm thấy
     */
    Optional<TAIKHOAN> findByMaGV(String maGV);
    
    /**
     * Tìm tài khoản theo mã sinh viên
     * 
     * @param maSV mã sinh viên
     * @return Optional chứa tài khoản nếu tìm thấy
     */
    Optional<TAIKHOAN> findByMaSV(String maSV);
    
    /**
     * Thêm tài khoản mới
     * 
     * @param taiKhoan thông tin tài khoản cần thêm
     */
    void save(TAIKHOAN taiKhoan);
    
    /**
     * Cập nhật thông tin tài khoản
     * 
     * @param taiKhoan thông tin tài khoản cần cập nhật
     */
    void update(TAIKHOAN taiKhoan);
    
    /**
     * Xóa tài khoản theo tên đăng nhập
     * 
     * @param loginname tên đăng nhập cần xóa
     */
    void deleteByUsername(String loginname);
} 