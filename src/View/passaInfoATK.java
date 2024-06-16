package View;

import Model.SalvarArquivo;

public class passaInfoATK {
	protected void passaInfo(SalvarArquivo s, PrimFrame f, AttackFrame a) {
		if(TrocaContexto.getContProntos() % 2 == 1) {
			a.setOpponentShips1(f.getShips());
			a.setNomeJogador1(f.getNomeJogador());
			s.setShips1(f.getShips());
			s.setNamePlayer1(f.getNomeJogador());
		}
		else {
			a.setOpponentShips2(f.getShips());
			a.setNomeJogador2(f.getNomeJogador());
			s.setShips2(f.getShips());
			s.setNamePlayer2(f.getNomeJogador());
		}
		a.setSaveFile(s);
	}
	protected void recriaInfo(SalvarArquivo s, AttackFrame a) {
		a.setOpponentShips1(s.getShips1());
		a.setOpponentShips2(s.getShips2());
		a.setNomeJogador1(s.getNamePlayer1());
		a.setNomeJogador2(s.getNamePlayer2());
		a.setTiros1(s.getShots1());
		a.setTiros2(s.getShots2());
		a.setSaveFile(s);
	}
}