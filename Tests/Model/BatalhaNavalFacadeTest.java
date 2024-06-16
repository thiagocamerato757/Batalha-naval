package Model;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class BatalhaNavalFacadeTest {
    private BatalhaNavalFacade batalhaNavalFacade;

    @Before
    public void setUp() {
        batalhaNavalFacade = new BatalhaNavalFacade();
    }

    @Test
    public void testGetAndSetNome() {
        batalhaNavalFacade.setNome("Teste");
        assertEquals("Teste", batalhaNavalFacade.getNome());
    }

    @Test
    public void testSetPosicaoPronta() {
        batalhaNavalFacade.setPosicaoPronta(true);
        assertTrue(batalhaNavalFacade.getEstadoPos());

        batalhaNavalFacade.setPosicaoPronta(false);
        assertFalse(batalhaNavalFacade.getEstadoPos());
    }

    @Test
    public void testPlaceShip() {
        // Configurar o navio e suas coordenadas
        Navio navio = new Navio(3);
        navio.adicionarCoordenada(new Point(1, 1));
        navio.adicionarCoordenada(new Point(1, 2));
        navio.adicionarCoordenada(new Point(1, 3));

        // Testar posicionamento de navio
        boolean placed = batalhaNavalFacade.placeShip(navio);
        assertTrue(placed);

        // Testar se o navio está na lista de navios
        assertEquals(1, batalhaNavalFacade.getNavios().size());
    }


    @Test
    public void testGetNaviosRestantes() {
        assertEquals(15, batalhaNavalFacade.getNaviosRestantes()); // Supondo que começa com 15 navios
    }
}
