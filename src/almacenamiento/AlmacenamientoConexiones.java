package almacenamiento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class AlmacenamientoConexiones {
	

	private final String NOMBRE_ARCHIVO = "conexiones";
	private AlmacenamientoArchivo almacenamientoArchivo;
	
	public AlmacenamientoConexiones() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
    }
	
	public void guardarConexion(String nombre, String ip, String puerto, String id, String tiempo, String alarmaMin, String alarmaMax) {
		Map<String, Vector<String>> mapeoConexiones = getConexiones();
		if(mapeoConexiones.get(nombre) == null) {
			String conexion = nombre+","+ip+","+puerto+","+id+","+tiempo+","+alarmaMin+","+alarmaMax;
			almacenamientoArchivo.guardarEntrada(conexion);
		}
		
	}
	
	public Map<String, String> getConexion(String nombre) {
		Map<String, String> mapeoDatos = new HashMap<String, String>();
		List<String> listaConexiones = almacenamientoArchivo.getEntradas();
		for(String conexion : listaConexiones) {
			String[] tokens = conexion.split(",");
			String nombreConexion = tokens[0];
			if(nombreConexion.equals(nombre)) {
				mapeoDatos.put("nombre", tokens[0]);
				mapeoDatos.put("ip", tokens[1]);
				mapeoDatos.put("puerto", tokens[2]);
				mapeoDatos.put("id", tokens[3]);
				mapeoDatos.put("tiempo", tokens[4]);
				mapeoDatos.put("alarmaMin", tokens[5]);
				mapeoDatos.put("alarmaMax", tokens[6]);
			}
		}
		return mapeoDatos;
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
			vectorDatos.add(tokens[5]); //Alarma min
			vectorDatos.add(tokens[6]); //Alarma max
			mapeoConexiones.put(nombre, vectorDatos);
		}
		return mapeoConexiones;
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id, String tiempo, String alarmaMin, String alarmaMax) {
		Map<String, Vector<String>> conexiones = getConexiones();
		conexiones.remove(nombreViejo);
		
		almacenamientoArchivo.borrarEntradas();
		for(Entry<String, Vector<String>> conexion: conexiones.entrySet()) {
			Vector<String> datos = conexion.getValue();
			String datosConexion = conexion.getKey()+",";
			for(String dato: datos) {
				
				datosConexion+= dato+",";
			}
			datosConexion = datosConexion.substring(0, datosConexion.length()-1);
			almacenamientoArchivo.guardarEntrada(datosConexion);
		}
		guardarConexion(nombreConexion, ip, puerto, id, tiempo, alarmaMin, alarmaMax);

	}

	public void eliminarConexion(String nombreConexion) {
		Map<String, Vector<String>> conexiones = getConexiones();
		conexiones.remove(nombreConexion);
		
		almacenamientoArchivo.borrarEntradas();
		for(Entry<String, Vector<String>> conexion: conexiones.entrySet()) {
			Vector<String> datos = conexion.getValue();
			almacenamientoArchivo.guardarEntrada(conexion.getKey()+","+datos.get(0)+","+datos.get(1)+","+datos.get(2)+","+datos.get(3));
		}
		
	}
}
