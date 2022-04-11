package com.TFG.springbootApp.auxiliarService;

import java.util.ArrayList;

public class PronombresRelativos {
	
	private ArrayList<String> pronomRelativos = new ArrayList<String>(); 
	
	public PronombresRelativos() {
		
		String allRelativos[] = {"el que","los que","la que","las que","que","el cual","los cuales","la cual","las cuales","lo cual",  		//listado de pronombres relativos
								  "cuyo","cuyos","cuya","cuyas","donde","lo que","quienes","quien"}; 
 		
		for (int i = 0; i < allRelativos.length; i++) {
			this.pronomRelativos.add(allRelativos[i]);
		}
						
		
	}
	
	public boolean isRelativo(String relativo) {
		
		return this.pronomRelativos.contains(relativo);
		
	}
	

}
