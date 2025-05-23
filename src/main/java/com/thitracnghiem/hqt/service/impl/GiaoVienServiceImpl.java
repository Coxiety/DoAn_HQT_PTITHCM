package com.thitracnghiem.hqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thitracnghiem.hqt.exception.BusinessException;
import com.thitracnghiem.hqt.exception.ErrorCodes;
import com.thitracnghiem.hqt.model.GIAOVIEN;
import com.thitracnghiem.hqt.repository.GiaoVienRepository;
import com.thitracnghiem.hqt.service.GiaoVienService;

@Service
public class GiaoVienServiceImpl implements GiaoVienService {

    @Autowired
    private GiaoVienRepository giaoVienRepository;
    
    @Override
    public List<GIAOVIEN> getAllGiaoVien() {
        return giaoVienRepository.findAll();
    }

    @Override
    public GIAOVIEN getGiaoVienByMaGV(String maGV) {
        return giaoVienRepository.findById(maGV)
                .orElseThrow(() -> new BusinessException(ErrorCodes.ENTITY_NOT_FOUND, "Không tìm thấy giáo viên với mã " + maGV));
    }

    @Override
    public String addGiaoVien(GIAOVIEN giaoVien) {
        if (giaoVien == null) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Thông tin giáo viên không được để trống");
        }
        
        // Validate thông tin giáo viên
        validateGiaoVien(giaoVien);
        
        return giaoVienRepository.save(giaoVien);
    }

    @Override
    public void updateGiaoVien(GIAOVIEN giaoVien) {
        if (giaoVien == null || giaoVien.getMAGV() == null) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Thông tin giáo viên không hợp lệ");
        }
        
        // Kiểm tra giáo viên tồn tại
        giaoVienRepository.findById(giaoVien.getMAGV())
                .orElseThrow(() -> new BusinessException(ErrorCodes.ENTITY_NOT_FOUND, "Không tìm thấy giáo viên với mã " + giaoVien.getMAGV()));
        
        // Validate thông tin giáo viên
        validateGiaoVien(giaoVien);
        
        giaoVienRepository.update(giaoVien);
    }

    @Override
    public void deleteGiaoVien(String maGV) {
        if (maGV == null || maGV.isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Mã giáo viên không hợp lệ");
        }
        
        // Kiểm tra giáo viên tồn tại
        giaoVienRepository.findById(maGV)
                .orElseThrow(() -> new BusinessException(ErrorCodes.ENTITY_NOT_FOUND, "Không tìm thấy giáo viên với mã " + maGV));
        
        giaoVienRepository.deleteById(maGV);
    }
    
    /**
     * Validate thông tin giáo viên
     * 
     * @param giaoVien thông tin cần validate
     */
    private void validateGiaoVien(GIAOVIEN giaoVien) {
        if (giaoVien.getHO() == null || giaoVien.getHO().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Họ giáo viên không được để trống");
        }
        
        if (giaoVien.getTEN() == null || giaoVien.getTEN().trim().isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_REQUEST, "Tên giáo viên không được để trống");
        }
    }
} 