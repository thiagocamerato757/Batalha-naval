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

    // Instâncias da classe Navio para o conjunto de testes
    private Navio navio1;
    private Navio navio2;
    private Navio navio3;

    // Este método é executado antes de cada teste, inicializando as instâncias de Navio
    @Before
    public void setUp() {
        navio1 = new Navio(1);  // Inicializa um Navio com tamanho 1
        Navio.setTipoNavio(navio1);  // Define o tipo com base no tamanho
        navio2 = new Navio(3);  // Inicializa um Navio com tamanho 3
        Navio.setTipoNavio(navio2);  // Define o tipo com base no tamanho
        navio3 = new Navio(5);  // Inicializa um Navio com tamanho 5
        Navio.setTipoNavio(navio3);  // Define o tipo com base no tamanho
    }

    // Testa a atribuição de tipo com base no tamanho do navio
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
        Navio teste6 = new Navio(0); // Testa um caso com tamanho fora do escopo
        Navio.setTipoNavio(teste6);

        // Verifica se cada tipo de navio é corretamente atribuído com base no tamanho
        assertEquals("submarino", teste1.getTipo());
        assertEquals("destroyer", teste2.getTipo());
        assertEquals("hidroavião", teste3.getTipo());
        assertEquals("cruzador", teste4.getTipo());
        assertEquals("couraçado", teste5.getTipo());
        assertEquals("indefinido", teste6.getTipo()); // Verifica o tipo indefinido
    }

    // Testa a adição de coordenadas ao navio e a recuperação das mesmas
    @Test
    public void testCoordenadas() {
        Point ponto1 = new Point(1, 1);
        Point ponto2 = new Point(1, 2);
        navio1.adicionarCoordenada(ponto1); // Adiciona a primeira coordenada
        navio1.adicionarCoordenada(ponto2); // Adiciona a segunda coordenada

        List<Point> coords = navio1.getCoordenadas(); // Obtém a lista de coordenadas
        assertEquals(2, coords.size()); // Verifica o tamanho da lista de coordenadas
        assertTrue(coords.contains(ponto1)); // Verifica se a primeira coordenada está presente
        assertTrue(coords.contains(ponto2)); // Verifica se a segunda coordenada está presente
    }

    // Testa o status de confirmação do navio
    @Test
    public void testConfirmed() {
        navio1.setConfirmed(true); // Define o navio como confirmado
        assertTrue(navio1.isConfirmed()); // Verifica se está confirmado

        navio1.setConfirmed(false); // Define o navio como não confirmado
        assertFalse(navio1.isConfirmed()); // Verifica se não está confirmado
    }

    // Testa o status de afundamento do navio
    @Test
    public void testSunk() {
        navio3.setSunk(true); // Define o navio como afundado
        assertTrue(navio3.isSunk()); // Verifica se está afundado

        navio3.setSunk(false); // Define o navio como não afundado
        assertFalse(navio3.isSunk()); // Verifica se não está afundado
    }
}
