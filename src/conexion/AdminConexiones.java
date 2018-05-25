package conexion;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import almacenamiento.AlmacenamientoConexiones;
import interfaz.AdminInfo;
import interfaz.AdminMensajes;
import interfaz.AdminTabla;

public class AdminConexiones implements Runnable {
	
	private final int RETARDO = 5000; //Retardo entre peticiones al servidor en milisegundos
	
	private Conexion conexionActual = null;
	
	//private static AdminTabla adminTabla = AdminTabla.getInstancia();
	private static AlmacenamientoConexiones almacenamientoConexiones = AlmacenamientoConexiones.getInstancia();
	private static AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	private static AdminTabla adminTabla = AdminTabla.getInstancia();
	private static AdminInfo adminInfo = AdminInfo.getInstancia();
	
	public AdminConexiones() {
	}

	@Override
	public void run() {
		while(true) {
			 
		 	try {
		 		if(conexionActual != null) {
					System.out.println("Enviando mensaje");
					String resu = conexionActual.consultarEstado();
					System.out.println(resu);
					if(!resu.equals("")) {
						adminTabla.agregarFila(resu);
						adminInfo.actualizarDatos(resu);
					}
					
		 		}
				Thread.sleep(RETARDO);
			} catch (IOException e) {
				adminMensajes.mostrarMensajeError("Error en la conexion");
				e.printStackTrace();
			} catch (InterruptedException e) {
				adminMensajes.mostrarMensajeError("Error en el hilo de ejecucion");
				e.printStackTrace();
			}

			 
		}
		
	}
	
	public void crearConexion(String nombre, String ip, String id) {
		almacenamientoConexiones.guardarConexion(nombre, ip, id);
	}
	
	public void cerrarConexion() {
		try {
			conexionActual.cerrarConexion();
		} catch (IOException e) {
			adminMensajes.mostrarMensajeError("Error al cerrar la conexion");
			e.printStackTrace();
		}
		conexionActual = null;
	}
	
	public void nuevaConexion(String nombreConexion) {
		try {
			if (conexionActual != null) {
				conexionActual.cerrarConexion();
			}
			Vector<String> datosConexion = almacenamientoConexiones.getConexion(nombreConexion);
			conexionActual = new Conexion(datosConexion.get(0), datosConexion.get(1));
		}
		catch (IOException e) {
			adminMensajes.mostrarMensajeError("Error al crear/cerrar la conexion");
			e.printStackTrace();
		}
	}
	
	public String[] getNombreConexiones() {
		Map<String, Vector<String>> mapeo = almacenamientoConexiones.getConexiones();
		String[] conexiones = new String[mapeo.size()];
		int i = 0;
		for(String clave: mapeo.keySet()) {
			conexiones[i] = clave;
			i++;
		}
		return conexiones;
	}

}
