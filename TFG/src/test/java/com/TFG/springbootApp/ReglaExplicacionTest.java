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
		String text = "El término “cíborg” hizo su primera aparición en un artículo de Manfred Clynes y Nathan S. Kline publicado en 1960 en la revista Astronautics y titulado “Cíborgs y espacio”. "
				+ "La palabra es una contracción de la expresión inglesa “cybernetic organism”. La integración del humano con la máquina, que es en última instancia lo que constituye un cíborg, es un escenario más pragmático y conciliador que lo que hemos descrito hasta ahora. "
				+ "¿Por qué luchar contra las máquinas u oponerse a su evolución en inteligencia si podemos formar parte de sus filas, y si ellas pueden también formar parte de las nuestras? El cíborg es un futuro preferible al de la guerra total contra las máquinas, al modo de Terminator. ";
				
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba11() throws Exception {
		String text = "Adri, que es mi hermana, juega muy bien al padel.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba12() throws Exception {
		String text = "El chico, cuyo nombre no recuerdo, me atendió muy mal.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
	}
	
	
	@Test
	void Prueba13() throws Exception {
		String text = "Mi tio Juan, que es el alto, tiene una limusina.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba14() throws Exception {
		String text = "El lago ,en cuya superficie se reflejaba el sol de la tarde,  nos sorprendió al ver su belleza.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba15() throws Exception {
		String text = "Tu peso, que disminuyó en los últimos meses, sigue siendo excesivo y pone en peligro su vida y su salud.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba16() throws Exception {
		String text = "El otro dia fui al Wanda Metropolitano, que es el campo del Atletico, a ver un partido.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba17() throws Exception {
		String text = "LG, la empresa que es de Corea del Sur, tiene buenos productos.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba18() throws Exception {
		String text = "El agua transparente, como cuando éramos niños, te invita con su frescura a bañarte en el río.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	@Test
	void Prueba19() throws Exception {
		String text = "Adidas, la empresa alemana, vende muy buenas zapatillas.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 0);
		
		
	}
	
	@Test
	void Prueba20() throws Exception {
		String text = "Rafa, que es el electricista, es mi amigo de la infancia.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	

		
	

}
