package com.thitracnghiem.hqt.repository.impl;

import com.thitracnghiem.hqt.model.GIAOVIEN;
import com.thitracnghiem.hqt.repository.GiaoVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiaoVienRepositoryImpl implements GiaoVienRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getAllGiaoVienProc;
    private SimpleJdbcCall getGiaoVienByMaGVProc;
    private SimpleJdbcCall insertGiaoVienProc;
    private SimpleJdbcCall updateGiaoVienProc;
    private SimpleJdbcCall deleteGiaoVienProc;
    private SimpleJdbcCall getNewMaGVProc;

    @Autowired
    public GiaoVienRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Khởi tạo các SimpleJdbcCall
        this.getAllGiaoVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_GetAll")
                .returningResultSet("giaoviens", giaoVienRowMapper);
                
        this.getGiaoVienByMaGVProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_GetByMaGV")
                .returningResultSet("giaovien", giaoVienRowMapper);
                
        this.insertGiaoVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_Insert")
                .declareParameters(
                    new SqlParameter("MAGV", Types.CHAR),
                    new SqlParameter("HO", Types.NVARCHAR),
                    new SqlParameter("TEN", Types.NVARCHAR),
                    new SqlParameter("SODTLL", Types.VARCHAR),
                    new SqlParameter("DIACHI", Types.NVARCHAR)
                );
                
        this.updateGiaoVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_Update")
                .declareParameters(
                    new SqlParameter("MAGV", Types.CHAR),
                    new SqlParameter("HO", Types.NVARCHAR),
                    new SqlParameter("TEN", Types.NVARCHAR),
                    new SqlParameter("SODTLL", Types.VARCHAR),
                    new SqlParameter("DIACHI", Types.NVARCHAR)
                );
                
        this.deleteGiaoVienProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_Delete")
                .declareParameters(
                    new SqlParameter("MAGV", Types.CHAR)
                );
                
        this.getNewMaGVProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GIAOVIEN_GetNewMaGV");
    }

    private final RowMapper<GIAOVIEN> giaoVienRowMapper = new RowMapper<GIAOVIEN>() {
        @Override
        public GIAOVIEN mapRow(ResultSet rs, int rowNum) throws SQLException {
            GIAOVIEN giaoVien = new GIAOVIEN();
            giaoVien.setMAGV(rs.getString("MAGV"));
            giaoVien.setHO(rs.getString("HO"));
            giaoVien.setTEN(rs.getString("TEN"));
            giaoVien.setSODTLL(rs.getString("SODTLL"));
            giaoVien.setDIACHI(rs.getString("DIACHI"));
            return giaoVien;
        }
    };

    @Override
    public List<GIAOVIEN> findAll() {
        Map<String, Object> result = getAllGiaoVienProc.execute();
        return (List<GIAOVIEN>) result.get("giaoviens");
    }

    @Override
    public Optional<GIAOVIEN> findById(String maGV) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("MAGV", maGV);
                
        Map<String, Object> result = getGiaoVienByMaGVProc.execute(paramMap);
        List<GIAOVIEN> giaoviens = (List<GIAOVIEN>) result.get("giaovien");
        
        return giaoviens.isEmpty() ? Optional.empty() : Optional.of(giaoviens.get(0));
    }

    @Override
    public String save(GIAOVIEN giaoVien) {
        // Nếu mã giáo viên chưa được thiết lập, tạo mã tự động
        if (giaoVien.getMAGV() == null || giaoVien.getMAGV().isEmpty()) {
            giaoVien.setMAGV(generateMaGV());
        }
        
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("MAGV", giaoVien.getMAGV())
                .addValue("HO", giaoVien.getHO())
                .addValue("TEN", giaoVien.getTEN())
                .addValue("SODTLL", giaoVien.getSODTLL())
                .addValue("DIACHI", giaoVien.getDIACHI());
                
        insertGiaoVienProc.execute(paramMap);
        return giaoVien.getMAGV();
    }

    @Override
    public void update(GIAOVIEN giaoVien) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("MAGV", giaoVien.getMAGV())
                .addValue("HO", giaoVien.getHO())
                .addValue("TEN", giaoVien.getTEN())
                .addValue("SODTLL", giaoVien.getSODTLL())
                .addValue("DIACHI", giaoVien.getDIACHI());
                
        updateGiaoVienProc.execute(paramMap);
    }

    @Override
    public void deleteById(String maGV) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("MAGV", maGV);
                
        deleteGiaoVienProc.execute(paramMap);
    }
    
    /**
     * Tạo mã giáo viên tự động sử dụng stored procedure
     * 
     * @return Mã giáo viên mới
     */
    private String generateMaGV() {
        Map<String, Object> result = getNewMaGVProc.execute();
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("#result-set-1");
        if (resultList != null && !resultList.isEmpty()) {
            return (String) resultList.get(0).get("MAGV");
        }
        
        // Fallback nếu stored procedure không trả về kết quả đúng
        return "GV001";
    }
} 