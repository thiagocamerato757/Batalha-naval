package Model;
import java.io.IOException;
import java.util.Scanner;

public class BatalhaNaval {
    private static final int TAMANHO_TABULEIRO = 15;
    private static final int NUM_NAVIOS = 5;
    private static final char[][] tabuleiro1 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static final char[][] tabuleiro2 = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    private static String[] navios1 = new String[NUM_NAVIOS];
    private static String[] navios2 = new String[NUM_NAVIOS];
    private static String[] tiros1 = new String[225];
    private static String[] tiros2 = new String[225];
    private static int qtd_tiros1;
    private static int qtd_tiros2;
    private static int naviosRestantes1 = NUM_NAVIOS;
    private static int naviosRestantes2 = NUM_NAVIOS;

    public static void main(String[] args) throws IOException {
        System.out.println("Jogador 1, posicione seus navios:");
        inicializarTabuleiro(tabuleiro1);
        exibirTabuleiro(tabuleiro1);

        System.out.println("Jogador 2, posicione seus navios:");
        inicializarTabuleiro(tabuleiro2);
        exibirTabuleiro(tabuleiro2);
        
        SalvarArquivo.EscreverArq(navios1,tiros1,qtd_tiros1,navios2,tiros2,qtd_tiros2);

        while (naviosRestantes1 > 0 && naviosRestantes2 > 0) {
            System.out.println("Vez do Jogador 1:");
            naviosRestantes2 = realizarJogada(tabuleiro2, naviosRestantes2);
            SalvarArquivo.EscreverArq(navios1,tiros1,qtd_tiros1,navios2,tiros2,qtd_tiros2);
            System.out.println("\nNavios restantes do Jogador 2: " + naviosRestantes2);
            if(naviosRestantes2 == 0) break;
            System.out.println("Vez do Jogador 2:");
            naviosRestantes1 = realizarJogada(tabuleiro1, naviosRestantes1);
            SalvarArquivo.EscreverArq(navios1,tiros1,qtd_tiros1,navios2,tiros2,qtd_tiros2);
            SalvarArquivo.LerArq();
            System.out.println("\nNavios restantes do Jogador 1: " + naviosRestantes1);
        }
        

        if (naviosRestantes1 == 0) {
            System.out.println("Parabéns, Jogador 2! Você afundou todos os navios do Jogador 1!");
        } else {
            System.out.println("Parabéns, Jogador 1! Você afundou todos os navios do Jogador 2!");
        }
    }

    private static void inicializarTabuleiro(char[][] tabuleiro) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < NUM_NAVIOS; i++) {
            boolean posicaoValida = false;
            while (!posicaoValida) {
                System.out.print("Posicione o navio " + (i + 1) + " (coordenada letra número), por exemplo, A3: ");
                String coordenada = scanner.nextLine().toUpperCase(); // Converte para maiúsculas para tratamento consistente
                if (tabuleiro == tabuleiro1) {
                	navios1[i] = coordenada;
                }
                else {
                	navios2[i] = coordenada;
                }

                if (coordenada.length() < 2 || coordenada.length() > 3) {
                    System.out.println("Coordenada inválida. Tente novamente.");
                    continue;
                }

                char letra = coordenada.charAt(0);
                int linha = letra - 'A'; // Convertendo a letra para o índice da linha

                if (linha < 0 || linha >= TAMANHO_TABULEIRO) {
                    System.out.println("Coordenada inválida. Tente novamente.");
                    continue;
                }

                int coluna;
                try {
                    coluna = Integer.parseInt(coordenada.substring(1)); // Extrai o número da coordenada
                } catch (NumberFormatException e) {
                    System.out.println("Coordenada inválida. Tente novamente.");
                    continue;
                }

                coluna--; // Ajusta para começar do índice 0
                if (coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
                    System.out.println("Coordenada inválida. Tente novamente.");
                    continue;
                }

                if (tabuleiro[linha][coluna] == 'N') {
                    System.out.println("Já existe um navio nesta posição. Tente novamente.");
                } else {
                    tabuleiro[linha][coluna] = 'N'; // Navio
                    posicaoValida = true;
                }
            }
        }
    }

    private static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("    1  2  3  4  5  6  7  8  9 10 11 12 13 14 15");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            System.out.format("%c  ", (i + 'A'));
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == 'N') {
                	System.out.print(" ~ ");
                } else if(tabuleiro[i][j] == 'X'){
                    System.out.print(" X ");
                   
                }else if(tabuleiro[i][j] == 'O') {
                	System.out.print(" O ");
                }else {
                	System.out.print(" ~ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }



    private static int realizarJogada(char[][] tabuleiro, int naviosRestantes) {
        Scanner scanner = new Scanner(System.in);
        boolean jogadaValida = false;
        int i = 0;
        while (!jogadaValida) {
            System.out.print("Digite a coordenada (letra número), por exemplo, A3: ");
            String coordenada = scanner.nextLine().toUpperCase(); // Converte para maiúsculas para tratamento consistente
            if (tabuleiro == tabuleiro2) {
            	tiros1[i] = coordenada;
            	qtd_tiros1++;
            }
            else {
            	tiros2[i] = coordenada;
            	qtd_tiros2++;
            }
            
            if (coordenada.length() < 2 || coordenada.length() > 3) {
                System.out.println("Coordenada inválida. Tente novamente.");
                continue;
            }

            char letra = coordenada.charAt(0);
            int linha = letra - 'A'; // Convertendo a letra para o índice da linha

            if (linha < 0 || linha >= TAMANHO_TABULEIRO) {
                System.out.println("Coordenada inválida. Tente novamente.");
                continue;
            }

            int coluna;
            try {
                coluna = Integer.parseInt(coordenada.substring(1)); // Extrai o número da coordenada
            } catch (NumberFormatException e) {
                System.out.println("Coordenada inválida. Tente novamente.");
                continue;
            }

            coluna--; // Ajusta para começar do índice 0
            if (coluna < 0 || coluna >= TAMANHO_TABULEIRO) {
                System.out.println("Coordenada inválida. Tente novamente.");
                continue;
            }

            jogadaValida = true;

            if (tabuleiro[linha][coluna] == 'N') {
                System.out.println("Você acertou um navio!");
                tabuleiro[linha][coluna] = 'X'; // Marcador de acerto
                naviosRestantes--;
            } else if (tabuleiro[linha][coluna] == 'X' || tabuleiro[linha][coluna] == 'O') {
                System.out.println("Você já tentou essa posição. Tente novamente.");
                jogadaValida = false; // Permite que o jogador tente novamente na mesma jogada
            } else {
                System.out.println("Você errou.");
                tabuleiro[linha][coluna] = 'O'; // Marcador de tentativa falha
            }
            i++;
        }
        
        exibirTabuleiro(tabuleiro);
        return naviosRestantes;
    }
}

