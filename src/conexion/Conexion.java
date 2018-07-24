package conexion;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controlador.AdminMensajes;


public class Conexion {
	
	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();

	private String ip;
	private Map<String, String> ids;
	private int puerto;
	private int tiempo;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	
	public Conexion(String nombreConexion, String ip, String puerto, String id, String tiempo) throws IOException {
		this.ip = ip;
		this.puerto = Integer.parseInt(puerto);
		this.ids = new HashMap<String, String>();
		ids.put(nombreConexion, id);
		this.tiempo = Integer.parseInt(tiempo)/2;
		
		socket = new Socket(this.ip, this.puerto);
        	
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String consultarEstado(String nombreConexion) {
		writer.println("<"+ids.get(nombreConexion)+"?ET>");
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
	
	public String getIp() {
		return ip;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public Integer getTiempo() {
		return tiempo;
	}
	
	public String idToNombre(String id) {
		String toReturn = null;
		for(Entry<String, String> e: ids.entrySet()) {
			if(e.getValue().equals(id)) {
				toReturn = e.getKey();
				break;
			}
		}
		return toReturn;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public void addId(String nombre, String id) {
		ids.put(nombre, id);
	}
	
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	
	public String toString() {
		return "Conexion: ip "+ip+" puerto "+puerto+" id "+ids.values().toString();
	}
}
