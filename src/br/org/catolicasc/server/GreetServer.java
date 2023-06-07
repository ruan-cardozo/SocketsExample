package br.org.catolicasc.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;

public class GreetServer {
	private ServerSocket serverSocket;

	private Socket clientSocket;

	private PrintWriter out;

	private BufferedReader in;

	public void start(int port) throws IOException {
		//inicializar atributos
		serverSocket = new ServerSocket(port); //escuta na porta port
		clientSocket = serverSocket.accept();  //espera conexão
		//handle para escrita de dados
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		//handle para leitura de dados
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		clientHandler(); //trata a conexão

	}

	private void clientHandler() throws IOException{
		String greeting = in.readLine(); //lê mensagem do cliente
		if ("hello server".equals(greeting)) {
			out.println("hello client"); //envia mensagem para o cliente
		} else {
			out.println("unrecognised greeting");
		}
	}
	public void stop() {
		try {
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar o servidor");
		}
	}

	public static void main(String[] args){
		GreetServer server=new GreetServer();
		try {
			server.start(6666);
		} catch (IOException e) {
			System.out.println("Erro ao iniciar o servidor");
		} finally {
			server.stop();
		}
	}
}