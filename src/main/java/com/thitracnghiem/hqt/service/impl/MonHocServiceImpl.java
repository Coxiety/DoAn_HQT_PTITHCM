package com.thitracnghiem.hqt.service.impl;

import com.thitracnghiem.hqt.model.MONHOC;
import com.thitracnghiem.hqt.repository.MonHocRepository;
import com.thitracnghiem.hqt.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonHocServiceImpl implements MonHocService {

    private final MonHocRepository monHocRepository;

    @Autowired
    public MonHocServiceImpl(MonHocRepository monHocRepository) {
        this.monHocRepository = monHocRepository;
    }

    @Override
    public List<MONHOC> getAllMonHoc() {
        return monHocRepository.findAll();
    }

    @Override
    public MONHOC getMonHocByMaMH(String maMH) {
        return monHocRepository.findByMaMH(maMH);
    }

    @Override
    public MONHOC getMonHocByTenMH(String tenMH) {
        return monHocRepository.findByTenMH(tenMH);
    }

    @Override
    public void createMonHoc(MONHOC monHoc) {
        monHocRepository.save(monHoc);
    }

    @Override
    public void updateMonHoc(MONHOC monHoc) {
        monHocRepository.update(monHoc);
    }    @Override
    public void deleteMonHoc(String maMH) {
        monHocRepository.delete(maMH);
    }
    
    @Override
    public List<MONHOC> searchMonHoc(String keyword) {
        return monHocRepository.search(keyword);
    }
}