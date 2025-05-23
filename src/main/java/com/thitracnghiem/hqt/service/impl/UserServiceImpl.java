package com.thitracnghiem.hqt.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.exception.ErrorCodes;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.repository.TaiKhoanRepository;
import com.thitracnghiem.hqt.repository.impl.TaiKhoanRepositoryImpl;
import com.thitracnghiem.hqt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    
    @Autowired
    private TaiKhoanRepositoryImpl taiKhoanRepositoryImpl;
    
    @Override
    public List<TAIKHOAN> getAllUsers() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public TAIKHOAN getUserById(String id) {
        return taiKhoanRepository.findByUsername(id)
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản với ID " + id));
    }

    @Override
    public TAIKHOAN createUser(TAIKHOAN user) {
        // Kiểm tra xem user đã tồn tại chưa
        if (taiKhoanRepositoryImpl.existsByLoginName(user.getLoginname())) {
            throw new BusinessException(ErrorCodes.USER_ALREADY_EXISTS, "Tài khoản đã tồn tại");
        }
        
        // Hash mật khẩu trước khi lưu
        user.setPassword(hashPassword(user.getPassword()));
        
        // Lưu user mới
        taiKhoanRepository.save(user);
        
        return user;
    }

    @Override
    public TAIKHOAN updateUser(TAIKHOAN user) {
        // Kiểm tra xem user có tồn tại không
        if (!taiKhoanRepositoryImpl.existsByLoginName(user.getLoginname())) {
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản");
        }
        
        // Hash mật khẩu nếu có thay đổi
        TAIKHOAN existingUser = taiKhoanRepository.findByUsername(user.getLoginname())
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản"));
        
        if (!existingUser.getPassword().equals(user.getPassword())) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        
        // Cập nhật user
        taiKhoanRepository.update(user);
        
        return user;
    }

    @Override
    public void deleteUser(String id) {
        // Kiểm tra xem user có tồn tại không
        if (!taiKhoanRepositoryImpl.existsByLoginName(id)) {
            throw new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản");
        }
        
        // Xóa user
        taiKhoanRepository.deleteByUsername(id);
    }
    
    @Override
    public boolean isPasswordValid(String rawPassword, String encodedPassword) {
        String hashedInput = hashPassword(rawPassword);
        return hashedInput.equals(encodedPassword);
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(ErrorCodes.SYSTEM_ERROR, "Lỗi khi mã hóa mật khẩu");
        }
    }
}
