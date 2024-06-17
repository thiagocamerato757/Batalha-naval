package Model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SalvarArquivoTest {
    private SalvarArquivo salvarArquivo;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        salvarArquivo = new SalvarArquivo();
        tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        
        // Configuração dos dados do teste
        salvarArquivo.setNamePlayer1("Player1");
        salvarArquivo.setNamePlayer2("Player2");
        
        List<Navio> ships1 = new ArrayList<>();
        Navio ship1 = new Navio(3);
        ship1.adicionarCoordenada(new Point(1, 1));
        ship1.adicionarCoordenada(new Point(1, 2));
        ship1.adicionarCoordenada(new Point(1, 3));
        ships1.add(ship1);
        salvarArquivo.setShips1(ships1);
        
        List<Navio> ships2 = new ArrayList<>();
        Navio ship2 = new Navio(2);
        ship2.adicionarCoordenada(new Point(2, 2));
        ship2.adicionarCoordenada(new Point(2, 3));
        ships2.add(ship2);
        salvarArquivo.setShips2(ships2);
        
        Map<Point, Color> shots1 = new HashMap<>();
        shots1.put(new Point(1, 1), Color.RED);
        salvarArquivo.setShots1(shots1);
        
        Map<Point, Color> shots2 = new HashMap<>();
        shots2.put(new Point(2, 2), Color.BLUE);
        salvarArquivo.setShots2(shots2);
        
        salvarArquivo.setNavios_restantes1(4);
        salvarArquivo.setNavios_restantes2(3);
        salvarArquivo.setMaxTiros(10);
        salvarArquivo.setVezJogador(true);
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testWriteAndReadFile() throws IOException {
        // Teste de escrita
        salvarArquivo.writeFile(tempFile);

        // Criação de uma nova instância para leitura
        SalvarArquivo loadedSalvarArquivo = new SalvarArquivo();
        loadedSalvarArquivo.readFile(tempFile);

        // Verificações dos dados lidos
        assertEquals(salvarArquivo.getNamePlayer1(), loadedSalvarArquivo.getNamePlayer1());
        assertEquals(salvarArquivo.getNamePlayer2(), loadedSalvarArquivo.getNamePlayer2());

        assertEquals(salvarArquivo.getShips1().size(), loadedSalvarArquivo.getShips1().size());
        assertEquals(salvarArquivo.getShips1().get(0).getCoordenadas(), loadedSalvarArquivo.getShips1().get(0).getCoordenadas());

        assertEquals(salvarArquivo.getShips2().size(), loadedSalvarArquivo.getShips2().size());
        assertEquals(salvarArquivo.getShips2().get(0).getCoordenadas(), loadedSalvarArquivo.getShips2().get(0).getCoordenadas());

        assertEquals(salvarArquivo.getShots1().size(), loadedSalvarArquivo.getShots1().size());
        assertEquals(salvarArquivo.getShots1().get(new Point(1, 1)), loadedSalvarArquivo.getShots1().get(new Point(1, 1)));

        assertEquals(salvarArquivo.getShots2().size(), loadedSalvarArquivo.getShots2().size());
        assertEquals(salvarArquivo.getShots2().get(new Point(2, 2)), loadedSalvarArquivo.getShots2().get(new Point(2, 2)));

        assertEquals(salvarArquivo.getNavios_restantes1(), loadedSalvarArquivo.getNavios_restantes1());
        assertEquals(salvarArquivo.getNavios_restantes2(), loadedSalvarArquivo.getNavios_restantes2());

        assertEquals(salvarArquivo.getMaxTiros(), loadedSalvarArquivo.getMaxTiros());
        assertEquals(salvarArquivo.getVezJogador(), loadedSalvarArquivo.getVezJogador());
    }
}
