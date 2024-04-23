package Model;

class Navio {
	enum TipodeNavio {
		submarino,
		destroyer,
		hidroaviao,
		cruzador,
		couracado;
	}
	
	public static String TipoNavio(int tamanho) {
		TipodeNavio nome = null;
		TipodeNavio tipos[] = TipodeNavio.values();
		switch(tamanho) {
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
		}
		return nome.toString();
	}
	
	public static void main(String[] args) {
		String nome;
		nome = TipoNavio(3);
		System.out.println(nome);
	}
}