package Model;

public abstract class TransitaEstadosTabuleiro {
	private static int contProntos = 0;
	public void atualizaEstadoTab(BatalhaNaval tabuleiro,boolean TF) {
		tabuleiro.setEstadoPos(TF);
		if(TF) {
			this.contProntos++;
		}
	}
	
	public static int getContProntos() {
		return contProntos;
	}

	public abstract void trocaPraAtaque();
}
