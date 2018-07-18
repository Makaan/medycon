package conexion;

import java.util.Map;
import java.util.Vector;
import almacenamiento.AlmacenamientoConexiones;

public class AdminConexiones {

	private static AdminConexiones instancia = null;
	private AlmacenamientoConexiones almacenamiento;
	
	private AdminConexiones() {
		System.out.println("new adminConexiones");
		almacenamiento = new AlmacenamientoConexiones();
	}
	
	public static AdminConexiones getInstancia() {
		if(instancia == null) {
			instancia = new AdminConexiones();
		}
		return instancia;
	}
	
	public void guardarConexion(String nombre, String ip, String puerto, String id, String tiempo) {
		almacenamiento.guardarConexion(nombre, ip, puerto, id, tiempo);
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

	public Vector<String> getConexion(String nombreConexion) {
		Map<String, Vector<String>> mapeo = almacenamiento.getConexiones();
		return mapeo.get(nombreConexion);
		
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id, String tiempo) {
		almacenamiento.editarConexion(nombreViejo, nombreConexion, ip, puerto, id, tiempo);
	}

}
