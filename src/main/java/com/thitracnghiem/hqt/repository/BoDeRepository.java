package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.BODE;
import java.util.List;

public interface BoDeRepository {
    List<BODE> findAll();
    BODE findByCauHoi(int CAUHOI);
    List<BODE> findByMaMH(String MAMH);
    List<BODE> findByMaMHAndTrinhDo(String MAMH, String TRINHDO);
    List<BODE> findByMaGV(String MAGV);
    List<BODE> getRandomQuestionsByMaMHAndTrinhDo(String MAMH, String TRINHDO, int SOCAUTHI);
    void save(BODE BODE);
    void update(BODE BODE);
    void delete(int CAUHOI);
    int countByMaMHAndTrinhDo(String MAMH, String TRINHDO);
} 