package almacenamiento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AlmacenamientoConexiones {
	

	private final String NOMBRE_ARCHIVO = "conexiones.txt";
	private AlmacenamientoArchivo almacenamientoArchivo;
	
	public AlmacenamientoConexiones() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
    }
	
	public void guardarConexion(String nombre, String ip, String id) {
		Map<String, Vector<String>> mapeoConexiones = getConexiones();
		if(mapeoConexiones.get(nombre) == null) {
			String conexion = nombre+","+ip+","+id;
			almacenamientoArchivo.guardarEntrada(conexion);
		}
		
	}
	
	public Vector<String> getConexion(String nombre) {
		Vector<String> vectorDatos = new Vector<String>();
		List<String> listaConexiones = almacenamientoArchivo.getEntradas();
		for(String conexion : listaConexiones) {
			String[] tokens = conexion.split(",");
			String nombreConexion = tokens[0];
			if(nombreConexion.equals(nombre)) {
				vectorDatos.add(tokens[1]); //IP
				vectorDatos.add(tokens[2]); //ID
			}
		}
		return vectorDatos;
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
