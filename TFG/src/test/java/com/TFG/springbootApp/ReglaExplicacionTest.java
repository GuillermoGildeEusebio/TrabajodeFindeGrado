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
		
		assertTrue(posicionExplicaciones.contains(2));
		
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
		String text = "Pablo, mi sobrino, salió a caminar por la plaza.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba6() throws Exception {
		String text = "El payaso, el animador de la fiesta de cumpleaños, nos hizo reír mucho";
		
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
				
				
		assertTrue(explicacionDetectadas.size() == 0);
		
	}
	
	
	@Test
	void Prueba8() throws Exception {
		String text = "Mis amigas, las mujeres más generosas, siempre me acompañan.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		assertTrue(posicionExplicaciones.contains(1));
		
	}
	
	
	@Test
	void Prueba9() throws Exception {
		String text = "Mis amigas, las mujeres más generosas que existen, siempre me acompañan.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 0);
		
	}
	
	
	@Test
	void Prueba10() throws Exception {
		String text = "Los chinos, que tienen una visión más dual y menos maniquea de las cosas, suelen decir que un error es una oportunidad. En el mundo occidental, en cambio, un error es visto siempre como un fracaso; por eso me parece interesante pararse por un momento a pensar cuánto debemos a nuestras equivocaciones. Hace poco estuvo en Madrid el físico francés Gérard Mourou, al que lo que podía haber sido un trágico accidente laboral le valió un Premio Nobel. "
				+ "En 1992, Detao Du, un joven estudiante chino, ayudante del profesor Mourou, estaba alineando los láseres en una máquina de laboratorio cuando la potente luz le hirió un ojo. Al llevarlo a urgencias, el médico preguntó al profesor que había acompañado a Detao al hospital qué clase de láser era aquel, porque nunca había visto una herida tan perfecta y focalizada. Veintitantos años más tarde, y gracias a este comentario que dejó cavilando a Mourou, la técnica de amplificación de pulso gorjeado es una herramienta común en oftalmología y se usa para corregir la miopía, la hipermetropía y el astigmatismo. "
				+ "El Premio Nobel de Física de 2018 no es el único beneficiario de los errores afortunados. Se cuentan por docenas los descubrimientos debidos a una chapuza, una negligencia o una colosal metedura de pata. El ejemplo más conocido tal vez sea el descubrimiento de la penicilina. Fleming no buscaba beneficiar a la humanidad ni cambiar el curso de la historia cuando descuidó las muestras de laboratorio que tenía a su cargo y una cepa de estafilococos que estaba estudiando se llenó de moho.";
		
		Map<String[] ,Integer> explicacionDetectadas = new HashMap<String[] , Integer>();
		
		
		explicacionDetectadas = explicacion.detectorExplicaciones(text);
				
				
		assertTrue(explicacionDetectadas.size() == 1);
		
		Collection<Integer> posicionExplicaciones = explicacionDetectadas.values();
		
		
		assertTrue(posicionExplicaciones.contains(8));
		
	}
	

		
	

}
