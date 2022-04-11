package com.TFG.springbootApp.controller;





import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.TFG.springbootApp.service.requestLibrAIryTokens;
import com.TFG.springbootApp.auxiliarService.respuestaJSONCorreccion;
import com.TFG.springbootApp.auxiliarService.respuestaJSONDeteccion;
import com.google.gson.Gson;
import com.google.gson.JsonObject;






@RestController
public class ReglaAposicion {	




	@GetMapping({"/aposicionDeteccion"})
	public String apopsicionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposiciones = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposicionesSinVerbo = new HashMap<String[] , Integer>();

		int id = 2;
		String name = "Regla - Aposiciones entre comas";
		String description = "Detectar el uso de explicaciones entre comas.";
		boolean pass = true;



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
				posiblesAposiciones.put(palabras,i);

		}


		/*
		 * Para que entren en la posibilidad de ser posibles explicaciones deben tener un verbo en ella
		 */

		for (String[] frasediv : posiblesAposiciones.keySet()){
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
				if(tokens.length() == 2)   //no tiene verbo entre comas
					posiblesAposicionesSinVerbo.put(frasediv,posiblesAposiciones.get(frasediv));

			}

		}


		/*
		 * Para la identificacion de explicaciones lo que hay antes de la coma debe ser un pronombre, un nombre o un nombre propio
		 */


		for (String[] frasediv : posiblesAposicionesSinVerbo.keySet()){

			int j = posiblesAposicionesSinVerbo.get(frasediv);

			if(j <= blockWords.size() -2) {

				String [] blockBefore = blockWords.get(j-1);

				String posibleSujeto = blockBefore[blockBefore.length-1];

				String [] blockPost = blockWords.get(j+1);

				String posibleVerbo = "";

				for (String words : blockPost){

					posibleVerbo = posibleVerbo + words;

				}

				boolean tieneSujeto = false;

				boolean tieneVerbo = true;

				String[] filter = new String[3];
				filter[0] = "PROPER_NOUN";
				filter[1] = "PRONOUN";
				filter[2] = "NOUN";

				String url = "tokens"; //Servicio a utilizar de LibrAIry
				String response = requestLibrAIryTokens.request(filter, posibleSujeto, url);
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

					String tokens = responseJSON.get("tokens").toString();
					if(tokens.length() != 2)   //Tiene un pronombre, un nombre o un nombre propioantes de las comas
						tieneSujeto = true;

				}


				String[] filter1 = new String[1];
				filter1[0] = "VERB";


				url = "tokens"; //Servicio a utilizar de LibrAIry
				response = requestLibrAIryTokens.request(filter1, posibleVerbo, url);
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

					String tokens = responseJSON.get("tokens").toString();
					if(tokens.length() != 2)   //Tiene un pronombre, un nombre o un nombre propioantes de las comas
						tieneVerbo = true;

				}



				if(tieneVerbo&&tieneSujeto)
					aposicionesDetectadas.put(frasediv,posiblesAposicionesSinVerbo.get(frasediv));

			}

		}


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


		List<String[]> blockWords = new LinkedList<String[]>();

		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposiciones = new HashMap<String[] , Integer>();

		Map<String[] ,Integer> posiblesAposicionesSinVerbo = new HashMap<String[] , Integer>();

		int id = 2;
		String name = "Regla - Aposiciones entre comas";
		String description = "Detectar el uso de explicaciones entre comas.";



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
				posiblesAposiciones.put(palabras,i);

		}


		/*
		 * Para que entren en la posibilidad de ser posibles explicaciones deben tener un verbo en ella
		 */

		for (String[] frasediv : posiblesAposiciones.keySet()){
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
				if(tokens.length() == 2)   //no tiene verbo entre comas
					posiblesAposicionesSinVerbo.put(frasediv,posiblesAposiciones.get(frasediv));

			}

		}


		/*
		 * Para la identificacion de explicaciones lo que hay antes de la coma debe ser un pronombre, un nombre o un nombre propio
		 */


		for (String[] frasediv : posiblesAposicionesSinVerbo.keySet()){

			int j = posiblesAposicionesSinVerbo.get(frasediv);

			if(j <= blockWords.size() -2) {

				String [] blockBefore = blockWords.get(j-1);

				String posibleSujeto = blockBefore[blockBefore.length-1];

				String [] blockPost = blockWords.get(j+1);

				String posibleVerbo = "";

				for (String words : blockPost){

					posibleVerbo = posibleVerbo + words;

				}

				boolean tieneSujeto = false;

				boolean tieneVerbo = true;

				String[] filter = new String[3];
				filter[0] = "PROPER_NOUN";
				filter[1] = "PRONOUN";
				filter[2] = "NOUN";

				String url = "tokens"; //Servicio a utilizar de LibrAIry
				String response = requestLibrAIryTokens.request(filter, posibleSujeto, url);
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

					String tokens = responseJSON.get("tokens").toString();
					if(tokens.length() != 2)   //Tiene un pronombre, un nombre o un nombre propioantes de las comas
						tieneSujeto = true;

				}


				String[] filter1 = new String[1];
				filter1[0] = "VERB";


				url = "tokens"; //Servicio a utilizar de LibrAIry
				response = requestLibrAIryTokens.request(filter1, posibleVerbo, url);
				if (!response.contains("ERROR")) {

					JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

					String tokens = responseJSON.get("tokens").toString();
					if(tokens.length() != 2)   //Tiene un pronombre, un nombre o un nombre propioantes de las comas
						tieneVerbo = true;

				}



				if(tieneVerbo&&tieneSujeto)
					aposicionesDetectadas.put(frasediv,posiblesAposicionesSinVerbo.get(frasediv));

			}

		}


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
