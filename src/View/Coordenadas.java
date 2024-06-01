package View;
import java.awt.Point;
import java.util.List;

import Model.Navio;

public class Coordenadas {
    private List<Point>coords;

    public Coordenadas(List<Point>coords) {
        this.coords = coords;
    }

    public void coordenadaGraficaParaIndices(Navio navio) {
    	for(int i = 0; i < coords.size(); i++) {
    		int coluna =coords.get(i).x - 43;
    		int linha = coords.get(i).y - 7;
    		 System.out.println(linha);
    		 System.out.println(coluna);
    		navio.adicionarCoordenada(new Point(linha,coluna));
    	}
        
    }

}
