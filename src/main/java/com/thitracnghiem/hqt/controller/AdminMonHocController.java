package com.thitracnghiem.hqt.controller;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.model.MONHOC;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/monhoc")
public class AdminMonHocController {

    @Autowired
    private MonHocService monHocService;
    
    @GetMapping("")
    public String listMonHoc(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<MONHOC> monHocs = monHocService.getAllMonHoc();
        model.addAttribute("monHocs", monHocs);
        model.addAttribute("monHoc", new MONHOC());
        model.addAttribute("isNew", true);
        model.addAttribute("username", currentUser.getLoginname());
        
        return "admin/monhoc/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("monHoc", new MONHOC());
        model.addAttribute("isNew", true);
        model.addAttribute("username", currentUser.getLoginname());
        
        return "admin/monhoc/form";
    }
    
    @PostMapping("/save")
    public String saveMonHoc(@ModelAttribute MONHOC monHoc, 
                           @RequestParam(required = false, defaultValue = "true") boolean isNew,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            if (isNew) {
                monHocService.createMonHoc(monHoc);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm môn học thành công");
            } else {
                monHocService.updateMonHoc(monHoc);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật môn học thành công");
            }
            return "redirect:/admin/monhoc";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/monhoc";
        }
    }
    
    @GetMapping("/edit/{maMonHoc}")
    public String showEditForm(@PathVariable("maMonHoc") String maMonHoc, 
                             Model model, 
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            MONHOC monHoc = monHocService.getMonHocByMaMH(maMonHoc);
            model.addAttribute("monHoc", monHoc);
            model.addAttribute("isNew", false);
            model.addAttribute("username", currentUser.getLoginname());
            
            return "admin/monhoc/form";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/monhoc";
        }
    }
    
    @GetMapping("/delete/{maMonHoc}")
    public String deleteMonHoc(@PathVariable("maMonHoc") String maMonHoc, 
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            monHocService.deleteMonHoc(maMonHoc);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa môn học thành công");
            return "redirect:/admin/monhoc";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/monhoc";
        }
    }
    
    @GetMapping("/search")
    public String searchMonHoc(@RequestParam String keyword, Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<MONHOC> monHocs = monHocService.searchMonHoc(keyword);
        model.addAttribute("monHocs", monHocs);
        model.addAttribute("monHoc", new MONHOC());
        model.addAttribute("isNew", true);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("username", currentUser.getLoginname());
        
        return "admin/monhoc/list";
    }
}
