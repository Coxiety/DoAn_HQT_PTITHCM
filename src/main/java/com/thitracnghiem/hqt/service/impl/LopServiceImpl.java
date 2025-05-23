package com.thitracnghiem.hqt.service.impl;

import com.thitracnghiem.hqt.model.LOP;
import com.thitracnghiem.hqt.repository.LopRepository;
import com.thitracnghiem.hqt.service.LopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopServiceImpl implements LopService {

    private final LopRepository lopRepository;

    @Autowired
    public LopServiceImpl(LopRepository lopRepository) {
        this.lopRepository = lopRepository;
    }

    @Override
    public List<LOP> getAllLop() {
        return lopRepository.findAll();
    }

    @Override
    public LOP getLopByMaLop(String maLop) {
        return lopRepository.findByMaLop(maLop);
    }

    @Override
    public LOP getLopByTenLop(String tenLop) {
        return lopRepository.findByTenLop(tenLop);
    }

    @Override
    public void createLop(LOP lop) {
        lopRepository.save(lop);
    }

    @Override
    public void updateLop(LOP lop) {
        lopRepository.update(lop);
    }

    @Override
    public void deleteLop(String maLop) {
        lopRepository.delete(maLop);
    }
} 