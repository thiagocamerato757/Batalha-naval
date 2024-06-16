package Model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NavioTest {

    private Navio navio1;
    private Navio navio2;
    private Navio navio3;

    @Before
    public void setUp() {
        navio1 = new Navio(1);
        Navio.setTipoNavio(navio1);
        navio2 = new Navio(3);
        Navio.setTipoNavio(navio2);
        navio3 = new Navio(5);
        Navio.setTipoNavio(navio3);
    }

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
        Navio teste6 = new Navio(0); // Testa caso tenha tamanho fora do escopo
        Navio.setTipoNavio(teste6);
        assertEquals("submarino", teste1.getTipo());
        assertEquals("destroyer", teste2.getTipo());
        assertEquals("hidroavião", teste3.getTipo());
        assertEquals("cruzador", teste4.getTipo());
        assertEquals("couraçado", teste5.getTipo());
        assertEquals("indefinido", teste6.getTipo());
    }

    @Test
    public void testCoordenadas() {
        Point ponto1 = new Point(1, 1);
        Point ponto2 = new Point(1, 2);
        navio1.adicionarCoordenada(ponto1);
        navio1.adicionarCoordenada(ponto2);

        List<Point> coords = navio1.getCoordenadas();
        assertEquals(2, coords.size());
        assertTrue(coords.contains(ponto1));
        assertTrue(coords.contains(ponto2));
    }

    

    @Test
    public void testConfirmed() {
        navio1.setConfirmed(true);
        assertTrue(navio1.isConfirmed());

        navio1.setConfirmed(false);
        assertFalse(navio1.isConfirmed());
    }

    @Test
    public void testSunk() {
        navio3.setSunk(true);
        assertTrue(navio3.isSunk());

        navio3.setSunk(false);
        assertFalse(navio3.isSunk());
    }
}