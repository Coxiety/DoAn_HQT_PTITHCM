package com.thitracnghiem.hqt.repository.impl;

import com.thitracnghiem.hqt.model.SINHVIEN;
import com.thitracnghiem.hqt.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SinhVienRepositoryImpl implements SinhVienRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getAllSinhVienProc;
    private SimpleJdbcCall getByMaSVProc;
    private SimpleJdbcCall getByHoTenProc;
    private SimpleJdbcCall getByMaLopProc;
    private SimpleJdbcCall insertSinhVienProc;
    private SimpleJdbcCall updateSinhVienProc;
    private SimpleJdbcCall deleteSinhVienProc;
    private SimpleJdbcCall getNewMaSVProc;

    @Autowired
    public SinhVienRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Khởi tạo các SimpleJdbcCall cho stored procedures
        this.getAllSinhVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_GetAll")
                .returningResultSet("sinhviens", sinhVienRowMapper);
                
        this.getByMaSVProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_GetByMaSV")
                .declareParameters(new SqlParameter("MASV", Types.NCHAR))
                .returningResultSet("sinhviens", sinhVienRowMapper);
                
        this.getByHoTenProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_GetByHoTen")
                .declareParameters(
                        new SqlParameter("HO", Types.NVARCHAR),
                        new SqlParameter("TEN", Types.NVARCHAR))
                .returningResultSet("sinhviens", sinhVienRowMapper);
                
        this.getByMaLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_GetByMaLop")
                .declareParameters(new SqlParameter("MALOP", Types.NCHAR))
                .returningResultSet("sinhviens", sinhVienRowMapper);
                
        this.insertSinhVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_Insert")
                .declareParameters(
                        new SqlParameter("HO", Types.NVARCHAR),
                        new SqlParameter("TEN", Types.NVARCHAR),
                        new SqlParameter("NGAYSINH", Types.TIMESTAMP),
                        new SqlParameter("DIACHI", Types.NVARCHAR),
                        new SqlParameter("MALOP", Types.NCHAR));
                        
        this.updateSinhVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_Update")
                .declareParameters(
                        new SqlParameter("MASV", Types.NCHAR),
                        new SqlParameter("HO", Types.NVARCHAR),
                        new SqlParameter("TEN", Types.NVARCHAR),
                        new SqlParameter("NGAYSINH", Types.TIMESTAMP),
                        new SqlParameter("DIACHI", Types.NVARCHAR),
                        new SqlParameter("MALOP", Types.NCHAR));
                        
        this.deleteSinhVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_Delete")
                .declareParameters(new SqlParameter("MASV", Types.NCHAR));
                
        this.getNewMaSVProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SINHVIEN_GetNewMaSV")
                .declareParameters(
                        new SqlParameter("MASV", Types.NCHAR));
    }

    private RowMapper<SINHVIEN> sinhVienRowMapper = new RowMapper<SINHVIEN>() {
        @Override
        public SINHVIEN mapRow(ResultSet rs, int rowNum) throws SQLException {
            SINHVIEN sinhVien = new SINHVIEN();
            sinhVien.setMASV(rs.getString("MASV"));
            sinhVien.setHO(rs.getString("HO"));
            sinhVien.setTEN(rs.getString("TEN"));
            sinhVien.setNGAYSINH(rs.getDate("NGAYSINH").toLocalDate());
            sinhVien.setDIACHI(rs.getString("DIACHI"));
            sinhVien.setMALOP(rs.getString("MALOP"));
            return sinhVien;
        }
    };

    @Override
    public List<SINHVIEN> findAll() {
        Map<String, Object> result = getAllSinhVienProc.execute(new HashMap<>());
        return (List<SINHVIEN>) result.get("sinhviens");
    }

    @Override
    public SINHVIEN findByMaSV(String maSV) {
        Map<String, Object> params = new HashMap<>();
        params.put("MASV", maSV);
        
        Map<String, Object> result = getByMaSVProc.execute(params);
        List<SINHVIEN> sinhViens = (List<SINHVIEN>) result.get("sinhviens");
        
        return sinhViens != null && !sinhViens.isEmpty() ? sinhViens.get(0) : null;
    }

    @Override
    public List<SINHVIEN> findByHoTen(String ho, String ten) {
        Map<String, Object> params = new HashMap<>();
        params.put("HO", ho);
        params.put("TEN", ten);
        
        Map<String, Object> result = getByHoTenProc.execute(params);
        return (List<SINHVIEN>) result.get("sinhviens");
    }

    @Override
    public List<SINHVIEN> findByMaLop(String maLop) {
        Map<String, Object> params = new HashMap<>();
        params.put("MALOP", maLop);
        
        Map<String, Object> result = getByMaLopProc.execute(params);
        return (List<SINHVIEN>) result.get("sinhviens");
    }

    @Override
    public void save(SINHVIEN sinhVien) {
        Map<String, Object> params = new HashMap<>();
        params.put("HO", sinhVien.getHO());
        params.put("TEN", sinhVien.getTEN());
        params.put("NGAYSINH", java.sql.Date.valueOf(sinhVien.getNGAYSINH()));
        params.put("DIACHI", sinhVien.getDIACHI());
        params.put("MALOP", sinhVien.getMALOP());
        
        Map<String, Object> result = insertSinhVienProc.execute(params);
        // Lấy mã sinh viên được tạo từ stored procedure
        if (result.containsKey("MASV")) {
            String maSV = (String) result.get("MASV");
            sinhVien.setMASV(maSV);
        } else if (result.containsKey("#result-set-1")) {
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
            if (!resultSet.isEmpty()) {
                String maSV = (String) resultSet.get(0).get("MASV");
                sinhVien.setMASV(maSV);
            }
        }
    }

    @Override
    public void update(SINHVIEN sinhVien) {
        Map<String, Object> params = new HashMap<>();
        params.put("MASV", sinhVien.getMASV());
        params.put("HO", sinhVien.getHO());
        params.put("TEN", sinhVien.getTEN());
        params.put("NGAYSINH", java.sql.Date.valueOf(sinhVien.getNGAYSINH()));
        params.put("DIACHI", sinhVien.getDIACHI());
        params.put("MALOP", sinhVien.getMALOP());
        
        updateSinhVienProc.execute(params);
    }

    @Override
    public void delete(String maSV) {
        Map<String, Object> params = new HashMap<>();
        params.put("MASV", maSV);
        
        deleteSinhVienProc.execute(params);
    }
    
    // Phương thức tạo mã sinh viên mới
    private String generateMaSV() {
        try {
            Map<String, Object> inParams = new HashMap<>();
            SqlParameter maSVParam = new SqlParameter("MASV", Types.NCHAR);
            inParams.put("MASV", null);
            
            Map<String, Object> result = getNewMaSVProc.execute(inParams);
            
            if (result.containsKey("MASV")) {
                return (String) result.get("MASV");
            }
            
            // Nếu không lấy được mã từ stored procedure, tạo mã mặc định
            return "N0000001";
        } catch (Exception e) {
            e.printStackTrace();
            return "N0000001";
        }
    }
} 