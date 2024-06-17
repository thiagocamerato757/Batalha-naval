package Model;

public abstract class TransitaEstadosTabuleiro {
    // Contador estático para rastrear quantos estão prontos
    private static int contProntos = 0;

    // Atualiza o estado do tabuleiro para indicar se está pronto para batalha
    public void atualizaEstadoTab(BatalhaNavalFacade tabuleiro, boolean TF) {
        tabuleiro.setEstadoPos(TF); // Define o estado de posicionamento no tabuleiro
        if (TF) {
            TransitaEstadosTabuleiro.contProntos++; // Incrementa o contador se o tabuleiro está pronto
        }
    }

    // Retorna o número de tabuleiros prontos
    public static int getContProntos() {
        return contProntos;
    }

    // Método abstrato para trocar o estado do jogo para o modo de ataque
    public abstract void trocaPraAtaque();
}
