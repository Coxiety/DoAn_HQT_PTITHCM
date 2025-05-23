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
public class MonHocController {

    @Autowired
    private MonHocService monHocService;
    
    // Legacy routes
    @GetMapping("/monhoc")
    public String getAllMonHoc(Model model) {
        model.addAttribute("monhocs", monHocService.getAllMonHoc());
        return "monhoc/list";
    }

    @GetMapping("/{maMH}")
    public String getMonHocDetail(@PathVariable String maMH, Model model) {
        MONHOC monHoc = monHocService.getMonHocByMaMH(maMH);
        if (monHoc == null) {
            return "redirect:/monhoc";
        }
        model.addAttribute("monhoc", monHoc);
        return "monhoc/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("monhoc", new MONHOC());
        return "monhoc/form";
    }

    @PostMapping("/add")
    public String addMonHoc(@ModelAttribute MONHOC monHoc) {
        monHocService.createMonHoc(monHoc);
        return "redirect:/monhoc";
    }

    @GetMapping("/edit/{maMH}")
    public String showEditForm(@PathVariable String maMH, Model model) {
        MONHOC monHoc = monHocService.getMonHocByMaMH(maMH);
        if (monHoc == null) {
            return "redirect:/monhoc";
        }
        model.addAttribute("monhoc", monHoc);
        return "monhoc/form";
    }

    @PostMapping("/edit/{maMH}")
    public String updateMonHoc(@PathVariable String maMH, @ModelAttribute MONHOC monHoc) {
        monHoc.setMAMH(maMH);
        monHocService.updateMonHoc(monHoc);
        return "redirect:/monhoc";
    }

    @GetMapping("/delete/{maMH}")
    public String deleteMonHoc(@PathVariable String maMH) {
        monHocService.deleteMonHoc(maMH);
        return "redirect:/monhoc";
    }
} 