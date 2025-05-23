package com.thitracnghiem.hqt.repository.impl;

import com.thitracnghiem.hqt.model.MONHOC;
import com.thitracnghiem.hqt.repository.MonHocRepository;
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
public class MonHocRepositoryImpl implements MonHocRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getAllMonHocProc;
    private SimpleJdbcCall getByMaMHProc;
    private SimpleJdbcCall getByTenMHProc;
    private SimpleJdbcCall insertMonHocProc;
    private SimpleJdbcCall updateMonHocProc;
    private SimpleJdbcCall deleteMonHocProc;

    @Autowired
    public MonHocRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Khởi tạo các SimpleJdbcCall cho stored procedures
        this.getAllMonHocProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_GetAll")
                .returningResultSet("monhocs", monHocRowMapper);
                
        this.getByMaMHProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_GetByMaMH")
                .declareParameters(new SqlParameter("MAMH", Types.NCHAR))
                .returningResultSet("monhocs", monHocRowMapper);
                
        this.getByTenMHProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_GetByTenMH")
                .declareParameters(new SqlParameter("TENMH", Types.NVARCHAR))
                .returningResultSet("monhocs", monHocRowMapper);
                
        this.insertMonHocProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_Insert")
                .declareParameters(
                        new SqlParameter("MAMH", Types.NCHAR),
                        new SqlParameter("TENMH", Types.NVARCHAR));
                        
        this.updateMonHocProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_Update")
                .declareParameters(
                        new SqlParameter("MAMH", Types.NCHAR),
                        new SqlParameter("TENMH", Types.NVARCHAR));
                        
        this.deleteMonHocProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_MONHOC_Delete")
                .declareParameters(new SqlParameter("MAMH", Types.NCHAR));
    }

    private RowMapper<MONHOC> monHocRowMapper = new RowMapper<MONHOC>() {
        @Override
        public MONHOC mapRow(ResultSet rs, int rowNum) throws SQLException {
            MONHOC monHoc = new MONHOC();
            monHoc.setMAMH(rs.getString("MAMH"));
            monHoc.setTENMH(rs.getString("TENMH"));
            return monHoc;
        }
    };

    @Override
    public List<MONHOC> findAll() {
        Map<String, Object> result = getAllMonHocProc.execute(new HashMap<>());
        return (List<MONHOC>) result.get("monhocs");
    }

    @Override
    public MONHOC findByMaMH(String maMH) {
        Map<String, Object> params = new HashMap<>();
        params.put("MAMH", maMH);
        
        Map<String, Object> result = getByMaMHProc.execute(params);
        List<MONHOC> monHocs = (List<MONHOC>) result.get("monhocs");
        
        return monHocs != null && !monHocs.isEmpty() ? monHocs.get(0) : null;
    }

    @Override
    public MONHOC findByTenMH(String tenMH) {
        Map<String, Object> params = new HashMap<>();
        params.put("TENMH", tenMH);
        
        Map<String, Object> result = getByTenMHProc.execute(params);
        List<MONHOC> monHocs = (List<MONHOC>) result.get("monhocs");
        
        return monHocs != null && !monHocs.isEmpty() ? monHocs.get(0) : null;
    }

    @Override
    public void save(MONHOC monHoc) {
        Map<String, Object> params = new HashMap<>();
        params.put("MAMH", monHoc.getMAMH());
        params.put("TENMH", monHoc.getTENMH());
        
        insertMonHocProc.execute(params);
    }

    @Override
    public void update(MONHOC monHoc) {
        Map<String, Object> params = new HashMap<>();
        params.put("MAMH", monHoc.getMAMH());
        params.put("TENMH", monHoc.getTENMH());
        
        updateMonHocProc.execute(params);
    }    @Override
    public void delete(String maMH) {
        Map<String, Object> params = new HashMap<>();
        params.put("MAMH", maMH);
        
        deleteMonHocProc.execute(params);
    }
    
    @Override
    public List<MONHOC> search(String keyword) {
        // Tìm kiếm theo mã hoặc tên môn học
        // Sử dụng native SQL query vì không có stored procedure
        String sql = "SELECT * FROM MONHOC WHERE MAMH LIKE ? OR TENMH LIKE ?";
        String searchTerm = "%" + keyword + "%";
        
        return jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm}, monHocRowMapper);
    }
}