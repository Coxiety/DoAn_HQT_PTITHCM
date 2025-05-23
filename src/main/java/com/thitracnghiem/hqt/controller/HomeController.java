package com.thitracnghiem.hqt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thitracnghiem.hqt.model.TAIKHOAN;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model, HttpSession session, @RequestParam(required = false) String message) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        
        if (message != null) {
            model.addAttribute("message", message);
        }
        
        return "home";
    }
    
    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/";
    }
    
    @GetMapping("/error")
    public String handleError() {
        return "error/general";
    }
    
    @GetMapping("/access")
    public String access(HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            // Chưa đăng nhập, chuyển đến trang đăng nhập
            return "redirect:/login";
        }
        
        // Đã đăng nhập, điều hướng tùy theo vai trò
        String role = currentUser.getRole();
        
        if ("Sinhvien".equals(role)) {
            return "redirect:/sinhvien";
        } else if ("Giangvien".equals(role)) {
            return "redirect:/giaovien";
        } else if ("PGV".equals(role)) {
            return "redirect:/admin";
        } else {
            // Vai trò không xác định, quay lại trang chủ
            return "redirect:/?message=Vai trò không hợp lệ";
        }
    }
}