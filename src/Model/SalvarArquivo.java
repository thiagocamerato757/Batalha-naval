package Model;

import java.io.*;

public class SalvarArquivo{
	public static void EscreverArq(String[] navios1, String[] tiros1, int qtd_tiros1, String[] navios2, String[] tiros2, int qtd_tiros2) throws IOException {
		PrintWriter arq = null;
		
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
	
	public static void LerArq() throws IOException{
		BufferedReader arq = new BufferedReader(new FileReader("dados_partida.txt"));
		int num_navios = 5;
	    String[] navios1 = new String[num_navios];
	    String[] navios2 = new String[num_navios];
	    String[] tiros1 = new String[225];
	    String[] tiros2 = new String[225];
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
