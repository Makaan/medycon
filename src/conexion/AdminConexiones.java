package conexion;

import java.util.Vector;

public class AdminConexiones implements Runnable {
	
	private final int RETARDO = 1000; //Retardo entre peticiones al servidor en milisegundos
	
	private Conexion conexionActual = null;
	
	private static AdminTabla adminTabla = AdminTabla.getInstancia();
	private static AlmacenamientoConexiones almacenamientoConexiones = AlmacenamientoConexiones.getInstancia();
	
	public AdminConexiones() {
	}

	@Override
	public void run() {
		while(true) {
			 if(conexionActual != null) {
				 conexionActual.consultarEstado();
			 }
			 Thread.sleep(RETARDO);
		}
		
	}
	
	public void nuevaConexion(String nombreConexion) {
		if (conexionActual != null) {
			conexionActual.cerrarConexion();
		}
		Vector<String> datosConexion = adminConexiones.getConexion(nombreConexion);
		conexionActual = new Conexion(datosConexion.get(0), datosConexion.get(1));
	}

}
