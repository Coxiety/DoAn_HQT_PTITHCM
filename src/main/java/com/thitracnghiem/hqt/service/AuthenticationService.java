package com.thitracnghiem.hqt.service;

import com.thitracnghiem.hqt.model.TAIKHOAN;

public interface AuthenticationService {
    
    /**
     * Đăng nhập người dùng
     * 
     * @param username Tên đăng nhập
     * @param password Mật khẩu chưa mã hóa
     * @return TAIKHOAN nếu xác thực thành công
     */
    TAIKHOAN login(String username, String password);
    
    /**
     * Mã hóa mật khẩu
     * 
     * @param password Mật khẩu cần mã hóa
     * @return Chuỗi đã mã hóa
     */
    String hashPassword(String password);
}
