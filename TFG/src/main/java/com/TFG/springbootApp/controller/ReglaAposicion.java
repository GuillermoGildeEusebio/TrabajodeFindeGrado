package com.TFG.springbootApp.controller;





import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.springbootApp.service.expertAI;
import com.TFG.springbootApp.service.requestLibrAIryAnnotationsGroups;
import com.TFG.springbootApp.service.requestLibrAIryTokens;
import com.TFG.springbootApp.auxiliarService.respuestaJSONCorreccion;
import com.TFG.springbootApp.auxiliarService.respuestaJSONDeteccion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ai.expert.nlapi.v2.message.AnalyzeResponse;






@RestController
public class ReglaAposicion {	

	public Map<String[] ,Integer> detectorAposicion(String text) throws IOException{
		
		
		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposiciones = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposicionesSinVerbo = new HashMap<String[] , Integer>();


		/*
		 *  Cogemos el texto y lo dividimos por las comas 
		 *  para identificar mejor las aposiciones
		 */
		
		
		String[] block = text.split(",");
		for (String words : block){
			String[] word = words.split(" ");
			blockWords.add(word);
		}

		/*
		 * Vemos si tiene . en los bloques para la identificacion de 
		 * falsas aposiciones
		 */

		for (int i = 1; i < block.length; i++) {
			boolean posibleExplicacion = true;
			String[] palabras  =  blockWords.get(i);

			for (String tokens : palabras){
				if(tokens.indexOf(".") != -1)
					posibleExplicacion = false;
			}
			if(posibleExplicacion)
				posiblesAposiciones.put(palabras,i);

		}


		/*
		 * Para que entren en la posibilidad de ser posibles aposiciones no deben tener un verbo en ella
		 */

		for (String[] frasediv : posiblesAposiciones.keySet()){
			String frase = "";
			for (String tokens : frasediv){
				frase  = frase +  " " + tokens;
			}

			//llamada a Libraly

			String[] filter = {"VERB"};		//Tipo de palabra que queremos encontrar
			String url = "tokens"; 			//Servicio a utilizar de LibrAIry

			String response = requestLibrAIryTokens.request(filter, frase, url);
			if (!response.contains("ERROR")) {

				JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

				String tokens = responseJSON.get("tokens").toString();
				if(tokens.length() == 2)   			//no tiene verbo entre comas
					posiblesAposicionesSinVerbo.put(frasediv,posiblesAposiciones.get(frasediv));

			}

		}


		/*
		 * Para la identificacion de explicaciones lo que hay antes de la coma debe ser un pronombre, un nombre o un nombre propio
		 * 
		 * Identificacion despues de la coma de un verbo
		 */

		AnalyzeResponse responseAI = null;

		//llamada al analizador sintactico

		try {
			responseAI = expertAI.analyzeText(text);
		} catch (Exception e) {

			System.out.println("Fallo del analizador sintactico: " + e.getMessage());
		}

		JsonObject responseJSONAI = new Gson().fromJson(responseAI.toJSON(),JsonObject.class);	            

		JsonArray tokensAI = responseJSONAI.get("data").getAsJsonObject().get("tokens").getAsJsonArray();  //sacamos los tokens para su posterior utilizacion
		
		
		//Recorremos la lista de las posibles aposiciones para su analisis

		for (String[] frasediv : posiblesAposicionesSinVerbo.keySet()){

			int j = posiblesAposicionesSinVerbo.get(frasediv);
			
			
			if(j <= blockWords.size() -2) {


				//Vamos a ver si la supuesta aposicion se esta refiriendo al nombre antes de la coma


				int posComa = 0;
				int k = 0;
				
				boolean tieneSujeto = false;

				//Bucle para colocarnos en el bloque de la posible aposicion con el analizador sintactico

				while(posComa != j) {
					if(tokensAI.get(k).getAsJsonObject().get("lemma").toString().contains(",")) {
						posComa++;
					}

					k++;
				}

				//cogemos el id del sujeto para su posterior utilizacion
				
			
				
				int y = k -2;
				
				
				int idSujeto = -1;
				
				boolean out = false;
				
				while(y>-1&&!tokensAI.get(y).getAsJsonObject().get("lemma").toString().contains(",") && !tokensAI.get(y).getAsJsonObject().get("lemma").toString().contains(".")&&!out) {
					if(tokensAI.get(y).getAsJsonObject().get("type").getAsString().trim().equals("NOU") || tokensAI.get(y).getAsJsonObject().get("type").getAsString().trim().contains("NPH")
							|| tokensAI.get(y).getAsJsonObject().get("type").getAsString().trim().contains("NPR")) {
						out = true;
						idSujeto = tokensAI.get(y).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt();
					}
					if(!out)
						y--;
					
				}
				
				int headSujeto = -1;
				
				if(idSujeto != -1) {
					tieneSujeto = true;
					headSujeto = tokensAI.get(y).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
				}
				
		
				//sacamos la posible aposicion a un String 

				String [] blockAc = blockWords.get(j);

				String blockActual = "";

				for (String words : blockAc){

					blockActual = blockActual + " " + words;

				}


				//cogemos el primer nombre de la posible aposicion

				String[] filter2 = new String[2];
				filter2[0] = "NOUN";		//Tipo de palabra que queremos encontrar
				filter2[1] = "PROPER_NOUN";

				String url = "annotations"; //Servicio a utilizar de LibrAIry
				String response = requestLibrAIryAnnotationsGroups.request(filter2, blockActual, url);
				String noun = "";
				
				
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);
					if(responseJSON.getAsJsonObject().get("annotatedText").getAsJsonArray().size()>0)
						noun = responseJSON.getAsJsonObject().get("annotatedText").getAsJsonArray().get(0).getAsJsonObject().get("token").getAsJsonObject().get("lemma").getAsString().trim();   //Guardamos el primer nombre 


				}

				boolean find = false;
				int referencia = -1;

				//cogemos a quien hace referencia el nombre cogido anteriormente 

				while(!find&&!noun.equals("")&&k<tokensAI.size()) {
					if(tokensAI.get(k).getAsJsonObject().get("lemma").getAsString().trim().equalsIgnoreCase(noun)) {
						referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt();
						find = true;
					}
					k++;
				}
				
				
				ArrayList<Integer> idRecorridos2 = new ArrayList<Integer>();
				boolean continuar = true;
				
				while(continuar) {

					boolean restart = true;
					k = 0;
					while(restart) {			
						if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
							referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
							if(referencia == idSujeto || headSujeto == referencia)
								continuar = false;
							if(idRecorridos2.contains(referencia))
								continuar = false;
							else
								idRecorridos2.add(referencia);
							restart = false;
						}
						k++;
						if(k == tokensAI.size()-1) {
							continuar = false;
							restart = false;
						}
					}

				}
				

				//si el nombre hace referencia al nombre/pronombre/Nombre personal de antes de la coma es una posible aposicion.

				if(referencia == idSujeto || headSujeto == referencia) {

					String [] blockPost = blockWords.get(j+1);

					String posibleVerbo = "";

					

					boolean tieneVerbo = false;

					boolean punto = false;

					//Cogemos la primera parte hasta el punto en caso de que este estuviese en el bloque posterior

					for (String words : blockPost){

						if(!punto)
							posibleVerbo = posibleVerbo + " " + words;

						if(words.equals(".") || words.contains("."))
							punto = true;

					}			
			
					


					String[] filter1 = new String[1];
					filter1[0] = "VERB";


					url = "tokens"; //Servicio a utilizar de LibrAIry
					response = requestLibrAIryTokens.request(filter1, posibleVerbo, url);
					if (!response.contains("ERROR")) {

						JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

						String tokens = responseJSON.get("tokens").toString();
						if(tokens.length() != 2)   //Tiene un verbo despues de las comas
							tieneVerbo = true;

					}



					if(tieneVerbo&&tieneSujeto)
						aposicionesDetectadas.put(frasediv,posiblesAposicionesSinVerbo.get(frasediv));

				}

			}
		}
		
		
		return aposicionesDetectadas;
	}
	
	


	@GetMapping({"/aposicionDeteccion"})
	public String apopsicionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {




		int id = 2;
		String name = "Regla - Identificar Aposiciones entre comas";
		String description = "Detectar el uso de explicaciones entre comas.";
		boolean pass = true;
		

		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();


		aposicionesDetectadas = detectorAposicion(text);


		String reason = "El texto contiene las siguientes aposiciones [";
		int i = 0;
		for (String[] frasediv : aposicionesDetectadas.keySet()){
			String explicacion = "";
			for (String tokens : frasediv){
				explicacion  = explicacion +  " " + tokens;
			}
			reason = reason + explicacion;

			if(i!=aposicionesDetectadas.size()-1)
				reason = reason + " , ";
			i++;
		}

		reason = reason + "]";

		return respuestaJSONDeteccion.codificador(id,name,description, pass, reason);	


	}

	@GetMapping({"/aposicionAdaptacion"})
	public String aposicionAdaptacion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {



		int id = 2;
		String name = "Regla - Adaptacion de Aposiciones entre comas";
		String description = "Adaptar el uso de adaptaciones entre comas.";
		
		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();


		/*
		 *  Cogemos el texto y lo dividimos por las comas 
		 *  para identificar mejor las explicaciones o aposiciones
		 */
		
		String[] block = text.split(",");
		for (String words : block){
			String[] word = words.split(" ");
			blockWords.add(word);
		}

		aposicionesDetectadas = detectorAposicion(text);

		String textoCorregido = "";
		boolean ponerComa = false;
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();

		for (int i = 0; i < blockWords.size(); i++) {
			boolean next = false;

			if(posicionAposiciones.contains(i)) {
				next = true;
				ponerComa = false;
			}


			if(!next) {	
				for (String tokens : blockWords.get(i)){
					if(ponerComa) {
						textoCorregido  = textoCorregido +  "," + tokens;
						ponerComa = false;
					}
					else {
						textoCorregido  = textoCorregido +  " " + tokens;
					}

				}
				ponerComa = true;
			}

		}

		textoCorregido = textoCorregido.trim();
		textoCorregido=textoCorregido.replaceAll("\\s{2,}", " ");



		return respuestaJSONCorreccion.codificador(id,name,description, textoCorregido);
	}	


}
