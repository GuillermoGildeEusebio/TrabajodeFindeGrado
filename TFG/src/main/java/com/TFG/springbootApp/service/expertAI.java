package com.TFG.springbootApp.service;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;

public class expertAI {

    static StringBuilder sb = new StringBuilder();

    // Sample text to be analyzed
    static {
        sb.append("Adri, que es enfermera de profesión, puso la vacuna.");

    }

    public static String getSampleText() {
        return sb.toString();
    }
    
    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
    	
    	//generamos el token que caduca cada 24 h
    	String token = "";
		try {
			String cmd = "curl -X POST https://developer.expert.ai/oauth2/token -H \"Content-Type: application/json; charset=utf-8\" -d \"{\\\"username\\\": \\\"guillermo.gildeeusebio@alumnos.upm.es\\\", \\\"password\\\": \\\"GuillermoGil-2000\\\"}\""; //Comando de apagado en linux
			Process process = Runtime.getRuntime().exec(cmd);
			while(process.isAlive()){
				
			}
			InputStream inputstream = process.getInputStream();
			BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
			
			int i = -1;
			while((i = bufferedinputstream.read() )!= -1) {
				token = token + Character.toString((char) i );
			}
			
			
		} catch (IOException ioe) {
			System.out.println (ioe);
		}
    	
    	
    	
    	
    	Credential credentialsProvider = new Credential("guillermo.gildeeusebio@alumnos.upm.es", "GuillermoGil-2000", token);
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }

    //Method for selecting the resource to be call by the API; 
    //as today, the API provides the standard context only, and  
    //five languages such as English, French, Spanish, German and Italian
    public static Analyzer createAnalyzer() throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
            .withVersion(API.Versions.V2)
            .withContext("standard")
            .withLanguage(API.Languages.es)
            .withAuthentication(createAuthentication())
            .build());
    }
    
    public static AnalyzeResponse analyzeText(String text) throws Exception {
        Analyzer analyzer = createAnalyzer();

        // Relations Analisys
        return analyzer.relations(text);
    }
    

	
	  public static void main(String[] args) { try { Analyzer analyzer =
	  createAnalyzer(); AnalyzeResponse response = null;
	  
	  
	  response = analyzer.relations(getSampleText());
	  response.prettyPrint();
	  
	  JsonObject responseJSON = new Gson().fromJson(response.toJSON(),
	  JsonObject.class);
	  
	  JsonArray tokens =
	  responseJSON.get("data").getAsJsonObject().get("tokens").getAsJsonArray();
	  
	  System.out.println(tokens.get(1).getAsJsonObject().get("lemma").toString());
	  
	  
	  
	  } catch(Exception ex) { ex.printStackTrace(); } }
	 
    
}