package com.thitracnghiem.hqt.controller;

import com.thitracnghiem.hqt.model.SINHVIEN;
import com.thitracnghiem.hqt.service.LopService;
import com.thitracnghiem.hqt.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    private final SinhVienService sinhVienService;
    private final LopService lopService;

    @Autowired
    public SinhVienController(SinhVienService sinhVienService, LopService lopService) {
        this.sinhVienService = sinhVienService;
        this.lopService = lopService;
    }

    @GetMapping
    public String getAllSinhVien(Model model) {
        model.addAttribute("sinhviens", sinhVienService.getAllSinhVien());
        return "sinhvien/list";
    }

    @GetMapping("/{maSV}")
    public String getSinhVienDetail(@PathVariable String maSV, Model model) {
        SINHVIEN sinhVien = sinhVienService.getSinhVienByMaSV(maSV);
        if (sinhVien == null) {
            return "redirect:/sinhvien";
        }
        model.addAttribute("sinhvien", sinhVien);
        return "sinhvien/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sinhvien", new SINHVIEN());
        model.addAttribute("lops", lopService.getAllLop());
        return "sinhvien/form";
    }

    @PostMapping("/add")
    public String addSinhVien(@ModelAttribute SINHVIEN sinhVien,
                             @RequestParam("ngaySinh") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngaySinh) {
        sinhVien.setNGAYSINH(ngaySinh);
        sinhVienService.createSinhVien(sinhVien);
        return "redirect:/sinhvien";
    }

    @GetMapping("/edit/{maSV}")
    public String showEditForm(@PathVariable String maSV, Model model) {
        SINHVIEN sinhVien = sinhVienService.getSinhVienByMaSV(maSV);
        if (sinhVien == null) {
            return "redirect:/sinhvien";
        }
        model.addAttribute("sinhvien", sinhVien);
        model.addAttribute("lops", lopService.getAllLop());
        return "sinhvien/form";
    }

    @PostMapping("/edit/{maSV}")
    public String updateSinhVien(@PathVariable String maSV, 
                                @ModelAttribute SINHVIEN sinhVien,
                                @RequestParam("ngaySinh") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngaySinh) {
        sinhVien.setMASV(maSV);
        sinhVien.setNGAYSINH(ngaySinh);
        sinhVienService.updateSinhVien(sinhVien);
        return "redirect:/sinhvien";
    }

    @GetMapping("/delete/{maSV}")
    public String deleteSinhVien(@PathVariable String maSV) {
        sinhVienService.deleteSinhVien(maSV);
        return "redirect:/sinhvien";
    }

    @GetMapping("/lop/{maLop}")
    public String getSinhVienByLop(@PathVariable String maLop, Model model) {
        model.addAttribute("sinhviens", sinhVienService.getSinhVienByMaLop(maLop));
        model.addAttribute("lop", lopService.getLopByMaLop(maLop));
        return "sinhvien/by-lop";
    }
} 