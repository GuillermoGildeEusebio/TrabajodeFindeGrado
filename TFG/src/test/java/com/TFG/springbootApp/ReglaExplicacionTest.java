package com.TFG.springbootApp;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.TFG.springbootApp.controller.ReglaExplicacion;

@SpringBootTest
class ReglaExplicacionTest {

	ReglaExplicacion explicacion = new ReglaExplicacion();
	
	@Test
	void Prueba1() throws Exception {
		String text = "Mi joya favorita,que es el anillo de mi compromiso, se perdió ayer.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba2() throws Exception {
		String text = "La integración del humano con la máquina, que es en última instancia lo que constituye un cíborg, es un escenario más pragmático y conciliador que lo que hemos descrito hasta ahora.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba3() throws Exception {
		String text = "En 1943 el general, encargado de supervisar desde septiembre de 1942 las investigaciones del Proyecto Manhattan, empezó a asignar a Enrico Fermi los problemas de desarrollo de tecnología nuclear en los que se encallaban otros investigadores.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	
	@Test
	void Prueba4() throws Exception {
		String text = "Adriana, la enfermera que me puso la vacuna, es mi hermana.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	//TODO
	
	@Test
	void Prueba5() throws Exception {
		String text = "Pablo, que es mi sobrino, salió a caminar por la plaza.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba6() throws Exception {
		String text = "El payaso,que es el animador de la fiesta de cumpleaños, nos hizo reír mucho";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
		
	}
	
	
	@Test
	void Prueba7() throws Exception {
		String text = "Mis amigas, las mujeres más generosas que existen, siempre me acompañan.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba8() throws Exception {
		String text = "Ramón, el portero del edificio, me reparó el calefón.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 0);
		

		
	}
	
	
	@Test
	void Prueba9() throws Exception {
		String text = "Marta, que es experta en botánica, regará las plantas mientras esté de viaje.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba10() throws Exception {
		String text = "Maria, a quien hecho mucho de menos, vendrá a visitarme este fin de semana por mi cumpleaños.";

		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	

		
	

}
