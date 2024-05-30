package Model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Navio {
    private int tamanho;
    private String tipo;
    private Color cor;
    private Shape shape;
    private boolean confirmed = false;
    private List<Point> coordenadas = new ArrayList<>();
    private int countRotation = 0;

    public List<Point> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Point> coordenadas) {
		this.coordenadas = coordenadas;
	}


	public enum TipodeNavio {
        submarino,
        destroyer,
        hidroaviao,
        cruzador,
        couracado;
    }

    public Navio(int tam) {
        this.setTamanho(tam);
        setTipoNavio(this);
    }

    public int getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(int tam) {
        this.tamanho = tam;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tip) {
        this.tipo = tip;
    }

    public Color getCor() {
        return this.cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setPosition(double x, double y) {
	        if (shape instanceof Rectangle2D.Double) {
	        	switch (countRotation) {
	        		case 0:
	        			((Rectangle2D.Double) shape).setRect(x, y, 30, 30 * this.tamanho);
	        			break;
	        		case 1:
	        			((Rectangle2D.Double) shape).setRect(x, y, 30  * this.tamanho, 30);
	        			break;
	        		case 2:
	        			((Rectangle2D.Double) shape).setRect(x, y - 30 * (this.tamanho - 1), 30, 30  * this.tamanho);
	        			break;
	        		case 3:
	        			((Rectangle2D.Double) shape).setRect(x - 30 * (this.tamanho - 1), y, 30  * this.tamanho, 30);
	        			break;
	        	}
	        } else if (shape instanceof Path2D.Double) {
	            Path2D.Double compoundShape = new Path2D.Double();
	            switch (countRotation) {
	            	case 0:
			            compoundShape.append(new Rectangle2D.Double(x, y, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x + 30, y - 30, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x + 60, y, 30, 30), false);
			            break;
	            	case 1:
		            	compoundShape.append(new Rectangle2D.Double(x, y, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x + 30, y + 30, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x, y + 60, 30, 30), false);
			            break;
	            	case 2:
		            	compoundShape.append(new Rectangle2D.Double(x, y, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x - 30, y + 30, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x - 60, y, 30, 30), false);
			            break;
		            case 3:
		            	compoundShape.append(new Rectangle2D.Double(x, y, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x - 30, y - 30, 30, 30), false);
			            compoundShape.append(new Rectangle2D.Double(x, y - 60, 30, 30), false);
			            break;
	            }
		        this.shape = compoundShape;
	        }
    }
   
    public int getRotationCount() {
    	return countRotation;
    }
    
    public void setRotationCount(int contRotacao) {
    	this.countRotation = contRotacao;
    }
    
    public void rotate() {
    	countRotation = (countRotation + 1) % 4;
    }

    public static void setTipoNavio(Navio nav) {
        int tam = nav.getTamanho();
        TipodeNavio nome = null;
        TipodeNavio tipos[] = TipodeNavio.values();
        switch (tam) {
            case 1:
                nome = tipos[0];
                nav.setCor(Color.GREEN.darker());
                nav.setShape(new Rectangle2D.Double());
                break;
            case 2:
                nome = tipos[1];
                nav.setCor(Color.BLUE);
                nav.setShape(new Rectangle2D.Double());
                break;
            case 3:
                nome = tipos[2];
                nav.setCor(Color.YELLOW);
                nav.setShape(new Path2D.Double());
                break;
            case 4:
                nome = tipos[3];
                nav.setCor(Color.ORANGE);
                nav.setShape(new Rectangle2D.Double());
                break;
            case 5:
                nome = tipos[4];
                nav.setCor(new Color(139, 69, 19));
                nav.setShape(new Rectangle2D.Double());
                break;
            default:
                nav.setTipo("indefinido");
                return;
        }
        nav.setTipo(nome.toString());
    }


public boolean isConfirmed() { 
    return confirmed;
}


public void setConfirmed(boolean confirmed) { 
    this.confirmed = confirmed;
}
}
