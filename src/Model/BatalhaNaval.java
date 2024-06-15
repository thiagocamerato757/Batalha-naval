package Model;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import View.Coordenadas;

public class BatalhaNaval {
    private static final int TAMANHO_TABULEIRO = 15;
    private final int NUM_NAVIOS = 15;
    private final int[][] tabuleiro = new int[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private int qtd_tiros = 0;
    private int naviosRestantes = NUM_NAVIOS;
    private ArrayList<Navio> navios = new ArrayList<>();
    
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
    protected ArrayList<Navio> getNavios() {
    	return this.navios;
    }
    
    public int getnaviosRestantes() {
    	return this.naviosRestantes;
    }
    
    protected void setNavios(ArrayList<Navio>navios) {
    	this.navios = navios;
    }
    
    protected void addNavios(Navio navio) {
    	this.navios.add(navio);
    }
    
    protected int getQtd_tiros() {
		return qtd_tiros;
	}
    
	protected void setQtd_tiros(int qtd_tiros) {
		this.qtd_tiros = qtd_tiros;
	}
	
	protected int getTAMANHO_TABULEIRO() {
		return TAMANHO_TABULEIRO;
	}
	
	protected int getNUM_NAVIOS() {
		return NUM_NAVIOS;
	}
	
	protected void setNaviosRestantes(int naviosRestantes) {
		this.naviosRestantes = naviosRestantes;
	}
	
	protected void exibirTabuleiro(int[][] tabuleiro) {
        for (int i = 0; i < BatalhaNaval.TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < BatalhaNaval.TAMANHO_TABULEIRO; j++) {
                //System.out.print(tabuleiro[i][j]);
            }
            //System.out.println();
        }
        //System.out.println();
    }
    
    protected static int getTamanhoTabuleiro() {
		return TAMANHO_TABULEIRO;
	}
    
    protected void AtualizaTab_Pos(Navio navio) {
        int[][] tabuleiro = getTabuleiro();
        List<Point> coordenadas = navio.getCoordenadas();
        
        for (Point coord : coordenadas) {
            int x = (int) coord.getX();
            int y = (int) coord.getY();
            
            // Verificação dos limites do tabuleiro
            if (x < 0 || x >= tabuleiro.length || y < 0 || y >= tabuleiro[0].length) {
                System.out.println("Coordenada fora dos limites: (" + x + ", " + y + ")");
                throw new ArrayIndexOutOfBoundsException("Coordenada fora dos limites: (" + x + ", " + y + ")");
            }
            
            tabuleiro[x][y] = 1; // Supondo que 1 indica a presença de um navio
        }
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
	protected int[][] getTabuleiro() {
		return this.tabuleiro;
	}
	
	protected int getNaviosRestantes() {
		// TODO Auto-generated method stub
		return this.naviosRestantes;
	}
}