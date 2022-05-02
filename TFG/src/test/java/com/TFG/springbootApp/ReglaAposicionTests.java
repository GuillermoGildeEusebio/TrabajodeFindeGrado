package com.TFG.springbootApp;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.TFG.springbootApp.controller.ReglaAposicion;

@SpringBootTest
class ReglaAposicionTests {

	ReglaAposicion aposicion = new ReglaAposicion();
	
	@Test
	void Prueba1() throws Exception {
		String text = "Mi joya favorita, el anillo de mi compromiso, se perdió ayer.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	@Test
	void Prueba2() throws Exception {
		String text = "Adri, enfermera de profesión, puso la vacuna.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	@Test
	void Prueba3() throws Exception {
		String text = "El otro dia me fui de vacaciones, fue una experiencia inolvidable. Fuimos a Madrid, la capital de España, a visitar a unos amigos.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(2));
		
	}
	
	
	
	@Test
	void Prueba4() throws Exception {
		String text = "Nuestros vecinos, los Herrera, se fueron de vacaciones.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	
	
	@Test
	void Prueba5() throws Exception {
		String text = "Pablo, mi sobrino, salió a caminar por la plaza.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	@Test
	void Prueba6() throws Exception {
		String text = "El payaso, el animador de la fiesta de cumpleaños, nos hizo reír mucho";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	@Test
	void Prueba7() throws Exception {
		String text = "Mis amigas, las mujeres más generosas que existen, siempre me acompañan.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 0);
		
	}
	
	
	@Test
	void Prueba8() throws Exception {
		String text = "Mis amigas, las mujeres más generosas, siempre me acompañan.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		assertTrue(posicionAposiciones.contains(1));
		
	}
	
	
	@Test
	void Prueba9() throws Exception {
		String text = "Mis amigas, las mujeres más generosas que existen, siempre me acompañan.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 0);
		
	}
	
	
	@Test
	void Prueba10() throws Exception {
		String text = "Los chinos, que tienen una visión más dual y menos maniquea de las cosas, suelen decir que un error es una oportunidad. En el mundo occidental, en cambio, un error es visto siempre como un fracaso; por eso me parece interesante pararse por un momento a pensar cuánto debemos a nuestras equivocaciones. Hace poco estuvo en Madrid el físico francés Gérard Mourou, al que lo que podía haber sido un trágico accidente laboral le valió un Premio Nobel. "
				+ "En 1992, Detao Du, un joven estudiante chino, ayudante del profesor Mourou, estaba alineando los láseres en una máquina de laboratorio cuando la potente luz le hirió un ojo. Al llevarlo a urgencias, el médico preguntó al profesor que había acompañado a Detao al hospital qué clase de láser era aquel, porque nunca había visto una herida tan perfecta y focalizada. Veintitantos años más tarde, y gracias a este comentario que dejó cavilando a Mourou, la técnica de amplificación de pulso gorjeado es una herramienta común en oftalmología y se usa para corregir la miopía, la hipermetropía y el astigmatismo. "
				+ "El Premio Nobel de Física de 2018 no es el único beneficiario de los errores afortunados. Se cuentan por docenas los descubrimientos debidos a una chapuza, una negligencia o una colosal metedura de pata. El ejemplo más conocido tal vez sea el descubrimiento de la penicilina. Fleming no buscaba beneficiar a la humanidad ni cambiar el curso de la historia cuando descuidó las muestras de laboratorio que tenía a su cargo y una cepa de estafilococos que estaba estudiando se llenó de moho.";
		
		Map<String[] ,Integer> aposicionesDetectadas = new HashMap<String[] , Integer>();
		
		
		aposicionesDetectadas = aposicion.detectorAposicion(text);
				
				
		assertTrue(aposicionesDetectadas.size() == 1);
		
		Collection<Integer> posicionAposiciones = aposicionesDetectadas.values();
		
		
		assertTrue(posicionAposiciones.contains(8));
		
	}
	

		
	

}
