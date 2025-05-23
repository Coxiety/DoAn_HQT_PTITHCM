package com.thitracnghiem.hqt.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.exception.ErrorCodes;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.repository.TaiKhoanRepository;
import com.thitracnghiem.hqt.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    
    @Override
    public TAIKHOAN login(String username, String password) {
        // Tìm tài khoản theo username
        TAIKHOAN taiKhoan = taiKhoanRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản"));
        
        // Hash mật khẩu người dùng nhập vào
        String hashedPassword = hashPassword(password);
        
        // So sánh với mật khẩu đã lưu trong database
        if (!taiKhoan.getPassword().equals(hashedPassword)) {
            throw new BusinessException(ErrorCodes.INVALID_CREDENTIALS, "Mật khẩu không chính xác");
        }
        
        return taiKhoan;
    }
    
    /**
     * Mã hóa mật khẩu sử dụng SHA-256
     * 
     * @param password Mật khẩu cần mã hóa
     * @return Chuỗi đã mã hóa
     */
    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(ErrorCodes.OPERATION_FAILED, "Lỗi khi mã hóa mật khẩu");
        }
    }
}
