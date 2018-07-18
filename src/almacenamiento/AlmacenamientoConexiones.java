package almacenamiento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class AlmacenamientoConexiones {
	

	private final String NOMBRE_ARCHIVO = "conexiones.txt";
	private AlmacenamientoArchivo almacenamientoArchivo;
	
	public AlmacenamientoConexiones() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
    }
	
	public void guardarConexion(String nombre, String ip, String puerto, String id, String tiempo) {
		Map<String, Vector<String>> mapeoConexiones = getConexiones();
		if(mapeoConexiones.get(nombre) == null) {
			String conexion = nombre+","+ip+","+puerto+","+id+","+tiempo;
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
				vectorDatos.add(tokens[2]); //Puerto
				vectorDatos.add(tokens[3]); //ID
				vectorDatos.add(tokens[4]); //tiempo
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
			Vector<String> vectorDatos = new Vector<String>();
			vectorDatos.add(tokens[1]); //IP
			vectorDatos.add(tokens[2]); //puerto
			vectorDatos.add(tokens[3]);	//id
			vectorDatos.add(tokens[4]); //Tiempo
			mapeoConexiones.put(nombre, vectorDatos);
		}
		return mapeoConexiones;
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id, String tiempo) {
		Map<String, Vector<String>> conexiones = getConexiones();
		conexiones.remove(nombreViejo);
		
		almacenamientoArchivo.borrarEntradas();
		for(Entry<String, Vector<String>> conexion: conexiones.entrySet()) {
			Vector<String> datos = conexion.getValue();
			almacenamientoArchivo.guardarEntrada(conexion.getKey()+","+datos.get(0)+","+datos.get(1)+","+datos.get(2)+","+datos.get(3));
		}
		almacenamientoArchivo.guardarEntrada(nombreConexion+","+ip+","+puerto+","+id+","+tiempo);

	}
}
