package View;

public class passaInfoATK {
	public boolean vez1 = true;
	
	protected void passaInfo(PrimFrame f, AttackFrame a) {
		if(TrocaContexto.getContProntos() == 1) {
			a.setOpponentShips1(f.getShips()); 
		}else {
			a.setOpponentShips2(f.getShips());
		}
	}
	
	protected void passaVez1() {
		vez1 = true;
	}
	
	protected void passaVez2() {
		vez1 = false;
	}
	
	public boolean isVez1() {
		return vez1;
	}
}
