package conexion;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;

import controlador.AdminMensajes;


public class Conexion {
	
	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();

	private String ip;
	private String id;
	private int puerto;
	private int tiempo;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public Conexion(String ip, String puerto, String id, String tiempo) throws IOException {
		this.ip = ip;
		this.puerto = Integer.parseInt(puerto);
		this.id = id;
		this.tiempo = Integer.parseInt(tiempo)/2;
		
		socket = new Socket(this.ip, this.puerto);
        	
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String consultarEstado() {
		writer.println("<"+id+"?ET>");
		String result = "";
		try {
			result = reader.readLine();
		} catch (IOException e) {
			adminMensajes.mostrarMensajeError("Error al consultar el dispositivo en ip: "+ip+":"+puerto);
			e.printStackTrace();
		}
		return result;
	}
	
	public void cerrarConexion() {
		try {
			socket.close();
		} catch (IOException e) {
			adminMensajes.mostrarMensajeError("Error al cerrar la conexion con el dispositivo: "+ip+":"+puerto);
			e.printStackTrace();
		}
	}
	
	public Integer getTiempo() {
		return tiempo;
	}
	
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	
	public String toString() {
		return "Conexion: ip "+ip+" puerto "+puerto+" id "+id;
	}
}
