package View;

public class TrocaContexto extends Model.TransitaEstadosTabuleiro{
	private AtkSingleton atq = AtkSingleton.getInstance();
	@Override
	public void trocaPraAtaque() {
		this.atq.getTabuleiro().setVisible(true);
	}
}
