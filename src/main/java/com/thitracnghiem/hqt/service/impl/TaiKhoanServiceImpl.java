package com.thitracnghiem.hqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.exception.ErrorCodes;
import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.repository.TaiKhoanRepository;
import com.thitracnghiem.hqt.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    
    @Override
    public List<TAIKHOAN> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public TAIKHOAN getTaiKhoanByLoginname(String loginname) {
        return taiKhoanRepository.findByUsername(loginname)
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản với tên đăng nhập " + loginname));
    }

    @Override
    public TAIKHOAN getTaiKhoanByMaGV(String maGV) {
        return taiKhoanRepository.findByMaGV(maGV)
                .orElse(null); // Có thể không có tài khoản liên kết với giáo viên
    }

    @Override
    public TAIKHOAN getTaiKhoanByMaSV(String maSV) {
        return taiKhoanRepository.findByMaSV(maSV)
                .orElse(null); // Có thể không có tài khoản liên kết với sinh viên
    }

    @Override
    public void addTaiKhoan(TAIKHOAN taiKhoan) {
        if (taiKhoan == null || taiKhoan.getLoginname() == null || taiKhoan.getLoginname().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Tên đăng nhập không được để trống");
        }
        
        // Kiểm tra tài khoản đã tồn tại
        if (taiKhoanRepository.findByUsername(taiKhoan.getLoginname()).isPresent()) {
            throw new BusinessException(ErrorCodes.DUPLICATE_ENTITY, "Tài khoản với tên đăng nhập " + taiKhoan.getLoginname() + " đã tồn tại");
        }
        
        // Validate thông tin tài khoản
        validateTaiKhoan(taiKhoan);
        
        taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public void updateTaiKhoan(TAIKHOAN taiKhoan) {
        if (taiKhoan == null || taiKhoan.getLoginname() == null || taiKhoan.getLoginname().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Thông tin tài khoản không hợp lệ");
        }
        
        // Kiểm tra tài khoản tồn tại
        taiKhoanRepository.findByUsername(taiKhoan.getLoginname())
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản với tên đăng nhập " + taiKhoan.getLoginname()));
        
        // Validate thông tin tài khoản
        validateTaiKhoan(taiKhoan);
        
        taiKhoanRepository.update(taiKhoan);
    }

    @Override
    public void deleteTaiKhoan(String loginname) {
        if (loginname == null || loginname.trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Tên đăng nhập không hợp lệ");
        }
        
        // Kiểm tra tài khoản tồn tại
        taiKhoanRepository.findByUsername(loginname)
                .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "Không tìm thấy tài khoản với tên đăng nhập " + loginname));
        
        taiKhoanRepository.deleteByUsername(loginname);
    }
    
    /**
     * Validate thông tin tài khoản
     * 
     * @param taiKhoan thông tin cần validate
     */
    private void validateTaiKhoan(TAIKHOAN taiKhoan) {
        if (taiKhoan.getPassword() == null || taiKhoan.getPassword().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Mật khẩu không được để trống");
        }
        
        if (taiKhoan.getRole() == null || taiKhoan.getRole().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Vai trò không được để trống");
        }
    }
}
