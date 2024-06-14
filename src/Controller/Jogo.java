package Controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Model.BatalhaNavalFacade;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import View.*;

public class Jogo {
	public Jogo() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NomeForm nome = new NomeForm();
                String jogador1 = nome.getJogador1();
                String jogador2 = nome.getJogador2();
                PrimFrame f = new PrimFrame("Batalha naval");
                f.setNomeJogador(jogador1);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);

                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Quando a primeira janela for fechada, abrir a segunda
                        PrimFrame frame = new PrimFrame("Batalha naval");
                        frame.setNomeJogador(jogador2);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
                    }
                });
            }
        });
	}
}
