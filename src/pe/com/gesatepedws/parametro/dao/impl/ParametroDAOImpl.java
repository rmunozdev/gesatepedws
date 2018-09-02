package pe.com.gesatepedws.parametro.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.gesatepedws.model.Parametro;
import pe.com.gesatepedws.parametro.dao.ParametroDAO;

@Repository
public class ParametroDAOImpl implements ParametroDAO {

	@Autowired
    protected SqlSession gesatepedSession;

	@Override
	public List<Parametro> listar() {
		
		return this.gesatepedSession.selectList("Parametro.listarParametros");
	}
	
	

}
