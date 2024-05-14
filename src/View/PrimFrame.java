package View;

import javax.swing.*;

public class PrimFrame extends JFrame {
	JPanel p = new JPanel();
	public final int LARG_DEFAULT=1000;
	public final int ALT_DEFAULT=1000;

	public PrimFrame(String s) {
		super(s);

		setSize(LARG_DEFAULT,ALT_DEFAULT);
		getContentPane().add(new PrimPanel());
	}


	public static void main(String[] args) {
			PrimFrame f=new PrimFrame("Batalha naval");
			f.setVisible(true);
	}
}
