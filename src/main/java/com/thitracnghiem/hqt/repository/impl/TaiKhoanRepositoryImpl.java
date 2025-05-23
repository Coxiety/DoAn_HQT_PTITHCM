package com.thitracnghiem.hqt.repository.impl;

import com.thitracnghiem.hqt.model.TAIKHOAN;
import com.thitracnghiem.hqt.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TaiKhoanRepositoryImpl implements TaiKhoanRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<TAIKHOAN> rowMapper = new RowMapper<TAIKHOAN>() {
        @Override
        public TAIKHOAN mapRow(ResultSet rs, int rowNum) throws SQLException {
            TAIKHOAN taiKhoan = new TAIKHOAN();
            taiKhoan.setLoginname(rs.getString("LOGINNAME"));
            taiKhoan.setPassword(rs.getString("PASSWORD"));
            taiKhoan.setRole(rs.getString("ROLE"));
            taiKhoan.setMagv(rs.getString("MAGV_REF"));
            taiKhoan.setMasv(rs.getString("MASV_REF"));
            return taiKhoan;
        }
    };

    @Override
    public List<TAIKHOAN> findAll() {
        String sql = "EXEC sp_TAIKHOAN_GetAll";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    @Override
    public Optional<TAIKHOAN> findByUsername(String loginname) {
        String sql = "EXEC sp_TAIKHOAN_GetByLoginName @LOGINNAME = ?";
        List<TAIKHOAN> results = jdbcTemplate.query(sql, rowMapper, loginname);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<TAIKHOAN> findByMaGV(String maGV) {
        String sql = "EXEC sp_TAIKHOAN_GetByMaGV @MAGV = ?";
        List<TAIKHOAN> results = jdbcTemplate.query(sql, rowMapper, maGV);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<TAIKHOAN> findByMaSV(String maSV) {
        String sql = "EXEC sp_TAIKHOAN_GetByMaSV @MASV = ?";
        List<TAIKHOAN> results = jdbcTemplate.query(sql, rowMapper, maSV);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void save(TAIKHOAN taiKhoan) {
        String sql = "EXEC sp_TAIKHOAN_Insert @LOGINNAME = ?, @PASSWORD = ?, @ROLE = ?, @MAGV = ?, @MASV = ?";
        jdbcTemplate.update(sql,
                taiKhoan.getLoginname(),
                taiKhoan.getPassword(),
                taiKhoan.getRole(),
                taiKhoan.getMagv(),
                taiKhoan.getMasv());
    }

    @Override
    public void update(TAIKHOAN taiKhoan) {
        String sql = "EXEC sp_TAIKHOAN_Update @LOGINNAME = ?, @PASSWORD = ?, @ROLE = ?, @MAGV = ?, @MASV = ?";
        jdbcTemplate.update(sql,
                taiKhoan.getLoginname(),
                taiKhoan.getPassword(),
                taiKhoan.getRole(),
                taiKhoan.getMagv(),
                taiKhoan.getMasv());
    }

    @Override
    public void deleteByUsername(String loginname) {
        String sql = "EXEC sp_TAIKHOAN_Delete @LOGINNAME = ?";
        jdbcTemplate.update(sql, loginname);
    }
    
    // Các phương thức bổ sung không có trong interface
    
    public boolean validateLogin(String loginname, String password) {
        String sql = "EXEC sp_TAIKHOAN_ValidateLogin @LOGINNAME = ?, @PASSWORD = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, loginname, password);
        return result != null && result == 1;
    }

    public boolean existsByLoginName(String loginname) {
        String sql = "EXEC sp_TAIKHOAN_ExistsByLoginName @LOGINNAME = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, loginname);
        return result != null && result == 1;
    }
}