package Model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;

public class Navio {
    private int tamanho;
    private String tipo;
    private Color cor;
    private Shape shape;
    private boolean confirmed = false;

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
            ((Rectangle2D.Double) shape).setRect(x, y, 30, 30 * this.tamanho);
        } else if (shape instanceof Path2D.Double) {
            Path2D.Double compoundShape = new Path2D.Double();
            compoundShape.append(new Rectangle2D.Double(x, y, 30, 30), false);
            compoundShape.append(new Rectangle2D.Double(x + 30, y - 30, 30, 30), false);
            compoundShape.append(new Rectangle2D.Double(x + 60, y, 30, 30), false);
            this.shape = compoundShape;
        }
    }

    public static void setTipoNavio(Navio nav) {
        int tam = nav.getTamanho();
        TipodeNavio nome = null;
        TipodeNavio tipos[] = TipodeNavio.values();
        switch (tam) {
            case 1:
                nome = tipos[0];
                nav.setCor(Color.GREEN);
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
                nav.setCor(Color.RED);
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
