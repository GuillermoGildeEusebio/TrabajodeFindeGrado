package com.TFG.springbootApp.auxiliarService;

import java.util.ArrayList;

public class Preposiciones {
	
	private ArrayList<String> preposiciones = new ArrayList<String>(); 
	
	public Preposiciones() {
		
		String allPreposiciones[] = {"al","del","a"," ante"," bajo"," cabe"," con"," contra"," de"," desde"," durante"," en"," entre"," hacia"," hasta"," mediante"," para"," por"," según"," sin"," so"," sobre"," tras"}; 
 		
		for (int i = 0; i < allPreposiciones.length; i++) {
			this.preposiciones.add(allPreposiciones[i]);
		}
						
		
	}
	
	public boolean isPreposicion(String relativo) {
		
		return this.preposiciones.contains(relativo);
		
	}
	

}
