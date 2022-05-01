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
import com.TFG.springbootApp.service.requestLibrAIryTokens;
import com.TFG.springbootApp.auxiliarService.Preposiciones;
import com.TFG.springbootApp.auxiliarService.PronombresRelativos;
import com.TFG.springbootApp.auxiliarService.respuestaJSONCorreccion;
import com.TFG.springbootApp.auxiliarService.respuestaJSONDeteccion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ai.expert.nlapi.v2.message.AnalyzeResponse;






@RestController
public class ReglaExplicacion {	

	private Map<String[] ,Integer> detectorExolicaciones(String text) throws IOException{

		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> explicacionesDetectadas = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesExplicaciones = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesExplicacionesVerbo = new HashMap<String[] , Integer>();


		/*
		 *  Cogemos el texto y lo dividimos por las comas 
		 *  para identificar mejor las explicaciones o aposiciones
		 */
		String[] block = text.split(",");
		for (String words : block){
			String[] word = words.split(" ");
			blockWords.add(word);
		}

		/*
		 * Vemos si tiene . en los bloques para la identificacion de 
		 * falsas aposiciones o explicaciones
		 */

		for (int i = 1; i < block.length; i++) {
			boolean posibleExplicacion = true;
			String[] palabras  =  blockWords.get(i);

			for (String tokens : palabras){
				if(tokens.indexOf(".") != -1)
					posibleExplicacion = false;
			}
			if(posibleExplicacion)
				posiblesExplicaciones.put(palabras,i);

		}

		/*
		 * Para que entren en la posibilidad de ser posibles explicaciones deben tener un verbo en ella
		 */

		for (String[] frasediv : posiblesExplicaciones.keySet()){
			String frase = "";
			for (String tokens : frasediv){
				frase  = frase +  " " + tokens;
			}

			//llamada a Libraly

			String[] filter = {"VERB"};
			String url = "tokens"; //Servicio a utilizar de LibrAIry

			String response = requestLibrAIryTokens.request(filter, frase, url);
			if (!response.contains("ERROR")) {

				JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

				String tokens = responseJSON.get("tokens").toString();
				if(tokens.length() != 2)   //tiene verbo entre comas
					posiblesExplicacionesVerbo.put(frasediv,posiblesExplicaciones.get(frasediv));

			}

		}


		/*
		 * Para la identificacion de explicaciones lo que hay antes de la coma debe ser un pronombre, un nombre o un nombre propio
		 */


		AnalyzeResponse responseAI = null;

		//llamada al analizador sintactico

		try {
			responseAI = expertAI.analyzeText(text);
		} catch (Exception e) {

			e.printStackTrace();
		}

		JsonObject responseJSONAI = new Gson().fromJson(responseAI.toJSON(),JsonObject.class);	            

		JsonArray tokensAI = responseJSONAI.get("data").getAsJsonObject().get("tokens").getAsJsonArray();


		for (String[] frasediv : posiblesExplicacionesVerbo.keySet()){

			int j = posiblesExplicacionesVerbo.get(frasediv);

			if(j <= blockWords.size() -2) {

				String posibleVerboDespues = "";

				boolean punto = false;

				//Cogemos la primera parte hasta el punto en caso de que este estuviese en el bloque posterior

				for (String words : blockWords.get(j+1)){

					if(!punto)
						posibleVerboDespues = posibleVerboDespues + " " + words;

					if(words.equals(".") || words.contains("."))
						punto = true;

				}	


				boolean tieneVerbo = false;

				String url = "tokens"; //Servicio a utilizar de LibrAIry


				String[] filter1 = new String[1];
				filter1[0] = "VERB";


				String response = requestLibrAIryTokens.request(filter1, posibleVerboDespues, url);
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

					String tokens = responseJSON.get("tokens").toString();

					if(tokens.length() != 2)   //tiene un verbo en la frase entre comas
						tieneVerbo = true;



				}

				PronombresRelativos allRelativos = new PronombresRelativos();

				Preposiciones preposiciones = new Preposiciones();

				String palabra1 = "";
				String palabra2 = "";
				String palabra3 = "";

				boolean tieneNexoInicio = false ;

				boolean tieneNexo = false ;

				boolean tieneSujeto = false;

				boolean iguales = false;

				boolean iguales2 = false;

				boolean iguales3 = false;

				int posComa = 0;
				int kinit = 0;

				//Bucle para colocarnos en el bloque de la posible aposicion

				while(posComa != j) {
					if(tokensAI.get(kinit).getAsJsonObject().get("lemma").toString().contains(",")) {
						posComa++;
					}

					kinit++;
				}
				
				int y = kinit -2;
				
				
				int idSujeto = -1;
				
				boolean out = false;
				
				while(y>-1&&!tokensAI.get(y).getAsJsonObject().get("lemma").toString().contains(",") && !tokensAI.get(y).getAsJsonObject().get("lemma").toString().contains(".")&&!out) {
					if(tokensAI.get(y).getAsJsonObject().get("type").getAsString().trim().equals("NOU") || tokensAI.get(y).getAsJsonObject().get("type").getAsString().trim().contains("NPH")) {
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


				if(tieneSujeto && tieneVerbo) {
				
				

				int i = 0;
				while(palabra1.equals("")) {															//error espacio en blanco despues de la coma
					if(!(blockWords.get(j)[i].equals(" ") || blockWords.get(j)[i].equals("")))
						palabra1 = blockWords.get(j)[i];
					i++;
				}


				palabra2 = blockWords.get(j)[i];

				palabra3 = blockWords.get(j)[i+1];

				//Caso 1 -->	Que la estructura este al inicio


				if(allRelativos.isRelativo(palabra1) || (preposiciones.isPreposicion(palabra1) && allRelativos.isRelativo(palabra2)) || (allRelativos.isRelativo(palabra1 + " " + palabra2)) || (preposiciones.isPreposicion(palabra1) && allRelativos.isRelativo(palabra2 + " " + palabra3)))
					tieneNexoInicio = true;



				//Caso 2 --> b.	Que la estructura este en medio de la frase. Para este caso usamos expert ai para ver si se refiere al nombre antes de la coma

				if(!tieneNexoInicio ) {

					palabra1 = "";
					palabra2 = "";
					palabra3 = "";


					//Buscamos la estructura

					while(!tieneNexo&&i<blockWords.get(j).length - 1) {

						palabra1 = blockWords.get(j)[i];
						if(allRelativos.isRelativo(palabra1)) {
							tieneNexo = true;

						}

						if(i<blockWords.get(j).length - 2) {

							palabra2 = blockWords.get(j)[i + 1];
							if((preposiciones.isPreposicion(palabra1) && allRelativos.isRelativo(palabra2)) || (allRelativos.isRelativo(palabra1 + " " + palabra2))) {
								tieneNexo = true;
							}

						}



						if(i<blockWords.get(j).length - 3) {

							palabra3 = blockWords.get(j)[i +2];
							if((preposiciones.isPreposicion(palabra1) && allRelativos.isRelativo(palabra2 + " " + palabra3))) {
								tieneNexo = true;
							}

						}


						i++;


					}


					//Vemos si pertenece al caso1

					if(tieneNexo) {

						//cogemos el id del sujeto


						boolean find = false;
						int referencia = -1;

						int k = kinit;

						//Buscamos el relativo para coger al referencia del relativo


						while(!find&&k<tokensAI.size()) {
							if(tokensAI.get(k).getAsJsonObject().get("type").getAsString().trim().equals("PRO")) {
								referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
								find = true;
							}
							k++;
						}


						k = kinit;

						boolean end = false;

						ArrayList<Integer> idRecorridos = new ArrayList<Integer>();  // aqui vamos guardando por que id vamos pasando para que no se quede en bucle


						//bucle para ver si nuestro pronombre al final se acaba relacionando con el nombre antes de la coma 

						while(referencia > kinit  && !end) {

							boolean restart = true;
							k = kinit;
							while(restart) {
								if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
									referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
									if(idRecorridos.contains(referencia))
										end = true;
									else
										idRecorridos.add(referencia);
									restart = false;
								}
								k++;
							}

						}


						iguales = referencia == idSujeto;



					}

				}




					//ver si el id del verbo apunta al sujeto o viceversa

					int k = kinit;
					


					while(!tokensAI.get(k).getAsJsonObject().get("lemma").toString().contains(",")&&k<tokensAI.size()) {


						k++;
					}

					int kfin = k;
					
					

					//Cogemos el primer verbo despues de la coma
					
					System.out.println(tieneVerbo);

					while(!tokensAI.get(k).getAsJsonObject().get("type").toString().trim().contains("VER")&&k<tokensAI.size()) {

						k++;
					}

					//guardamos su id

					int idVerb = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt();


					int referencia = idVerb;

					//vemos si el verbo hace referencia al sujeto

					boolean continuar = true;

					ArrayList<Integer> idRecorridos2 = new ArrayList<Integer>();


					//vemos si el verbo referencia al sujeto antes de la coma
					while(referencia > kfin  && continuar) {

						boolean restart = true;
						k = 0;
						while(restart) {
							if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
								referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
								if(referencia == idSujeto)
									continuar = false;
								if(idRecorridos2.contains(referencia))
									continuar = false;
								else
									idRecorridos2.add(referencia);
								restart = false;
							}
							k++;
						}

					}

					continuar  = true;

					int headVerbo = referencia;

					//guardamos los id a los que los nombres relacionados entre si apuntan

					ArrayList<Integer> referenciasNombres = new ArrayList<Integer>();

					referencia = idSujeto;

					while(continuar) {

						boolean restart = true;
						k = 0;
						while(restart) {
							if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
								if(tokensAI.get(k).getAsJsonObject().get("type").getAsString().trim().equals("NOU")) {
									if(referenciasNombres.contains(referencia)) {
										continuar = false;
										restart = false;
									}
									else {
										restart = false;
										referenciasNombres.add(referencia);
										referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
									}
								}
								else {
									restart = false;
									continuar = false;
								}

							}
							k++;
							if(k == tokensAI.size())
								restart = false;
						}
						if(k == tokensAI.size())
							continuar = false;
					}

					//vemos si el nombre de antes de la coma apunta al verbo o si el verbo apunta a los posibles sujetos

					iguales2 = (headSujeto == idVerb || referenciasNombres.contains(headVerbo) || headSujeto == headVerbo);
					
					


				
				
				
				if(iguales2&& !tieneNexoInicio && !iguales) {
					
					 k = kinit;


					while(!tokensAI.get(k).getAsJsonObject().get("lemma").toString().contains(",")&&k<tokensAI.size()) {


						k++;
					}

					 kfin = k;

					//Cogemos el primer verbo de la frase
					
					k = kinit;

					while(!tokensAI.get(k).getAsJsonObject().get("type").toString().trim().contains("VER")&&k<tokensAI.size()) {

						k++;
					}

					//guardamos su id

					 idVerb = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt();

					 referencia = idVerb;
					
					

					//vemos si el verbo hace referencia al sujeto

					 continuar = true;

					idRecorridos2 = new ArrayList<Integer>();

					
					
					//vemos si el verbo referencia al sujeto antes de la coma
					while(referencia >= kinit && continuar) {

						boolean restart = true;
						k = 0;
						
						while(restart) {

							if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
								referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
								if(referencia == idSujeto)
									continuar = false;
								if(idRecorridos2.contains(referencia))
									continuar = false;
								else
									idRecorridos2.add(referencia);
								restart = false;
							}
							k++;
						}

					}

					continuar  = true;

					int headVerboFrase = referencia;
					
					
					

					//guardamos los id a los que los nombres relacionados entre si apuntan

					referenciasNombres = new ArrayList<Integer>();
					
					referenciasNombres.add(idSujeto);

					referencia = idSujeto;

					while(continuar) {

						boolean restart = true;
						k = 0;
						while(restart) {
							if(tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("id").getAsInt() == referencia) {
								if(tokensAI.get(k).getAsJsonObject().get("type").getAsString().trim().equals("NOU")) {
									if(referenciasNombres.contains(referencia)) {
										continuar = false;
										restart = false;
									}
									else {
										restart = false;
										referenciasNombres.add(referencia);
										referencia = tokensAI.get(k).getAsJsonObject().get("dependency").getAsJsonObject().get("head").getAsInt();
									}
								}
								else {
									restart = false;
									continuar = false;
								}

							}
							k++;
							if(k == tokensAI.size())
								restart = false;
						}
						if(k == tokensAI.size())
							continuar = false;
					}

					//vemos si el nombre de antes de la coma apunta al verbo o si el verbo apunta a los posibles sujetos

					iguales3 = (headSujeto == idVerb || referenciasNombres.contains(headVerboFrase) || headSujeto == headVerboFrase);
					
					
					
				}
				
				
				}
				


				if(tieneSujeto&&tieneVerbo&&(tieneNexoInicio||iguales||iguales3)&&iguales2)
					explicacionesDetectadas.put(frasediv,posiblesExplicacionesVerbo.get(frasediv));

			}

		}

		return explicacionesDetectadas;
	}



	@GetMapping({"/explicacionDeteccion"})
	public String explicacionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {



		int id = 1;
		String name = "Regla - Deteccion de Explicaciones entre comas";
		String description = "Detectar el uso de explicaciones entre comas.";
		boolean pass = true;


		Map<String[] ,Integer> explicacionesDetectadas = new HashMap<String[] , Integer>();

		explicacionesDetectadas = detectorExolicaciones(text);


		String reason = "El texto contiene las siguientes explicaciones [";
		int i = 0;
		for (String[] frasediv : explicacionesDetectadas.keySet()){
			String explicacion = "";
			for (String tokens : frasediv){
				explicacion  = explicacion +  " " + tokens;
			}
			reason = reason + explicacion;

			if(i!=explicacionesDetectadas.size()-1)
				reason = reason + " , ";
			i++;
		}

		reason = reason + "]";

		return respuestaJSONDeteccion.codificador(id,name,description, pass, reason);	

	}	

	@GetMapping({"/explicacionAdaptacion"})
	public String explicacionAdaptacion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {



		int id = 1;
		String name = "Regla - Adaptacion de Explicaciones entre comas";
		String description = "Adaptar el texto con las explicaciones entre comas.";

		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> explicacionesDetectadas = new HashMap<String[] , Integer>();



		/*
		 *  Cogemos el texto y lo dividimos por las comas 
		 *  para identificar mejor las explicaciones o aposiciones
		 */
		String[] block = text.split(",");
		for (String words : block){
			String[] word = words.split(" ");
			blockWords.add(word);
		}

		explicacionesDetectadas = detectorExolicaciones(text);

		String textoCorregido = "";
		boolean ponerComa = false;
		Collection<Integer> posicionExplicaciones = explicacionesDetectadas.values();

		for (int i = 0; i < blockWords.size(); i++) {
			boolean next = false;

			if(posicionExplicaciones.contains(i)) {
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
