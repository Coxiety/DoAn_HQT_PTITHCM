package com.thitracnghiem.hqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.thitracnghiem.hqt.dto.SinhVienDTO;
import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.model.SINHVIEN;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.service.AuthenticationService;
import com.thitracnghiem.hqt.service.LopService;
import com.thitracnghiem.hqt.service.SinhVienService;
import com.thitracnghiem.hqt.service.TaiKhoanService;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin/sinhvien")
public class AdminSinhVienController {
    
    @Autowired
    private SinhVienService sinhVienService;
    
    @Autowired
    private TaiKhoanService taiKhoanService;
    
    @Autowired
    private AuthenticationService authService;
    
    @Autowired
    private LopService lopService;
    
    @GetMapping("")
    public String listSinhVien(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<SINHVIEN> sinhViens = sinhVienService.getAllSinhVien();
        model.addAttribute("sinhViens", sinhViens);
        model.addAttribute("sinhVienDTO", new SinhVienDTO()); // Thêm đối tượng DTO cho form modal
        model.addAttribute("lops", lopService.getAllLop()); // Thêm danh sách lớp
        return "admin/sinhvien/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("sinhVienDTO", new SinhVienDTO());
        model.addAttribute("lops", lopService.getAllLop());
        return "admin/sinhvien/add";
    }
    
    @PostMapping("/add")
    public String addSinhVien(@ModelAttribute("sinhVienDTO") SinhVienDTO sinhVienDTO, 
                              @RequestParam("ngaySinh") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngaySinh,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Tạo sinh viên mới
            SINHVIEN sinhVien = new SINHVIEN();
            sinhVien.setHO(sinhVienDTO.getHo());
            sinhVien.setTEN(sinhVienDTO.getTen());
            sinhVien.setNGAYSINH(ngaySinh);
            sinhVien.setDIACHI(sinhVienDTO.getDiaChi());
            sinhVien.setMALOP(sinhVienDTO.getMaLop());
            
            // Lưu sinh viên và lấy mã sinh viên đã tạo
            sinhVienService.createSinhVien(sinhVien);
            String maSV = sinhVien.getMASV();
            
            // Tạo tài khoản cho sinh viên
            TAIKHOAN taiKhoan = new TAIKHOAN();
            taiKhoan.setLoginname(sinhVienDTO.getLoginname());
            // Hash mật khẩu trước khi lưu
            String hashedPassword = authService.hashPassword(sinhVienDTO.getPassword());
            taiKhoan.setPassword(hashedPassword);
            taiKhoan.setRole("Sinhvien");
            taiKhoan.setMASV_REF(maSV);
            
            taiKhoanService.addTaiKhoan(taiKhoan);
            
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sinh viên thành công!");
            return "redirect:/admin/sinhvien";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/sinhvien";
        }
    }
    
    @PostMapping("/add-ajax")
    @ResponseBody
    public ResponseEntity<?> addSinhVienAjax(@RequestParam("ho") String ho,
                                           @RequestParam("ten") String ten,
                                           @RequestParam("ngaySinh") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngaySinh,
                                           @RequestParam("diaChi") String diaChi,
                                           @RequestParam("maLop") String maLop,
                                           @RequestParam("loginname") String loginname,
                                           @RequestParam("password") String password,
                                           HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            // Tạo sinh viên mới
            SINHVIEN sinhVien = new SINHVIEN();
            sinhVien.setHO(ho);
            sinhVien.setTEN(ten);
            sinhVien.setNGAYSINH(ngaySinh);
            sinhVien.setDIACHI(diaChi);
            sinhVien.setMALOP(maLop);
            
            // Lưu sinh viên và lấy mã sinh viên đã tạo
            sinhVienService.createSinhVien(sinhVien);
            String maSV = sinhVien.getMASV();
            
            // Tạo tài khoản cho sinh viên
            TAIKHOAN taiKhoan = new TAIKHOAN();
            taiKhoan.setLoginname(loginname);
            // Hash mật khẩu trước khi lưu
            String hashedPassword = authService.hashPassword(password);
            taiKhoan.setPassword(hashedPassword);
            taiKhoan.setRole("Sinhvien");
            taiKhoan.setMASV_REF(maSV);
            
            taiKhoanService.addTaiKhoan(taiKhoan);
            
            return ResponseEntity.ok().body("Thêm sinh viên thành công!");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/get/{maSV}")
    @ResponseBody
    public ResponseEntity<?> getSinhVien(@PathVariable("maSV") String maSV, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            SINHVIEN sinhVien = sinhVienService.getSinhVienByMaSV(maSV);
            if (sinhVien == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy sinh viên");
            }
            
            // Lấy thông tin tài khoản nếu có
            TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaSV(maSV);
            
            SinhVienDTO sinhVienDTO = new SinhVienDTO();
            sinhVienDTO.setMaSV(sinhVien.getMASV());
            sinhVienDTO.setHo(sinhVien.getHO());
            sinhVienDTO.setTen(sinhVien.getTEN());
            sinhVienDTO.setNgaySinh(sinhVien.getNGAYSINH());
            sinhVienDTO.setDiaChi(sinhVien.getDIACHI());
            sinhVienDTO.setMaLop(sinhVien.getMALOP());
            
            if (taiKhoan != null) {
                sinhVienDTO.setLoginname(taiKhoan.getLoginname());
            }
            
            return ResponseEntity.ok().body(sinhVienDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/edit-ajax")
    @ResponseBody
    public ResponseEntity<?> updateSinhVienAjax(@RequestParam("maSV") String maSV,
                                              @RequestParam("ho") String ho,
                                              @RequestParam("ten") String ten,
                                              @RequestParam("ngaySinh") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngaySinh,
                                              @RequestParam("diaChi") String diaChi,
                                              @RequestParam("maLop") String maLop,
                                              @RequestParam(value = "password", required = false) String password,
                                              HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return ResponseEntity.badRequest().body("Không có quyền thực hiện thao tác này");
        }
        
        try {
            // Cập nhật thông tin sinh viên
            SINHVIEN sinhVien = new SINHVIEN();
            sinhVien.setMASV(maSV);
            sinhVien.setHO(ho);
            sinhVien.setTEN(ten);
            sinhVien.setNGAYSINH(ngaySinh);
            sinhVien.setDIACHI(diaChi);
            sinhVien.setMALOP(maLop);
            
            sinhVienService.updateSinhVien(sinhVien);
            
            // Cập nhật mật khẩu nếu có
            if (password != null && !password.isEmpty()) {
                TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaSV(maSV);
                if (taiKhoan != null) {
                    // Hash mật khẩu mới
                    String hashedPassword = authService.hashPassword(password);
                    taiKhoan.setPassword(hashedPassword);
                    taiKhoanService.updateTaiKhoan(taiKhoan);
                }
            }
            
            return ResponseEntity.ok().body("Cập nhật sinh viên thành công!");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/edit/{maSV}")
    public String showEditForm(@PathVariable("maSV") String maSV, Model model, HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        SINHVIEN sinhVien = sinhVienService.getSinhVienByMaSV(maSV);
        if (sinhVien == null) {
            return "redirect:/admin/sinhvien";
        }
        
        // Lấy thông tin tài khoản nếu có
        TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaSV(maSV);
        
        SinhVienDTO sinhVienDTO = new SinhVienDTO();
        sinhVienDTO.setMaSV(sinhVien.getMASV());
        sinhVienDTO.setHo(sinhVien.getHO());
        sinhVienDTO.setTen(sinhVien.getTEN());
        sinhVienDTO.setNgaySinh(sinhVien.getNGAYSINH());
        sinhVienDTO.setDiaChi(sinhVien.getDIACHI());
        sinhVienDTO.setMaLop(sinhVien.getMALOP());
        
        if (taiKhoan != null) {
            sinhVienDTO.setLoginname(taiKhoan.getLoginname());
        }
        
        model.addAttribute("sinhVienDTO", sinhVienDTO);
        model.addAttribute("lops", lopService.getAllLop());
        return "admin/sinhvien/edit";
    }
    
    @PostMapping("/edit")
    public String updateSinhVien(@ModelAttribute("sinhVienDTO") SinhVienDTO sinhVienDTO, 
                                @RequestParam("ngaySinh") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngaySinh,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Cập nhật thông tin sinh viên
            SINHVIEN sinhVien = new SINHVIEN();
            sinhVien.setMASV(sinhVienDTO.getMaSV());
            sinhVien.setHO(sinhVienDTO.getHo());
            sinhVien.setTEN(sinhVienDTO.getTen());
            sinhVien.setNGAYSINH(ngaySinh);
            sinhVien.setDIACHI(sinhVienDTO.getDiaChi());
            sinhVien.setMALOP(sinhVienDTO.getMaLop());
            
            sinhVienService.updateSinhVien(sinhVien);
            
            // Cập nhật tài khoản nếu có thay đổi mật khẩu
            if (sinhVienDTO.getPassword() != null && !sinhVienDTO.getPassword().isEmpty()) {
                TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaSV(sinhVienDTO.getMaSV());
                if (taiKhoan != null) {
                    // Hash mật khẩu mới
                    String hashedPassword = authService.hashPassword(sinhVienDTO.getPassword());
                    taiKhoan.setPassword(hashedPassword);
                    taiKhoanService.updateTaiKhoan(taiKhoan);
                }
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sinh viên thành công!");
            return "redirect:/admin/sinhvien";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/sinhvien/edit/" + sinhVienDTO.getMaSV();
        }
    }
    
    @GetMapping("/delete/{maSV}")
    public String deleteSinhVien(@PathVariable("maSV") String maSV, 
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        // Kiểm tra phân quyền
        TAIKHOAN currentUser = (TAIKHOAN) session.getAttribute("currentUser");
        if (currentUser == null || !"PGV".equals(currentUser.getRole())) {
            return "redirect:/login?error=unauthorized";
        }
        
        try {
            // Xóa tài khoản liên kết với sinh viên (nếu có)
            TAIKHOAN taiKhoan = taiKhoanService.getTaiKhoanByMaSV(maSV);
            if (taiKhoan != null) {
                taiKhoanService.deleteTaiKhoan(taiKhoan.getLoginname());
            }
            
            // Xóa sinh viên
            sinhVienService.deleteSinhVien(maSV);
            
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/admin/sinhvien";
    }
} 