package conexion;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;


public class Conexion {
	
	private final int PUERTO = 1000;

	private String ip;
	private String id;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public Conexion(String ip, String id) throws IOException {
		this.ip = ip;
		this.id = id;

		socket = new Socket(ip, PUERTO);
        System.out.println("conctando");
        	
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
	}
	
	public String consultarEstado() throws IOException {
		writer.println("<"+id+"?ET>");
		return reader.readLine();
	}
	
	public void cerrarConexion() throws IOException {
		socket.close();
	}
}
