package Model;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Navio.TipodeNavio;

public class NavioTest {

	@Test
	public void testTipo() {
		Navio teste1 = new Navio(1);
		Navio.setTipoNavio(teste1);
		Navio teste2 = new Navio(2);
		Navio.setTipoNavio(teste2);
		Navio teste3 = new Navio(3);
		Navio.setTipoNavio(teste3);
		Navio teste4 = new Navio(4); 
		Navio.setTipoNavio(teste4);
		Navio teste5 = new Navio(5);
		Navio.setTipoNavio(teste5);
		Navio teste6 = new Navio(0);//testa caso tenha tamanho fora do escopo
		Navio.setTipoNavio(teste6);
		assertEquals("submarino",teste1.getTipo());
		assertEquals("destroyer",teste2.getTipo());
		assertEquals("hidroaviao",teste3.getTipo());
		assertEquals("cruzador",teste4.getTipo());
		assertEquals("couracado",teste5.getTipo());
		assertEquals("indefinido",teste6.getTipo());
		
	}

}
