/**
 * Laboratorio 2 de Sistemas Distribuidos
 * 
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 17/12/2022
 * 
 * Modificação: Angélica Beatriz Gonelli Luciano
 * Ultima atualização: 24/10/2024
 */
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;//json

public class Cliente {
    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;

    private int porta = 1025;

    public void iniciar() {
        System.out.println("Cliente iniciado na porta: " + porta);
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        try {
            socket = new Socket("127.0.0.1", porta);
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            // Menu
            while (true) {
                System.out.println("Escolha uma opção:");
                System.out.println("1. Ler fortuna");
                System.out.println("2. Escrever fortuna");
                System.out.println("3. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // ler o /n

                if (opcao == 1) {//leitura
                    String request = gson.toJson(Map.of("method", "read", "args", new String[]{""}));
                    enviarRequest(request);

                } else if (opcao == 2) {//escrita
                    System.out.println("Digite a nova fortuna:");
                    String novaFortuna = scanner.nextLine();
                    String request = gson.toJson(Map.of("method", "write", "args", new String[]{novaFortuna}));
                    enviarRequest(request);

                } else if (opcao == 3) {//sair
                    System.out.println("Encerrando cliente.");
                    break;
                } else {
                    System.out.println("Opção inválida.");
                }
            }

            socket.close(); // fechar socket
            scanner.close();// fechar scanner
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // enviar a requisição do cliente para o servidor
    private void enviarRequest(String jsonRequest) {
        try {
            saida.writeUTF(jsonRequest);
            String response = entrada.readUTF();
            System.out.println("Resposta do servidor: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().iniciar();
    }
}
