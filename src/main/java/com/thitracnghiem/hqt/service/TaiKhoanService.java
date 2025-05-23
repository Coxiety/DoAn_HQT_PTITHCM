package com.thitracnghiem.hqt.service;

import java.util.List;

import com.thitracnghiem.hqt.model.TAIKHOAN;

public interface TaiKhoanService {
    
    /**
     * Lấy danh sách tất cả tài khoản
     * 
     * @return danh sách tài khoản
     */
    List<TAIKHOAN> getAllTaiKhoan();
    
    /**
     * Lấy thông tin tài khoản theo loginname
     * 
     * @param loginname tên đăng nhập
     * @return thông tin tài khoản
     */
    TAIKHOAN getTaiKhoanByLoginname(String loginname);
    
    /**
     * Lấy thông tin tài khoản theo mã giáo viên
     * 
     * @param maGV mã giáo viên
     * @return thông tin tài khoản
     */
    TAIKHOAN getTaiKhoanByMaGV(String maGV);
    
    /**
     * Lấy thông tin tài khoản theo mã sinh viên
     * 
     * @param maSV mã sinh viên
     * @return thông tin tài khoản
     */
    TAIKHOAN getTaiKhoanByMaSV(String maSV);
    
    /**
     * Thêm tài khoản mới
     * 
     * @param taiKhoan thông tin tài khoản cần thêm
     */
    void addTaiKhoan(TAIKHOAN taiKhoan);
    
    /**
     * Cập nhật thông tin tài khoản
     * 
     * @param taiKhoan thông tin tài khoản cần cập nhật
     */
    void updateTaiKhoan(TAIKHOAN taiKhoan);
    
    /**
     * Xóa tài khoản theo loginname
     * 
     * @param loginname tên đăng nhập cần xóa
     */
    void deleteTaiKhoan(String loginname);
}
