package Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Navio;

public class BatalhaNaval implements OrbservaTabuleiro {
    private static final int TAMANHO_TABULEIRO = 15;
    private static final int NUM_NAVIOS = 15;
    private final int[][] tabuleiro = new int[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static int qtd_tiros = 0;
    private static int naviosRestantes = NUM_NAVIOS;
    private ArrayList<Navio> navios = new ArrayList<>();
    private boolean posicaoPronta = false;
    
    public BatalhaNaval() {
    	for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
            	tabuleiro[i][j] = 0;
                //System.out.print(tabuleiro[i][j]);
            }
            //System.out.println();
        }
        //System.out.println();
    }
    
    public int getNaviosRestantes() {
    	return this.naviosRestantes;
    }
    public ArrayList<Navio> getNavios() {
    	return this.navios;
    }
    
    public void setNavios(ArrayList<Navio>navios) {
    	this.navios = navios;
    }
    
    public void addNavios(Navio navio) {
    	this.navios.add(navio);
    }
    
    public static void exibirTabuleiro(int[][] tabuleiro) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                //System.out.print(tabuleiro[i][j]);
            }
            //System.out.println();
        }
        //System.out.println();
    }
    
    public void AtualizaTab_Pos(Navio ship){ //implemetacao da interface
    	navios.add(ship);
    	for(int i = 0; i < this.navios.size(); i++) {
    		for(int j = 0; j < this.navios.get(i).getCoordenadas().size(); j++) {
    			this.tabuleiro[this.navios.get(i).getCoordenadas().get(j).x][this.navios.get(i).getCoordenadas().get(j).y] = this.navios.get(i).getTamanho();
    		}
    	}
    	
    	for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                //System.out.print(tabuleiro[j][i] + " ");
            }
            //System.out.println();
        }
    	
    }
    
    protected boolean getEstadoPos() {
    	return this.posicaoPronta;
    }
    
    protected void setEstadoPos(boolean TF) {
    	this.posicaoPronta = TF;
    }
    //#TODO : aleterar essa funcao para a partida 
	/*
	 * private static int realizarJogada(char[][] tabuleiro, int naviosRestantes) {
	 * Scanner scanner = new Scanner(System.in); boolean jogadaValida = false; int i
	 * = 0; while (!jogadaValida) {
	 * System.out.print("Digite a coordenada (letra número), por exemplo, A3: ");
	 * String coordenada = scanner.nextLine().toUpperCase(); // Converte para
	 * maiúsculas para tratamento consistente if (tabuleiro == tabuleiro2) {
	 * //tiros1[qtd_tiros1] = coordenada; qtd_tiros1++; } else { tiros2[qtd_tiros2]
	 * = coordenada; qtd_tiros2++; }
	 * 
	 * if (coordenada.length() < 2 || coordenada.length() > 3) {
	 * System.out.println("Coordenada inválida. Tente novamente."); continue; }
	 * 
	 * char letra = coordenada.charAt(0); int linha = letra - 'A'; // Convertendo a
	 * letra para o índice da linha
	 * 
	 * if (linha < 0 || linha >= TAMANHO_TABULEIRO) {
	 * System.out.println("Coordenada inválida. Tente novamente."); continue; }
	 * 
	 * int coluna; try { coluna = Integer.parseInt(coordenada.substring(1)); //
	 * Extrai o número da coordenada } catch (NumberFormatException e) {
	 * System.out.println("Coordenada inválida. Tente novamente."); continue; }
	 * 
	 * coluna--; // Ajusta para começar do índice 0 if (coluna < 0 || coluna >=
	 * TAMANHO_TABULEIRO) {
	 * System.out.println("Coordenada inválida. Tente novamente."); continue; }
	 * 
	 * jogadaValida = true;
	 * 
	 * if (tabuleiro[linha][coluna] == 'N') {
	 * System.out.println("Você acertou um navio!"); tabuleiro[linha][coluna] = 'X';
	 * // Marcador de acerto naviosRestantes--; } else if (tabuleiro[linha][coluna]
	 * == 'X' || tabuleiro[linha][coluna] == 'O') {
	 * System.out.println("Você já tentou essa posição. Tente novamente.");
	 * jogadaValida = false; // Permite que o jogador tente novamente na mesma
	 * jogada } else { System.out.println("Você errou."); tabuleiro[linha][coluna] =
	 * 'O'; // Marcador de tentativa falha } i++; }
	 * 
	 * exibirTabuleiro(tabuleiro); return naviosRestantes; }
	 * 
	 * public static String[] getNavios1() { return navios1; }
	 * 
	 * public static void setNavios1(String[] navios1) { BatalhaNaval.navios1 =
	 * navios1; }
	 * 
	 * public static String[] getNavios2() { return navios2; }
	 * 
	 * public static void setNavios2(String[] navios2) { BatalhaNaval.navios2 =
	 * navios2; }
	 * 
	 * public static String[] getTiros1() { return tiros1; }
	 * 
	 * public static void setTiros1(String[] tiros1) { BatalhaNaval.tiros1 = tiros1;
	 * }
	 * 
	 * public static String[] getTiros2() { return tiros2; }
	 * 
	 * public static void setTiros2(String[] tiros2) { BatalhaNaval.tiros2 = tiros2;
	 * }
	 * 
	 * public static int getQtd_tiros1() { return qtd_tiros1; }
	 * 
	 * public static void setQtd_tiros1(int qtd_tiros1) { BatalhaNaval.qtd_tiros1 =
	 * qtd_tiros1; }
	 * 
	 * public static int getQtd_tiros2() { return qtd_tiros2; }
	 * 
	 * public static void setQtd_tiros2(int qtd_tiros2) { BatalhaNaval.qtd_tiros2 =
	 * qtd_tiros2; }
	 */
}

