package com.thitracnghiem.hqt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thitracnghiem.hqt.dto.LoginRequest;
import com.thitracnghiem.hqt.dto.LoginResponse;
import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.service.AuthenticationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;
    
    /**
     * Xử lý yêu cầu đăng nhập
     * 
     * @param loginRequest thông tin đăng nhập gồm username và password
     * @return kết quả đăng nhập dưới dạng LoginResponse
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        
        try {
            // Gọi service để xử lý đăng nhập
            TAIKHOAN taiKhoan = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            
            // Nếu đăng nhập thành công, thiết lập thông tin phản hồi
            response.setUsername(taiKhoan.getLoginname());
            response.setRole(taiKhoan.getRole());
            response.setSuccess(true);
            response.setMessage("Đăng nhập thành công");
            
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            // Xử lý trường hợp đăng nhập thất bại
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * API đăng ký tài khoản mới
     * Lưu ý: Khi đăng ký, mật khẩu cũng cần được hash trước khi lưu vào DB
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody TAIKHOAN taiKhoan) {
        LoginResponse response = new LoginResponse();
        
        try {
            // Hash mật khẩu trước khi lưu vào database
            String rawPassword = taiKhoan.getPassword();
            String hashedPassword = authService.hashPassword(rawPassword);
            taiKhoan.setPassword(hashedPassword);
            
            // TODO: Thêm logic lưu tài khoản vào database
            // taiKhoanRepository.save(taiKhoan);
            
            response.setSuccess(true);
            response.setMessage("Đăng ký tài khoản thành công");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Đăng ký tài khoản thất bại: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}