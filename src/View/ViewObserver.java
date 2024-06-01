package View;

public class ViewObserver {
	private PrimFrame frame;
	
	public ViewObserver(PrimFrame frame){
		this.frame = frame;
	}
	public void update() {
        // Atualize a interface do usuário conforme necessário
        frame.repaint(); // Por exemplo, repinte o painel quando o estado do jogo mudar
    }
}
