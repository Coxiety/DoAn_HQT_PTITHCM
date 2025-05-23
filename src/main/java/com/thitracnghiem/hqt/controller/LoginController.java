package com.thitracnghiem.hqt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.service.AuthenticationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authService;
    
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            if ("unauthorized".equals(error)) {
                model.addAttribute("errorMessage", "Bạn không có quyền truy cập trang này");
            } else {
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng");
            }
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       @RequestParam(required = false) String userType,
                       HttpSession session,
                       Model model) {
        try {
            TAIKHOAN user = authService.login(username, password);
            
            // Lưu thông tin người dùng vào session
            session.setAttribute("currentUser", user);
            session.setAttribute("userType", userType); // Lưu loại người dùng
            
            // Chuyển hướng theo vai trò
            String role = user.getRole();
            if ("PGV".equals(role)) {
                return "redirect:/admin";
            } else if ("Giangvien".equals(role)) {
                return "redirect:/giangvien";
            } else if ("Sinhvien".equals(role)) {
                return "redirect:/sinhvien";
            } else {
                return "redirect:/home";
            }
            
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi đăng nhập: " + e.getMessage());
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
