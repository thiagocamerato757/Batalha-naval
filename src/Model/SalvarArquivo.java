package Model;

import java.io.*;

public class SalvarArquivo{
    private String[] navios1;
    private String[] tiros1;
    private int qtd_tiros1;
    private String[] navios2;
    private String[] tiros2;
    private int qtd_tiros2;
    
	// Getters e Setters
    public String[] getNavios1() {
        return navios1;
    }
    public void setNavios1(String[] navios1) {
        this.navios1 = navios1;
    }
    public String[] getTiros1() {
        return tiros1;
    }
    public void setTiros1(String[] tiros1) {
        this.tiros1 = tiros1;
    }
    public int getQtdTiros1() {
        return qtd_tiros1;
    }
    public void setQtdTiros1(int qtd_tiros1) {
        this.qtd_tiros1 = qtd_tiros1;
    }
    public String[] getNavios2() {
        return navios2;
    }
    public void setNavios2(String[] navios2) {
        this.navios2 = navios2;
   }
    public String[] getTiros2() {
        return tiros2;
    }
    public void setTiros2(String[] tiros2) {
        this.tiros2 = tiros2;
    }
    public int getQtdTiros2() {
        return qtd_tiros2;
    }
    public void setQtdTiros2(int qtd_tiros2) {
        this.qtd_tiros2 = qtd_tiros2;
    }
    
    // Método para escrever os dados no arquivo
	public void EscreverArq() throws IOException {
		PrintWriter arq = null;
		navios1 = BatalhaNaval.getNavios1();
		navios2 = BatalhaNaval.getNavios2();
		tiros1 = BatalhaNaval.getTiros1();
		tiros2 = BatalhaNaval.getTiros2();
		qtd_tiros1 = BatalhaNaval.getQtd_tiros1();
		qtd_tiros2 = BatalhaNaval.getQtd_tiros2();
		
		arq = new PrintWriter(new FileWriter("dados_partida.txt"));
		
		arq.write("Jogador 1\n");
		if (navios1 != null) {
			for (int i =0; i<5;i++) {
				arq.write(navios1[i]);
				arq.write("\n");
			}
		}
		arq.write("\n");
		if (qtd_tiros1 != 0) {
			for (int i =0; i<qtd_tiros1;i++) {
				if (tiros1[i] != null) {
					arq.write(tiros1[i]);
					arq.write("\n");
				}
			}
			arq.write("\n");
		}
		arq.write("Jogador 2\n");
		if (navios2 != null) {
			for (int i =0; i<5;i++) {
				arq.write(navios2[i]);
				arq.write("\n");
			}
		}
		arq.write("\n");
		if (qtd_tiros2 != 0) {
			for (int i =0; i<qtd_tiros2;i++) {
				if (tiros2[i]!=null) {
					arq.write(tiros2[i]);
					arq.write("\n");
				}
			}
		}
		
		arq.close();
	}
	
	public void LerArq() throws IOException{
		BufferedReader arq = new BufferedReader(new FileReader("dados_partida.txt"));
		int num_navios = 5;
	    setNavios1(new String[num_navios]);
	    setNavios2(new String[num_navios]);
	    setTiros1(new String[225]);
	    setTiros2(new String[225]);
		String s;
	    arq.readLine(); // le "jogador 1"
		for(int i = 0; i<num_navios; i++) {
			s = arq.readLine();
			navios1[i] = s;
		}
		int i =0;
		s = arq.readLine(); // le "\n"
		while(!s.equals("Jogador 2")) {
			if(!s.isEmpty()) {
				tiros1[i] = s;
				i++;
			}
			s = arq.readLine();
		}
		for(i = 0; i<num_navios; i++) {
			s = arq.readLine();
			navios2[i] = s;
		}
		i = 0;
		arq.readLine(); // le "\n"
		while((s = arq.readLine()) != null) {
			tiros2[i] = s;
			i++;
		}

		arq.close();
	}
}
