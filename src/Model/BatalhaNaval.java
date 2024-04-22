package Model;
import java.util.Scanner;

public class BatalhaNaval {
    private static final int TAMANHO_TABULEIRO = 5;
    private static final int NUM_NAVIOS = 3;
    private static final char[][] tabuleiro1 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final char[][] tabuleiro2 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static int naviosRestantes1 = NUM_NAVIOS;
    private static int naviosRestantes2 = NUM_NAVIOS;

    public static void main(String[] args) {
        System.out.println("Jogador 1, posicione seus navios:");
        inicializarTabuleiro(tabuleiro1);
        exibirTabuleiro(tabuleiro1);

        System.out.println("Jogador 2, posicione seus navios:");
        inicializarTabuleiro(tabuleiro2);
        exibirTabuleiro(tabuleiro2);

        while (naviosRestantes1 > 0 && naviosRestantes2 > 0) {
            System.out.println("Vez do Jogador 1:");
            naviosRestantes2 = realizarJogada(tabuleiro2, naviosRestantes2);
            System.out.println("\nNavios restantes do Jogador 2: " + naviosRestantes2);
            if(naviosRestantes2 == 0) break;
            System.out.println("Vez do Jogador 2:");
            naviosRestantes1 = realizarJogada(tabuleiro1, naviosRestantes1);
            System.out.println("\nNavios restantes do Jogador 1: " + naviosRestantes1);
        }

        if (naviosRestantes1 == 0) {
            System.out.println("Parabéns, Jogador 2! Você afundou todos os navios do Jogador 1!");
        } else {
            System.out.println("Parabéns, Jogador 1! Você afundou todos os navios do Jogador 2!");
        }
    }

    private static void inicializarTabuleiro(char[][] tabuleiro) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                tabuleiro[i][j] = '~'; // Água
            }
        }

        // Posicionar navios
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < NUM_NAVIOS; i++) {
            System.out.println("Posicione o navio " + (i + 1) + " (formato: linha coluna):");
            int linha = scanner.nextInt();
            int coluna = scanner.nextInt();
            if (linha < 0 || linha >= TAMANHO_TABULEIRO || coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
                System.out.println("Posição inválida. Tente novamente.");
                i--; // Tenta novamente posicionar o mesmo navio
            } else if (tabuleiro[linha][coluna] == 'N') {
                System.out.println("Já existe um navio nesta posição. Tente novamente.");
                i--; // Tenta novamente posicionar o mesmo navio
            } else {
                tabuleiro[linha][coluna] = 'N'; // Navio
            }
        }
    }

    private static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("  0 1 2 3 4");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == 'N') {
                    System.out.print("~ "); // Água escondida
                } else {
                    System.out.print(tabuleiro[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int realizarJogada(char[][] tabuleiro, int naviosRestantes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a linha: ");
        int linha = scanner.nextInt();
        System.out.print("Digite a coluna: ");
        int coluna = scanner.nextInt();

        if (linha < 0 || linha >= TAMANHO_TABULEIRO || coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
            System.out.println("Jogada inválida. Tente novamente.");
            return naviosRestantes;
        }

        if (tabuleiro[linha][coluna] == 'N') {
            System.out.println("Você acertou um navio!");
            tabuleiro[linha][coluna] = 'X'; // Marcador de acerto
            naviosRestantes--;
        } else if (tabuleiro[linha][coluna] == 'X' || tabuleiro[linha][coluna] == 'O') {
            System.out.println("Você já tentou essa posição. Tente novamente.");
        } else {
            System.out.println("Você errou otario.");
            tabuleiro[linha][coluna] = 'O'; // Marcador de tentativa falha
        }

        exibirTabuleiro(tabuleiro);
        return naviosRestantes;
    }
}

