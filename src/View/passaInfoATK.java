package View;

public class passaInfoATK {
	protected void passaInfo(PrimFrame f, AttackFrame a) {
		if(TrocaContexto.getContProntos() == 1) {
			a.setOpponentShips1(f.getShips());
			a.setNomeJogador1(f.getNomeJogador());
		}
		else {
			a.setOpponentShips2(f.getShips());
			a.setNomeJogador2(f.getNomeJogador());
		}
	}
}
