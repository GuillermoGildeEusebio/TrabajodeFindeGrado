package com.TFG.springbootApp.service;


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
    	Credential credentialsProvider = new Credential("guillermo.gildeeusebio@alumnos.upm.es", "GuillermoGil-2000", "eyJraWQiOiI1RDVOdFM1UHJBajVlSlVOK1RraXVEZE15WWVMMFJQZ3RaUDJGTlhESHpzPSIsImFsZyI6IlJTMjU2In0.eyJjdXN0b206Y291bnRyeSI6IkVTIiwic3ViIjoiMzUxNzM0MTUtYjVhOS00N2VlLWI4MjgtNmRmZmEwODk3MmJhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS13ZXN0LTEuYW1hem9uYXdzLmNvbVwvZXUtd2VzdC0xX0FVSGdRMDhDQiIsImNvZ25pdG86dXNlcm5hbWUiOiIzNTE3MzQxNS1iNWE5LTQ3ZWUtYjgyOC02ZGZmYTA4OTcyYmEiLCJhdWQiOiIxZWdzNjNxOTlwM3NlYmVjaHNiNzI5dDgwbyIsImV2ZW50X2lkIjoiYjFhMzczMTctYjYzZi00ZGU0LWJlNjYtMGUyZDI2YjVjMmI2IiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NDk5NTQwMDMsImV4cCI6MTY1MDEyNzIzNywiaWF0IjoxNjUwMDQwODM3LCJlbWFpbCI6Imd1aWxsZXJtby5naWxkZWV1c2ViaW9AYWx1bW5vcy51cG0uZXMiLCJjdXN0b206bWFya2V0aW5nQXV0aCI6IjAifQ.jGdAu9rEHoKUxuIsM8-dqPLG5HSLAl4kIOhLOW0IyL7SrUJshMnvM4ZhkV5QaFB0NjXM5OhExzFxu7V4Mq20ycdNfyXLe5VBi7WKGbpJgqsE2nA4sIGRSCA3-x3ucVdKyVIqN_SdtjT4Wkrl3sQ1MKfH4Eg8CZ5NMrWS11GWOiyxHdntQUUPj4DF6UuB1AeKorQ3ytZRarm5naoiVjvc4bfOLKgGba9pyB21-ISxAmxhQqk1bZGXLVOHA_ugKTZ3-u6O8M7iPoZcNEgWGXOCpHVxR9SlxpgjQMm6zdG0-MD1MIE_0rM2zWM5oyXm8DvVMJ1fmLqy1vT0FEYNyc_KRg");
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
    

	
//	  public static void main(String[] args) { try { Analyzer analyzer =
//	  createAnalyzer(); AnalyzeResponse response = null;
//	  
//	  
//	  response = analyzer.relations(getSampleText());
//	  response.prettyPrint();
//	  
//	  JsonObject responseJSON = new Gson().fromJson(response.toJSON(),
//	  JsonObject.class);
//	  
//	  JsonArray tokens =
//	  responseJSON.get("data").getAsJsonObject().get("tokens").getAsJsonArray();
//	  
//	  System.out.println(tokens.get(1).getAsJsonObject().get("lemma").toString());
//	  
//	  
//	  
//	  } catch(Exception ex) { ex.printStackTrace(); } }
	 
    
}