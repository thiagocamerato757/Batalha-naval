package Model;

class Navio {
	private int tamanho;
	private String tipo;
	
	enum TipodeNavio {
		submarino,
		destroyer,
		hidroaviao,
		cruzador,
		couracado;
	}
	
	public int getTamanho() {
		return this.tamanho;
	}
	public void setTamanho(int tam) {
		this.tamanho = tam;
	}
	protected Navio(int tam) { //construtor ja inicia com o tamanho
		this.setTamanho(tam);
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tip) {
		this.tipo = tip;
	}
	public static void TipoNavio(Navio nav) {
		int tam = nav.getTamanho();
		TipodeNavio nome = null;
		TipodeNavio tipos[] = TipodeNavio.values();
		switch(tam) {
		case 1:
			nome = tipos[0];
			break;
		case 2:
			nome = tipos[1];
			break;
		case 3:
			nome = tipos[2];
			break;
		case 4:
			nome = tipos[3];
			break;
		case 5:
			nome = tipos[4];
			break;
		default:
			nav.setTipo("indefinido"); //caso de tamanho errado
			return;
		}
		nav.setTipo(nome.toString());
	}
}