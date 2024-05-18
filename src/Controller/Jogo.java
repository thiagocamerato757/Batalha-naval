package Controller;

import javax.swing.JFrame;

import View.*;

public class Jogo {
	public static void main (String[] arqs) {
		NomeForm nome = new NomeForm();
		String jogador1 = nome.getJogador1();
		String jogador2 = nome.getJogador2();
		
		PrimFrame f = new PrimFrame("Batalha naval");
		f.setNomeJogador(jogador1);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        PrimFrame frame = new PrimFrame("Batalha naval");
		frame.setNomeJogador(jogador2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
}
