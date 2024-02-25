package com.datafast.midsactivos.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class RepositoryMidsActivos {

	private static final Logger log= LoggerFactory.getLogger(RepositoryMidsActivos.class);

	@Autowired 
    @Qualifier("jdbcDfreportes")
    protected JdbcTemplate jdbcDfreportes;
	
	public List<Map<String, Object>> consultaCaja(String usuario) {
        Timestamp now = new Timestamp(new Date().getTime());

        String sql = "EXEC sp_MidActivos_Caja_Act @FECHA = ?, @USUARIO = ?";
        List<Map<String, Object>> result = jdbcDfreportes.queryForList(sql, now, usuario);
        
        log.info("Se genero mids de caja");
        return result;
    }
	
	public List<Map<String, Object>> consultaPos(String usuario) {
        Timestamp now = new Timestamp(new Date().getTime());

        String sql = "EXEC sp_MidActivos_Pos_Act @FECHA = ?, @USUARIO = ?";
        List<Map<String, Object>> result = jdbcDfreportes.queryForList(sql, now, usuario);
        
        log.info("Se genero mids de pos");
        return result;
    }

}
