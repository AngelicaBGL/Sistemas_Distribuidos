/**
 * Lab0: Leitura de Base de Dados N�o-Distribuida
 * 
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 20/02/2023
 * 
 * Referencias: 
 * https://docs.oracle.com/javase/tutorial/essential/io
 * 
 * Modificações: Angélica B. G. Luciano
 * Ultima atualização: 17/10/2024
 */

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths			
			.get("src\\fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				}// fim while

				System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm)
				throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(
					path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
					System.out.println(fortune.toString());

					System.out.println(lineCount);
				}// fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

	
		public void read(HashMap<Integer, String> hm) 
			throws FileNotFoundException {
				if (hm.isEmpty()) {
					System.out.println("A base de dados está vazia.");
					return;
				}
				
				SecureRandom random = new SecureRandom();
				int randomKey = random.nextInt(hm.size());  // Seleciona uma chave aleatória
				String fortuna = hm.get(randomKey);
			
				System.out.println("Fortuna aleatória:");
				System.out.println(fortuna);
		}
		

		
		public void write(HashMap<Integer, String> hm) 
			throws FileNotFoundException {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Digite a nova fortuna para adicionar:");
				String novaFortuna = scanner.nextLine();  // Lê a fortuna do usuário
			
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), true))) {
					writer.write(novaFortuna);
					writer.newLine();
					writer.write("%");  // Adiciona o separador de fortuna
					writer.newLine();
					System.out.println("Nova fortuna adicionada com sucesso.");
				} catch (IOException e) {
					System.out.println("SHOW: Exceção ao escrever no arquivo.");
					e.printStackTrace();
				}
		}
		
		

	}
	public void iniciar() {
		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap<Integer, String> hm = new HashMap<>();
			fr.parser(hm);  // Lê todas as fortunas do arquivo e armazena no HashMap
			fr.read(hm);    // Lê e exibe uma fortuna aleatória
			fr.write(hm);   // Adiciona uma nova fortuna ao arquivo
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}
