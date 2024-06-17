package Model;

import java.util.ArrayList;

public class BatalhaNaval {
    // Constante para o tamanho do tabuleiro
    private static final int TAMANHO_TABULEIRO = 15;
    // Número de navios no jogo
    private final int NUM_NAVIOS = 15;
    // Matriz que representa o tabuleiro do jogo
    private final int[][] tabuleiro = new int[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    // Quantidade de tiros disparados
    private int qtd_tiros = 0;
    // Número de navios restantes no jogo
    private int naviosRestantes = NUM_NAVIOS;
    // Lista de navios no jogo
    private ArrayList<Navio> navios = new ArrayList<>();
    

    // Retorna a lista de navios
    protected ArrayList<Navio> getNavios() {
        return this.navios;
    }
    
    // Retorna o número de navios restantes
    public int getnaviosRestantes() {
        return this.naviosRestantes;
    }
    
    // Define a lista de navios
    protected void setNavios(ArrayList<Navio> navios) {
        this.navios = navios;
    }
    
    // Adiciona um navio à lista de navios
    protected void addNavios(Navio navio) {
        this.navios.add(navio);
    }
    
    // Retorna a quantidade de tiros disparados
    protected int getQtd_tiros() {
        return qtd_tiros;
    }
    
    // Define a quantidade de tiros disparados
    protected void setQtd_tiros(int qtd_tiros) {
        this.qtd_tiros = qtd_tiros;
    }
    
    // Retorna o tamanho do tabuleiro
    protected int getTAMANHO_TABULEIRO() {
        return TAMANHO_TABULEIRO;
    }
    
    // Retorna o número de navios no jogo
    protected int getNUM_NAVIOS() {
        return NUM_NAVIOS;
    }
    
    // Define o número de navios restantes
    protected void setNaviosRestantes(int naviosRestantes) {
        this.naviosRestantes = naviosRestantes;
    }
    
    
    // Retorna o tamanho do tabuleiro (estático)
    protected static int getTamanhoTabuleiro() {
        return TAMANHO_TABULEIRO;
    }

    // Retorna a matriz do tabuleiro
    protected int[][] getTabuleiro() {
        return this.tabuleiro;
    }
    
    // Retorna o número de navios restantes
    protected int getNaviosRestantes() {
        return this.naviosRestantes;
    }
}
