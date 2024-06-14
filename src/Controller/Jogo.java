package Controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import View.*;

public class Jogo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InicialFrame inicial = new InicialFrame();
                inicial.setVisible(true);
            }
        });
    }

    public static void startNewGame() {
        NomeForm nomeForm = new NomeForm();
        String jogador1 = nomeForm.getJogador1();
        String jogador2 = nomeForm.getJogador2();

        PrimFrame f1 = new PrimFrame("Batalha naval");
        f1.setNomeJogador(jogador1);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);

        f1.addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosed(WindowEvent e) {
                  PrimFrame f2 = new PrimFrame("Batalha naval");
                  f2.setNomeJogador(jogador2);
                  f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  f2.setVisible(true);
                  
                  f2.addWindowListener(new WindowAdapter() {
                      @Override
                      public void windowClosed(WindowEvent e) {
                    	  TrocaContexto troca = new TrocaContexto();
                          // Após fechar a segunda janela, abrir a AttackFrame
                    	  if(TrocaContexto.getContProntos() == 2) {
                              troca.trocaPraAtaque(); //troca para tela de ataque
                          }
                      }
                  });
             }
         });
        
        
     }
}
