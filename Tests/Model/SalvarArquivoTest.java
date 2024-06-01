package Model;


import static org.junit.Assert.*; 
import Model.BatalhaNaval;
import Model.SalvarArquivo;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class SalvarArquivoTest {
	
    //@Test
	/*
	 * public void testEscreverArq() { BatalhaNaval batalhanaval = new
	 * BatalhaNaval(); try { String[] navi1 = {"A1", "B2", "C3", "D4", "E5"};
	 * batalhanaval.setNavios1 (navi1); String[] tiro1 = {"A1", "B2", "C3"};
	 * batalhanaval.setTiros1(tiro1); int qtd_tiro1 = 3;
	 * batalhanaval.setQtd_tiros1(qtd_tiro1); String[] navi2 = {"F6", "G7", "H8",
	 * "I9", "J10"}; batalhanaval.setNavios2(navi2); String[] tiro2 = {"F6", "G7",
	 * "H8", "I9", "J10"}; batalhanaval.setTiros2(tiro2); int qtd_tiro2 = 5;
	 * batalhanaval.setQtd_tiros2(qtd_tiro2);
	 * 
	 * SalvarArquivo salvaTest = new SalvarArquivo(); salvaTest.EscreverArq();
	 * 
	 * // Testa se o arquivo existe File file = new File("dados_partida.txt");
	 * assertTrue(file.exists());
	 * 
	 * } catch (IOException e) { fail("IOException should not be thrown here"); } }
	 */

    @Test
   public void testLerArq() {
    	SalvarArquivo salvou = new SalvarArquivo();   
        try {
            salvou.LerArq();
            String[] navios1 = salvou.getNavios1();
            String[] tiros1 = salvou.getTiros1();
            String[] navios2 = salvou.getNavios2();
            String[] tiros2 = salvou.getTiros2();
            
            // Assertions para verificar se o dado lido esta correto
            assertEquals("A1", navios1[0]);
            assertEquals("B2", navios1[1]);
            assertEquals("C3", navios1[2]);

            assertEquals("F6", navios2[0]);
            assertEquals("G7", navios2[1]);
            assertEquals("H8", navios2[2]);
            assertEquals("I9", navios2[3]);
            assertEquals("J10", navios2[4]);

            assertEquals("A1", tiros1[0]);
            assertEquals("B2", tiros1[1]);
            assertEquals("C3", tiros1[2]);

            assertEquals("F6", tiros2[0]);
            assertEquals("G7", tiros2[1]);
            assertEquals("H8", tiros2[2]);
            assertEquals("I9", tiros2[3]);
            assertEquals("J10", tiros2[4]);

        } catch (IOException e) {
            fail("IOException should not be thrown here");
        }
    }
}
