package View;

public class AtkSingleton { //faz com que a interface de ataque seja chamada apenas 1 vez, que é o correto
	private static AtkSingleton instance;
    private AttackFrame tabuleiro;

    private AtkSingleton() {
        tabuleiro = new AttackFrame("Tela de ataque");
    }

    public static synchronized AtkSingleton getInstance() {
        if (instance == null) {
            instance = new AtkSingleton();
        }
        return instance;
    }

    public AttackFrame getTabuleiro() {
        return tabuleiro;
    }
    
    public void resetInstance() {
    	instance = null;
    }
}

