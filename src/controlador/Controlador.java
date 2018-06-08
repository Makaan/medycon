package controlador;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import almacenamiento.AlmacenamientoConexiones;
import almacenamiento.EscritorExcel;
import conexion.AdminConexiones;
import conexion.Conexion;
import controlador.AdminInfo;
import controlador.AdminMensajes;
import controlador.AdminTabla;
import interfaz.Interfaz;

public class Controlador implements Runnable {
	
	private final int RETARDO = 1000; //Retardo entre peticiones al servidor en milisegundos
	private Interfaz interfaz;
	
	//private static AdminTabla adminTabla = AdminTabla.getInstancia();

	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	private AdminConexiones adminConexiones = AdminConexiones.getInstancia();
	
	private AdminTabla adminTabla;
	private AdminInfo adminInfo;
	private AdminGrafico adminGrafico;
	private Conexion conexion;
	private String nombreConexion;
	
	public Controlador(Interfaz interfaz, String nombreConexion) {
		this.interfaz = interfaz;
		
		adminTabla = new AdminTabla(interfaz, nombreConexion);
		adminInfo = new AdminInfo(interfaz);
		adminGrafico = new AdminGrafico(interfaz);
		
	}

	@Override
	public void run() {
		while(true) {
			System.out.println(conexion != null);
			if(conexion != null) {
				System.out.println(conexion != null);
				try {
					String resu = conexion.consultarEstado();
					System.out.println(resu);
					if(!resu.equals("")) {
						adminTabla.agregarFila(resu);
						adminInfo.actualizarDatos(resu);
					}
					Thread.sleep(RETARDO);
		 		}
				catch (InterruptedException e) {
					adminMensajes.mostrarMensajeError("Error en el hilo de ejecucion");
					e.printStackTrace();
				}catch (IOException e) {
					adminMensajes.mostrarMensajeError("Error de entrada/salida");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cerrarConexion() {
		if(conexion != null) {
			try {
				conexion.cerrarConexion();
			} catch (IOException e) {
				adminMensajes.mostrarMensajeError("Error al cerrar la conexion");
				e.printStackTrace();
			}
			conexion = null;
		}
		
	}
	
	public void nuevaConexion(String nombreConexion) {
		this.nombreConexion = nombreConexion;
		try {
			if (conexion != null) {
				conexion.cerrarConexion();
			}
			Vector<String> datosConexion = adminConexiones.getConexion(nombreConexion);
			conexion = new Conexion(datosConexion.get(0), datosConexion.get(1));
			System.out.println("Creada conexion "+(conexion != null));
			adminTabla = new AdminTabla(interfaz, nombreConexion);
		}
		catch (IOException e) {
			adminMensajes.mostrarMensajeError("Error al crear/cerrar la conexion");
			e.printStackTrace();
		}
	}

	public String[] getNombresColumnas() {
		return AdminTabla.getNombresColumnas();
	}

	public void exportar() {
		EscritorExcel.exportar(AdminTabla.getNombresColumnas(), adminTabla.getDatosTabla(), nombreConexion);
		
	}

	public void graficarSemana() {
		adminGrafico.graficarSemana(adminTabla.getDatosTabla());
		
	}

	public void graficarDia() {
		adminGrafico.graficarDia(adminTabla.getDatosTabla());
		
	}

	public void graficarHora() {
		if(adminTabla != null) 
			adminGrafico.graficarHora(adminTabla.getDatosTabla());
		
	}

	public void borrarLista() {
		adminTabla.borrarLista();
		
	}

}
