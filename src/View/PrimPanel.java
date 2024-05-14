package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class PrimPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;

		double leftX=100.0;
		double topY=100.0;
		double larg=50.0;
		double alt=50.0;
		int i, j = 0;
		while(j<15) {
			i = 0;
			leftX=100.0;
			while(i<15) {
				Rectangle2D rt=new Rectangle2D.Double(leftX,topY,larg,alt);
				leftX+=50;
				g2d.draw(rt);
				i++;
			}
			topY+=50;
			j++;
		}
	}
}