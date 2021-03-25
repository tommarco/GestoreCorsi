package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model { //Il modello verrà chiamato dal controllore
	
	private CorsoDAO corsoDao;
	
	public Model() {
		
		corsoDao= new CorsoDAO();
		
	}
	//metodo fa solo da passa carte verso il DAO e poi ritorna al chiamante
	public List <Corso> getCorsiByPeriodo(Integer pd){
		
		return corsoDao.getCorsiByPeriodo(pd); // il chiamante è il controllore
	}
	public Map <Corso, Integer> getIscrittiByPeriodo(Integer pd){
		
		return corsoDao.getIscrittiByPeriodo(pd);
	}
	
}
