package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	//Che dati si scambiano il nostro programma è il database?
	//Poichè le informazioni che dobbiamo estrapolare non sono numeri o string ma sono oggetti
	// vi è un pattern di programmazione ovvero OERM serve per definire la struttura dei dati che il database
	//scambia con il programma
	
	//Pattern: per ogni tabella che vogliamo modellare per il nostro programma creiamo una classe apposita 
	//chiamata javabean 
	//tutte le tabelle ESCLUSE quelle che modellano una relazione "MOLTI A MOLTI" HANNO la correspettiva
	//classe in java
	
	//Inserisco corso nel package model
	
	public List <Corso> getCorsiByPeriodo(Integer periodo){
		String sql=" SELECT * " + 
				"FROM corso " + 
				" WHERE pd= ?";
		
		List <Corso> result = new ArrayList <Corso>();
		
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) { // Per ogni riga mi creo un nuovo corso
				
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c); //aggiungo alla lista
			}
			
			rs.close();
			conn.close();
			st.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public Map <Corso, Integer> getIscrittiByPeriodo(Integer periodo){
		
		String sql="SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot " + 
				"FROM corso c, iscrizione i " + 
				"WHERE c.codins= i.codins AND c.pd=? " + 
				"GROUP BY c.codins, c.nome, c.crediti, c.pd";
		
		Map <Corso,Integer> result = new HashMap <Corso,Integer>();
		
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) { // Per ogni riga mi creo un nuovo corso
				
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n= rs.getInt("tot");
				result.put(c,n); //aggiungo alla lista
			}
			
			rs.close();
			conn.close();
			st.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
		
	}
	

}
