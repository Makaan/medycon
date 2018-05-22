package almacenamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AlmacenamientoConexiones {
	

	private final String NOMBRE_ARCHIVO = "conexiones.txt";
	private static AlmacenamientoConexiones instancia;
	private AlmacenamientoArchivo almacenamientoArchivo;
	
	private AlmacenamientoConexiones() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
    }
	
	public static AlmacenamientoConexiones getInstancia() {
		if(instancia == null) {
			instancia = new AlmacenamientoConexiones();
		}
		return instancia;
	}
	
	public void guardarConexion(String nombre, String ip, String id) {
		Map<String, Vector<String>> mapeoConexiones = getConexiones();
		if(mapeoConexiones.get(nombre) == null) {
			String conexion = nombre+","+ip+","+id;
			almacenamientoArchivo.guardarEntrada(conexion);
		}
		
	}
	
	public Map<String, Vector<String>> getConexiones() {
		HashMap<String, Vector<String>> mapeoConexiones = new HashMap<String, Vector<String>>();
		List<String> listaConexiones = almacenamientoArchivo.getEntradas();
		for(String conexion : listaConexiones) {
			String[] tokens = conexion.split(",");
			String nombre = tokens[0];
			Vector<String> vectorDatos = new Vector<String>(2);
			vectorDatos.add(tokens[1]); //IP
			vectorDatos.add(tokens[2]); //ID
			mapeoConexiones.put(nombre, vectorDatos);
		}
		return mapeoConexiones;
	}
}
