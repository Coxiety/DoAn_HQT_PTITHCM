package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.LOP;
import java.util.List;

public interface LopRepository {
    List<LOP> findAll();
    LOP findByMaLop(String MALOP);
    LOP findByTenLop(String TENLOP);
    void save(LOP LOP);
    void update(LOP LOP);
    void delete(String MALOP);
} 