package View;
import javax.swing.*;

public class NomeForm {
    private String jogador1;
    private String jogador2;

    public NomeForm() {
        iniciarPartida();
    }

    private void iniciarPartida() {
        // Exibir diálogo para inserção do nome do jogador 1
        jogador1 = JOptionPane.showInputDialog(null, "Insira o nome do jogador 1:", "Identificação do Jogador 1", JOptionPane.QUESTION_MESSAGE);
        if (jogador1 == null || jogador1.isEmpty()) { //caso clique somente no ok o nome por padrão vai ser jogador 1
            jogador1 = "Jogador 1";
        }

        // Exibir diálogo para inserção do nome do jogador 2
        jogador2 = JOptionPane.showInputDialog(null, "Insira o nome do jogador 2:", "Identificação do Jogador 2", JOptionPane.QUESTION_MESSAGE);
        if (jogador2 == null || jogador2.isEmpty()) { //caso clique somente no ok o nome por padrão vai ser jogador 2
            jogador2 = "Jogador 2";
        }

        // Exibir os nomes dos jogadores
        System.out.println("Jogador 1: " + jogador1);
        System.out.println("Jogador 2: " + jogador2);
    }

    public static void main(String[] args) {
        new NomeForm();
    }
}
