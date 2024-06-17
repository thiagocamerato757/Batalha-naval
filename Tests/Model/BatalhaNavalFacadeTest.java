package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BatalhaNavalFacadeTest {
    private BatalhaNavalFacade batalhaNavalFacade;
    private Navio navio1;
    private Navio navio2;
    private Navio navio3;

    @Before
    public void setUp() {
        batalhaNavalFacade = new BatalhaNavalFacade();
        navio1 = new Navio(1);
        navio2 = new Navio(3);
        navio3 = new Navio(5);

        // Inicializando tipos de navios
        Navio.setTipoNavio(navio1);
        Navio.setTipoNavio(navio2);
        Navio.setTipoNavio(navio3);
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
    public void testGetNavios() {
        assertNotNull(batalhaNavalFacade.getNavios());
        assertTrue(batalhaNavalFacade.getNavios().isEmpty());
    }

    @Test
    public void testGetNaviosRestantes() {
        assertEquals(15, batalhaNavalFacade.getNaviosRestantes()); // Supondo que começa com 15 navios
    }



    @Test
    public void testGetNumNavios() {
        assertEquals(15, batalhaNavalFacade.getNumNavios()); // Supondo que começa com 15 navios
    }
    
    
}
