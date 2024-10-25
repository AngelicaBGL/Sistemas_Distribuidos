import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Servidor {
    private static Socket socket;
    private static ServerSocket server;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    private static final Path path = Paths.get("src/fortune-br.txt");
    
	private int porta = 1025;

    public void iniciar() {
        System.out.println("Servidor iniciado na porta: " + porta);
        try {
            server = new ServerSocket(porta);
            socket = server.accept();  // Bloqueia até receber uma conexão

            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            // Lê a mensagem JSON do cliente
            String jsonRequest = entrada.readUTF();// requisição do cliente
            String jsonResponse = processar(jsonRequest);//resposta do servidor

            // Envia a resposta JSON ao cliente
            saida.writeUTF(jsonResponse);

            
		
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	// processa a escolha do cliente
    private String processar(String jsonRequest) {
        Gson gson = new Gson();
        JsonObject request = gson.fromJson(jsonRequest, JsonObject.class);
        String metodo = request.get("method").getAsString();

        switch (metodo) {
            case "read":
                String fortuna = getFortunaAleatoria();
                return gson.toJson(Map.of("result", fortuna));

            case "write":
                String novaFortuna = request.getAsJsonArray("args").get(0).getAsString();
                boolean sucesso = adicionarFortuna(novaFortuna);
                return gson.toJson(Map.of("result", sucesso ? "true" : "false"));

            default:
                return gson.toJson(Map.of("result", "false"));
        }
    }

	// Sorteia uma fortuna aleatória para o cliente
	private String getFortunaAleatoria() {
		try (BufferedReader reader = Files.newBufferedReader(path, java.nio.charset.StandardCharsets.UTF_8)) {
			List<String> fortunas = new ArrayList<>();
			StringBuilder fortuna = new StringBuilder();
			String linha;
	
			while ((linha = reader.readLine()) != null) {
				if (linha.equals("%")) {
					fortunas.add(fortuna.toString());
					fortuna.setLength(0);  // Limpa o StringBuilder para a próxima fortuna
				} else {
					fortuna.append(linha).append("\n");
				}
			}
			return fortunas.get(new Random().nextInt(fortunas.size()));
	
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro ao ler a fortuna.";
		}
	}
	
	//escreve uma nova fortuna no arquivo .txt
    private boolean adicionarFortuna(String novaFortuna) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), true))) {
            writer.write(novaFortuna);
            writer.newLine();
            writer.write("%");//separador de fortunas
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new Servidor().iniciar();
    }
}
