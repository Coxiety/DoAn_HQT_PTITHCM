package com.thitracnghiem.hqt.repository.impl;

import com.thitracnghiem.hqt.model.LOP;
import com.thitracnghiem.hqt.repository.LopRepository;
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
public class LopRepositoryImpl implements LopRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getAllLopProc;
    private SimpleJdbcCall getByMaLopProc;
    private SimpleJdbcCall getByTenLopProc;
    private SimpleJdbcCall insertLopProc;
    private SimpleJdbcCall updateLopProc;
    private SimpleJdbcCall deleteLopProc;

    @Autowired
    public LopRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Khởi tạo các SimpleJdbcCall cho stored procedures
        this.getAllLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_GetAll")
                .returningResultSet("lops", lopRowMapper);
                
        this.getByMaLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_GetByMaLop")
                .declareParameters(new SqlParameter("MALOP", Types.NCHAR))
                .returningResultSet("lops", lopRowMapper);
                
        this.getByTenLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_GetByTenLop")
                .declareParameters(new SqlParameter("TENLOP", Types.NVARCHAR))
                .returningResultSet("lops", lopRowMapper);
                
        this.insertLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_Insert")
                .declareParameters(
                        new SqlParameter("MALOP", Types.NCHAR),
                        new SqlParameter("TENLOP", Types.NVARCHAR));
                        
        this.updateLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_Update")
                .declareParameters(
                        new SqlParameter("MALOP", Types.NCHAR),
                        new SqlParameter("TENLOP", Types.NVARCHAR));
                        
        this.deleteLopProc = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_LOP_Delete")
                .declareParameters(new SqlParameter("MALOP", Types.NCHAR));
    }

    private RowMapper<LOP> lopRowMapper = new RowMapper<LOP>() {
        @Override
        public LOP mapRow(ResultSet rs, int rowNum) throws SQLException {
            LOP lop = new LOP();
            lop.setMALOP(rs.getString("MALOP"));
            lop.setTENLOP(rs.getString("TENLOP"));
            return lop;
        }
    };

    @Override
    public List<LOP> findAll() {
        Map<String, Object> result = getAllLopProc.execute(new HashMap<>());
        return (List<LOP>) result.get("lops");
    }

    @Override
    public LOP findByMaLop(String maLop) {
        Map<String, Object> params = new HashMap<>();
        params.put("MALOP", maLop);
        
        Map<String, Object> result = getByMaLopProc.execute(params);
        List<LOP> lops = (List<LOP>) result.get("lops");
        
        return lops != null && !lops.isEmpty() ? lops.get(0) : null;
    }

    @Override
    public LOP findByTenLop(String tenLop) {
        Map<String, Object> params = new HashMap<>();
        params.put("TENLOP", tenLop);
        
        Map<String, Object> result = getByTenLopProc.execute(params);
        List<LOP> lops = (List<LOP>) result.get("lops");
        
        return lops != null && !lops.isEmpty() ? lops.get(0) : null;
    }

    @Override
    public void save(LOP lop) {
        Map<String, Object> params = new HashMap<>();
        params.put("MALOP", lop.getMALOP());
        params.put("TENLOP", lop.getTENLOP());
        
        insertLopProc.execute(params);
    }

    @Override
    public void update(LOP lop) {
        Map<String, Object> params = new HashMap<>();
        params.put("MALOP", lop.getMALOP());
        params.put("TENLOP", lop.getTENLOP());
        
        updateLopProc.execute(params);
    }

    @Override
    public void delete(String maLop) {
        Map<String, Object> params = new HashMap<>();
        params.put("MALOP", maLop);
        
        deleteLopProc.execute(params);
    }
} 