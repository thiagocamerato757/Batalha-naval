package View;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Model.Navio;

public class Coordenadas {
    private List<Point>coords;
    private int right_x;
    private int down_y;

    public Coordenadas(List<Point>coords, int right_x, int down_y) {
    	this.right_x = right_x;
    	this.down_y = down_y;
        this.coords = coords;
    }

    public void coordenadaGraficaParaIndices(Navio navio) {
    	for(int i = 0; i < coords.size(); i++) {
    		int coluna = ((coords.get(i).x - right_x) / 30) - 1;
    		int linha = ((coords.get(i).y - down_y) / 30) - 1;
    		navio.adicionarCoordenada(new Point(coluna,linha));
    	}
    }
    
    public List<Point> coordenadaIndicesParaGrafica(Navio navio) {
    	List<Point> coordGrafica = new ArrayList<>();
        for (int i = 0; i < coords.size(); i++) {
            int coluna = ((coords.get(i).x + 1) * 30) + right_x;
            int linha = ((coords.get(i).y + 1) * 30) + down_y;
            coordGrafica.add(new Point(coluna, linha));
        }
        return coordGrafica;
    }

}
