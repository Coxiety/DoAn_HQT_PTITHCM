package com.thitracnghiem.hqt.service;

import com.thitracnghiem.hqt.model.LOP;
import java.util.List;

public interface LopService {
    List<LOP> getAllLop();
    LOP getLopByMaLop(String maLop);
    LOP getLopByTenLop(String tenLop);
    void createLop(LOP lop);
    void updateLop(LOP lop);
    void deleteLop(String maLop);
}
