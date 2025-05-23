package com.thitracnghiem.hqt.service.impl;

import com.thitracnghiem.hqt.model.SINHVIEN;
import com.thitracnghiem.hqt.repository.SinhVienRepository;
import com.thitracnghiem.hqt.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    private final SinhVienRepository sinhVienRepository;

    @Autowired
    public SinhVienServiceImpl(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    @Override
    public List<SINHVIEN> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    @Override
    public SINHVIEN getSinhVienByMaSV(String maSV) {
        return sinhVienRepository.findByMaSV(maSV);
    }

    @Override
    public List<SINHVIEN> getSinhVienByHoTen(String ho, String ten) {
        return sinhVienRepository.findByHoTen(ho, ten);
    }

    @Override
    public List<SINHVIEN> getSinhVienByMaLop(String maLop) {
        return sinhVienRepository.findByMaLop(maLop);
    }

    @Override
    public void createSinhVien(SINHVIEN sinhVien) {
        sinhVienRepository.save(sinhVien);
    }

    @Override
    public void updateSinhVien(SINHVIEN sinhVien) {
        sinhVienRepository.update(sinhVien);
    }

    @Override
    public void deleteSinhVien(String maSV) {
        sinhVienRepository.delete(maSV);
    }
} 