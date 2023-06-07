package br.org.catolicasc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GreetClient {
	private Socket clientSocket;

	private PrintWriter out;

	private BufferedReader in;

	public void start(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		//handle escrita de dados
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		//handler leitura de dados
		in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
	}

	public void stop() {
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar o cliente");
		}
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg); //manda mensagem para o servidor
		return in.readLine(); //retorna resposta do servidor
	}

	public static void main(String[] args) {
		GreetClient client = new GreetClient();
		try {
			client.start("127.0.0.1", 6666);
			String response = client.sendMessage("hello server");
			System.out.println("Resposta do servidor: " + response);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao iniciar o cliente", e);
		} finally {
			client.stop();
		}
	}
}