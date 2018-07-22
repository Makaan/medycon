package conexion;

import java.util.Map;
import java.util.Vector;
import almacenamiento.AlmacenamientoConexiones;

public class AdminConexiones {

	private static AdminConexiones instancia = null;
	private AlmacenamientoConexiones almacenamiento;
	
	private AdminConexiones() {
		almacenamiento = new AlmacenamientoConexiones();
	}
	
	public static AdminConexiones getInstancia() {
		if(instancia == null) {
			instancia = new AdminConexiones();
		}
		return instancia;
	}
	
	public void guardarConexion(String nombre, String ip, String puerto, String id, String tiempo, String alarmaMin, String alarmaMax) {
		almacenamiento.guardarConexion(nombre, ip, puerto, id, tiempo, alarmaMin, alarmaMax);
	}
	
	public String[] getNombreConexiones() {
		Map<String, Vector<String>> mapeo = almacenamiento.getConexiones();
		String[] conexiones = new String[mapeo.size()];
		int i = 0;
		for(String clave: mapeo.keySet()) {
			conexiones[i] = clave;
			i++;
		}
		return conexiones;
	}

	public Map<String,String> getConexion(String nombreConexion) {
		return almacenamiento.getConexion(nombreConexion);
		
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id, String tiempo, String alarmaMin, String alarmaMax) {
		almacenamiento.editarConexion(nombreViejo, nombreConexion, ip, puerto, id, tiempo, alarmaMin, alarmaMax);
	}

	public void eliminarConexion(String nombreConexion) {
		almacenamiento.eliminarConexion(nombreConexion);
		
	}

}
