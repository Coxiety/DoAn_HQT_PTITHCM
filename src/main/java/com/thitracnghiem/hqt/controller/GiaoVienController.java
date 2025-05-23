package com.thitracnghiem.hqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thitracnghiem.hqt.dto.GiaoVienDTO;
import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.model.GIAOVIEN;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.service.AuthenticationService;
import com.thitracnghiem.hqt.service.GiaoVienService;
import com.thitracnghiem.hqt.service.TaiKhoanService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/giaovien")
public class GiaoVienController {
    
    @Autowired
    private GiaoVienService giaoVienService;
    
    @Autowired
    private TaiKhoanService taiKhoanService;
    
    @Autowired
    private AuthenticationService authService;
    
    @GetMapping("")
    public String listGiaoVien(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<GIAOVIEN> giaoViens = giaoVienService.getAllGiaoVien();
        model.addAttribute("giaoViens", giaoViens);
        model.addAttribute("giaoVienDTO", new GiaoVienDTO()); // Thêm đối tượng DTO cho form modal
        return "admin/giaovien/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("giaoVienDTO", new GiaoVienDTO());
        return "admin/giaovien/add";
    }
    
    @PostMapping("/add")
    public String addGiaoVien(@ModelAttribute("giaoVienDTO") GiaoVienDTO giaoVienDTO, 
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Tạo giáo viên mới
            GIAOVIEN giaoVien = new GIAOVIEN();
            giaoVien.setHO(giaoVienDTO.getHo());
            giaoVien.setTEN(giaoVienDTO.getTen());
            giaoVien.setDIACHI(giaoVienDTO.getDiachi());
            giaoVien.setSODTLL(giaoVienDTO.getSodtll());
            
            // Lưu giáo viên và lấy mã giáo viên đã tạo
            String maGV = giaoVienService.addGiaoVien(giaoVien);
            
            // Tạo tài khoản cho giáo viên
            TAIKHOAN taiKhoan = new TAIKHOAN();
            taiKhoan.setLoginname(giaoVienDTO.getLoginname());
            // Hash mật khẩu trước khi lưu
            String hashedPassword = authService.hashPassword(giaoVienDTO.getPassword());
            taiKhoan.setPassword(hashedPassword);
            taiKhoan.setRole("Giangvien");
            taiKhoan.setMAGV_REF(maGV);
            
            taiKhoanService.addTaiKhoan(taiKhoan);
            
            redirectAttributes.addFlashAttribute("successMessage", "Thêm giáo viên thành công!");
            return "redirect:/admin/giaovien";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/giaovien";
        }
    }
    
    @PostMapping("/add-ajax")
    @ResponseBody
    public ResponseEntity<?> addGiaoVienAjax(@RequestParam("ho") String ho,
                                           @RequestParam("ten") String ten,
                                           @RequestParam("diachi") String diachi,
                                           @RequestParam("sodtll") String sodtll,
                                           @RequestParam("loginname") String loginname,
                                           @RequestParam("password") String password,
                                           HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            // Tạo giáo viên mới
            GIAOVIEN giaoVien = new GIAOVIEN();
            giaoVien.setHO(ho);
            giaoVien.setTEN(ten);
            giaoVien.setDIACHI(diachi);
            giaoVien.setSODTLL(sodtll);
            
            // Lưu giáo viên và lấy mã giáo viên đã tạo
            String maGV = giaoVienService.addGiaoVien(giaoVien);
            
            // Tạo tài khoản cho giáo viên
            TAIKHOAN taiKhoan = new TAIKHOAN();
            taiKhoan.setLoginname(loginname);
            // Hash mật khẩu trước khi lưu
            String hashedPassword = authService.hashPassword(password);
            taiKhoan.setPassword(hashedPassword);
            taiKhoan.setRole("Giangvien");
            taiKhoan.setMAGV_REF(maGV);
            
            taiKhoanService.addTaiKhoan(taiKhoan);
            
            return ResponseEntity.ok().body("Thêm giáo viên thành công!");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/get/{maGV}")
    @ResponseBody
    public ResponseEntity<?> getGiaoVien(@PathVariable("maGV") String maGV, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            GIAOVIEN giaoVien = giaoVienService.getGiaoVienByMaGV(maGV);
            if (giaoVien == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy giáo viên");
            }
            
            // Lấy thông tin tài khoản nếu có
            TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaGV(maGV);
            
            GiaoVienDTO giaoVienDTO = new GiaoVienDTO();
            giaoVienDTO.setMaGV(giaoVien.getMAGV());
            giaoVienDTO.setHo(giaoVien.getHO());
            giaoVienDTO.setTen(giaoVien.getTEN());
            giaoVienDTO.setDiachi(giaoVien.getDIACHI());
            giaoVienDTO.setSodtll(giaoVien.getSODTLL());
            
            if (taiKhoan != null) {
                giaoVienDTO.setLoginname(taiKhoan.getLoginname());
            }
            
            return ResponseEntity.ok().body(giaoVienDTO);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/edit-ajax")
    @ResponseBody
    public ResponseEntity<?> updateGiaoVienAjax(@RequestParam("maGV") String maGV,
                                              @RequestParam("ho") String ho,
                                              @RequestParam("ten") String ten,
                                              @RequestParam("diachi") String diachi,
                                              @RequestParam("sodtll") String sodtll,
                                              @RequestParam(value = "password", required = false) String password,
                                              HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            // Cập nhật thông tin giáo viên
            GIAOVIEN giaoVien = new GIAOVIEN();
            giaoVien.setMAGV(maGV);
            giaoVien.setHO(ho);
            giaoVien.setTEN(ten);
            giaoVien.setDIACHI(diachi);
            giaoVien.setSODTLL(sodtll);
            
            giaoVienService.updateGiaoVien(giaoVien);
            
            // Cập nhật mật khẩu nếu có
            if (password != null && !password.isEmpty()) {
                TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaGV(maGV);
                if (taiKhoan != null) {
                    // Hash mật khẩu mới
                    String hashedPassword = authService.hashPassword(password);
                    taiKhoan.setPassword(hashedPassword);
                    taiKhoanService.updateTaiKhoan(taiKhoan);
                }
            }
            
            return ResponseEntity.ok().body("Cập nhật giáo viên thành công!");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/edit/{maGV}")
    public String showEditForm(@PathVariable("maGV") String maGV, Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        GIAOVIEN giaoVien = giaoVienService.getGiaoVienByMaGV(maGV);
        if (giaoVien == null) {
            return "redirect:/admin/giaovien";
        }
        
        // Lấy thông tin tài khoản nếu có
        TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaGV(maGV);
        
        GiaoVienDTO giaoVienDTO = new GiaoVienDTO();
        giaoVienDTO.setMaGV(giaoVien.getMAGV());
        giaoVienDTO.setHo(giaoVien.getHO());
        giaoVienDTO.setTen(giaoVien.getTEN());
        giaoVienDTO.setDiachi(giaoVien.getDIACHI());
        giaoVienDTO.setSodtll(giaoVien.getSODTLL());
        
        if (taiKhoan != null) {
            giaoVienDTO.setLoginname(taiKhoan.getLoginname());
        }
        
        model.addAttribute("giaoVienDTO", giaoVienDTO);
        return "admin/giaovien/edit";
    }
    
    @PostMapping("/edit")
    public String updateGiaoVien(@ModelAttribute("giaoVienDTO") GiaoVienDTO giaoVienDTO, 
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Cập nhật thông tin giáo viên
            GIAOVIEN giaoVien = new GIAOVIEN();
            giaoVien.setMAGV(giaoVienDTO.getMaGV());
            giaoVien.setHO(giaoVienDTO.getHo());
            giaoVien.setTEN(giaoVienDTO.getTen());
            giaoVien.setDIACHI(giaoVienDTO.getDiachi());
            giaoVien.setSODTLL(giaoVienDTO.getSodtll());
            
            giaoVienService.updateGiaoVien(giaoVien);
            
            // Cập nhật tài khoản nếu có thay đổi mật khẩu
            if (giaoVienDTO.getPassword() != null && !giaoVienDTO.getPassword().isEmpty()) {
                TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaGV(giaoVienDTO.getMaGV());
                if (taiKhoan != null) {
                    // Hash mật khẩu mới
                    String hashedPassword = authService.hashPassword(giaoVienDTO.getPassword());
                    taiKhoan.setPassword(hashedPassword);
                    taiKhoanService.updateTaiKhoan(taiKhoan);
                }
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giáo viên thành công!");
            return "redirect:/admin/giaovien";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/giaovien/edit/" + giaoVienDTO.getMaGV();
        }
    }
    
    @GetMapping("/delete/{maGV}")
    public String deleteGiaoVien(@PathVariable("maGV") String maGV, 
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Xóa tài khoản liên kết với giáo viên (nếu có)
            TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaGV(maGV);
            if (taiKhoan != null) {
                taiKhoanService.deleteTaiKhoan(taiKhoan.getLoginname());
            }
            
            // Xóa giáo viên
            giaoVienService.deleteGiaoVien(maGV);
            
            redirectAttributes.addFlashAttribute("successMessage", "Xóa giáo viên thành công!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/admin/giaovien";
    }
} 