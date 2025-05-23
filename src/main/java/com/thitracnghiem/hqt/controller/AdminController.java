package com.thitracnghiem.hqt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thitracnghiem.hqt.service.UserService;
import com.thitracnghiem.hqt.model.TAIKHOAN;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public String adminDashboard(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("username", currentUser.getLoginname());
        model.addAttribute("role", currentUser.getRole());
        
        return "admin/dashboard";
    }
    
    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }
}
