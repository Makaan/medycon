package controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import almacenamiento.AlmacenamientoConexiones;
import almacenamiento.EscritorExcel;
import conexion.AdminConexiones;
import conexion.Conexion;
import controlador.AdminInfo;
import controlador.AdminMensajes;
import controlador.AdminTabla;
import interfaz.Interfaz;

public class Controlador {
	
	private Interfaz interfaz;
	
	//private static AdminTabla adminTabla = AdminTabla.getInstancia();

	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	private AdminConexiones adminConexiones = AdminConexiones.getInstancia();
	
	
	private AdminInfo adminInfo;
	private AdminGrafico adminGrafico;
	
	private Map<String, AdminTabla> mapAdminTablas;
	private Map<String, Conexion> conexiones;
	private Map<String, Timer> timers;
	private String conexionSeleccionada;
	
	public Controlador(Interfaz interfaz, String conexionSeleccionada) {
		this.interfaz = interfaz;
		
		mapAdminTablas = new HashMap<String, AdminTabla>();
		conexiones = new HashMap<String, Conexion>();
		timers = new HashMap<String, Timer>();
		
		this.conexionSeleccionada = conexionSeleccionada;
		for(String nombreConexion: adminConexiones.getNombreConexiones()) {
			mapAdminTablas.put(nombreConexion, new AdminTabla(interfaz, nombreConexion));
		};
		
		
		adminInfo = new AdminInfo(interfaz);
		adminGrafico = new AdminGrafico(interfaz);
	}
	
	public void cerrarConexion(String nombre) {
		if(conexiones.containsKey(nombre)) {
			timers.get(nombre).cancel();
			timers.remove(nombre);
			Conexion conexion = conexiones.get(nombre);
			conexion.cerrarConexion();
			conexiones.remove(nombre);
			
		}
		
	}
	
	public void cerrarConexiones() {
		for(Entry<String, Conexion> conexion: conexiones.entrySet()) {
			conexion.getValue().cerrarConexion();
			conexiones.remove(conexion.getKey());
			timers.get(conexion.getKey()).cancel();
			timers.remove(conexion.getKey());
		}
		
	}
	
	public void nuevaConexion(String nombreConexion) {
		Vector<String> datosConexion = adminConexiones.getConexion(nombreConexion);
		if(conexiones.containsKey(nombreConexion)) {
			cerrarConexion(nombreConexion);
			timers.get(nombreConexion).cancel();
		}
		Conexion conexion = new Conexion(datosConexion.get(0), datosConexion.get(1), datosConexion.get(2), datosConexion.get(3));
		conexiones.put(nombreConexion, conexion);
		
		AdminTabla adminTabla = new AdminTabla(interfaz, nombreConexion);
		mapAdminTablas.put(nombreConexion, adminTabla);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				String resu = conexion.consultarEstado();
				if(!resu.equals("")) {
					adminTabla.agregarFila(resu);
					if(nombreConexion.equals(conexionSeleccionada)) {
						System.out.println(conexionSeleccionada+" "+nombreConexion);
						adminInfo.actualizarDatos(resu);
					}
					
				}
				
			}
			
		}, 0, conexion.getTiempo());
		System.out.println(timer);
		timers.put(nombreConexion, timer);
		
	}

	public String[] getNombresColumnas() {
		return AdminTabla.getNombresColumnas();
	}

	public void exportar(String nombreConexion, String nombreArchivo) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			EscritorExcel.exportar(AdminTabla.getNombresColumnas(), mapAdminTablas.get(nombreConexion).getDatosTabla(), nombreArchivo);
	}

	public void graficarSemana(String nombreConexion) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			adminGrafico.graficarSemana(nombreConexion, mapAdminTablas.get(nombreConexion).getDatosTabla());
	}

	public void graficarDia(String nombreConexion) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			adminGrafico.graficarDia(nombreConexion, mapAdminTablas.get(nombreConexion).getDatosTabla());
	}

	public void graficarHora(String nombreConexion) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			adminGrafico.graficarHora(nombreConexion, mapAdminTablas.get(nombreConexion).getDatosTabla());
	}

	public void borrarLista(String nombreConexion) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			mapAdminTablas.get(nombreConexion).borrarLista();
	}
	

	public void modificarVelocidadMuestreo(String velocidadStr, String nombreConexion) {
		int velocidad = 0;
		try {
			velocidad = Integer.parseInt(velocidadStr);		
		}
		catch(NumberFormatException e) {
			adminMensajes.mostrarMensajeError("El valor ingresado no es un numero entero valido");
		}
		if(velocidad <= 0) {
			adminMensajes.mostrarMensajeError("El valor ingresado no puede ser menor a cero");
		}
		else {
			Vector<String> datosConexion = adminConexiones.getConexion(nombreConexion);
			System.out.println(velocidadStr);
			adminConexiones.editarConexion(nombreConexion, nombreConexion, datosConexion.get(0), datosConexion.get(1), datosConexion.get(2), velocidadStr);
			if(conexiones.containsKey(nombreConexion)) {
				conexiones.get(nombreConexion).setTiempo(velocidad);
				timers.get(nombreConexion).scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						String resu = conexiones.get(nombreConexion).consultarEstado();
						if(!resu.equals("")) {
							mapAdminTablas.get(nombreConexion).agregarFila(resu);
							if(nombreConexion.equals(conexionSeleccionada)) {
								adminInfo.actualizarDatos(resu);
							}
							
						}
					}
				}, 0, velocidad);
			}
			
		}
		
	}

	public void conexionSeleccionada(String nombreConexion) {
		System.out.println("ConexionSeleccionada "+nombreConexion);
		conexionSeleccionada = nombreConexion;
		if(mapAdminTablas.containsKey(nombreConexion)) {
			mapAdminTablas.get(nombreConexion).actualizarDatosTabla();
		}
		graficarHora(nombreConexion);
		
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id) {
		
		if(mapAdminTablas.containsKey(nombreViejo)) {
			AdminTabla admin = mapAdminTablas.get(nombreViejo);
			mapAdminTablas.remove(nombreViejo);
			mapAdminTablas.put(nombreConexion, admin);
			admin.renombrar(nombreConexion);
		}
		
		
		adminConexiones.editarConexion(nombreViejo, nombreConexion, ip, puerto, id, adminConexiones.getConexion(nombreViejo).get(3));
		if(conexiones.get(nombreConexion)!= null)
			nuevaConexion(nombreConexion);
		
	}

	public boolean estaConectado(String nombre) {
		
		return conexiones.containsKey(nombre);
	}

	

}
