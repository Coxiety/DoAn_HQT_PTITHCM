package com.thitracnghiem.hqt.repository;

import com.thitracnghiem.hqt.model.GIAOVIEN_DANGKY;
import java.util.List;
import java.time.LocalDateTime;

public interface GiaoVienDangKyRepository {
    List<GIAOVIEN_DANGKY> findAll();
    GIAOVIEN_DANGKY findByMaLopAndMaMHAndLan(String MALOP, String MAMH, int LAN);
    List<GIAOVIEN_DANGKY> findByMaGV(String MAGV);
    List<GIAOVIEN_DANGKY> findByMaLop(String MALOP);
    List<GIAOVIEN_DANGKY> findByMaMH(String MAMH);
    List<GIAOVIEN_DANGKY> findByNgayThi(LocalDateTime NGAYTHI);
    void save(GIAOVIEN_DANGKY GIAOVIEN_DANGKY);
    void update(GIAOVIEN_DANGKY GIAOVIEN_DANGKY);
    void delete(String MALOP, String MAMH, int LAN);
    boolean existsByMaLopAndMaMHAndLan(String MALOP, String MAMH, int LAN);
} 