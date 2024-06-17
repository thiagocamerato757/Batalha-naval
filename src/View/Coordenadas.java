package View;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Model.Navio;

public class Coordenadas {
    private List<Point> coords;
    private int right_x;
    private int down_y;

    // Construtor que inicializa as coordenadas, right_x e down_y
    public Coordenadas(List<Point> coords, int right_x, int down_y) {
        this.right_x = right_x;
        this.down_y = down_y;
        this.coords = coords;
    }

    // Converte coordenadas gráficas em índices e adiciona ao navio
    public void coordenadaGraficaParaIndices(Navio navio) {
        navio.getCoordenadas().clear(); // Limpa as coordenadas anteriores de cliques
        for (int i = 0; i < coords.size(); i++) {
            int coluna = ((coords.get(i).x - right_x) / 30) - 1;
            int linha = ((coords.get(i).y - down_y) / 30) - 1;
            navio.adicionarCoordenada(new Point(coluna, linha));
        }
    }

}
