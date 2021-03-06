package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
	private AlarmaNivel alarmaNivel;
	
	private Map<String, AdminTabla> mapAdminTablas;
	private Map<String, Conexion> conexiones;
	private Map<String, TimerConexion> timers;
	private String conexionSeleccionada;
	
	public Controlador(Interfaz interfaz, String conexionSeleccionada) {
		this.interfaz = interfaz;
		
		mapAdminTablas = new HashMap<String, AdminTabla>();
		conexiones = new HashMap<String, Conexion>();
		timers = new HashMap<String, TimerConexion>();
		alarmaNivel = new AlarmaNivel();
		
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
		System.out.println("nueva conexion "+nombreConexion);
		if(conexiones.containsKey(nombreConexion)) {
			cerrarConexion(nombreConexion);
			timers.get(nombreConexion).cancel();
		}
		
		AdminTabla adminTabla = new AdminTabla(interfaz, nombreConexion);
		mapAdminTablas.put(nombreConexion, adminTabla);
		
		Map<String, String> datosConexion = adminConexiones.getConexion(nombreConexion);
		Conexion conexion = buscarConexionConIp(datosConexion.get("ip"));
		if(conexion == null) {
			try {
				conexion = new Conexion(nombreConexion, datosConexion.get("ip"), datosConexion.get("puerto"), datosConexion.get("id"), datosConexion.get("tiempo"));
			}
			catch (IOException e) {
				adminMensajes.mostrarMensajeError("Error al conectarse con el dispositivo: "+nombreConexion);
				if(conexiones.containsKey(nombreConexion)) {
					conexiones.remove(nombreConexion);
					
				}
				if(timers.containsKey(nombreConexion)) {
					timers.get(nombreConexion).cancel();
					timers.remove(nombreConexion);
				}
				e.printStackTrace();
			}
			
		}
		else {
			conexion.addId(nombreConexion, datosConexion.get("id"));
		}
		conexiones.put(nombreConexion, conexion);
		Integer tiempo = conexiones.get(nombreConexion).getTiempo();
		TimerConexion timerConexion = new TimerConexion(this, nombreConexion, tiempo);
		timers.put(nombreConexion, timerConexion);
		
		
		
	}

	

	public String[] getNombresColumnas() {
		return AdminTabla.getNombresColumnas();
	}

	public void exportar(String nombreConexion, String nombreArchivo) {
		if(mapAdminTablas.get(nombreConexion)!= null) {
			
			String[] nombres = AdminTabla.getNombresColumnas();
			/*
			String[] nombresNuevos = new String[nombres.length+1];
			nombresNuevos[0]= nombres[0];
			nombresNuevos[1]= "HORA";
			for(int i = 1; i < nombres.length; i++) {
				nombresNuevos[i+1]= nombres[i];
			}
			*/
			
			List<String> listaDatos = mapAdminTablas.get(nombreConexion).getDatosTabla();
			
			/*List<String> listaNueva = new LinkedList<String>();
			
			//SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm:ss"); 
			//SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MMM,yyyy,HH:mm:ss"); 
			
			for(String dato: listaDatos) {
				String tokens[] = dato.split(",");
				String resu = "";
				try {
					Date fecha = AdminTabla.formateador.parse(tokens[0]);
					resu+= formateadorFecha.format(fecha)+",";
					//resu+= formateadorHora.format(fecha)+",";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 1; i < tokens.length; i++) {
					resu+=tokens[i]+",";
				}
				listaNueva.add(resu);
			}
			*/
			EscritorExcel.exportar(nombres, listaDatos, nombreArchivo);
		}
			
	}
	
	public void graficarMes(String nombreConexion) {
		if(mapAdminTablas.get(nombreConexion)!= null)
			adminGrafico.graficarMes(nombreConexion, mapAdminTablas.get(nombreConexion).getDatosTabla());
		
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
			Map<String, String> datosConexion = adminConexiones.getConexion(nombreConexion);
			adminConexiones.editarConexion(nombreConexion, nombreConexion, datosConexion.get("ip"), datosConexion.get("puerto"), datosConexion.get("id"), velocidadStr, datosConexion.get("alarmaMin"), datosConexion.get("alarmaMax"));
			if(conexiones.containsKey(nombreConexion)) {
				TimerConexion timer = timers.get(nombreConexion);
				timer.cancel();
				timers.remove(nombreConexion);
				
				conexiones.get(nombreConexion).setTiempo(velocidad);
				timer = new TimerConexion(this, nombreConexion, velocidad);
				timers.put(nombreConexion, timer);
			}
			
		}
		
	}

	public void conexionSeleccionada(String nombreConexion) {
		conexionSeleccionada = nombreConexion;
		if(mapAdminTablas.containsKey(nombreConexion)) {
			mapAdminTablas.get(nombreConexion).actualizarDatosTabla();
		}
		adminInfo.mostrarDatos(nombreConexion);
		graficarHora(nombreConexion);
		
	}
	
	public void editarConexion(String nombreViejo, String nombreConexion, String ip, String puerto, String id) {
		
		if(mapAdminTablas.containsKey(nombreViejo)) {
			AdminTabla admin = mapAdminTablas.get(nombreViejo);
			mapAdminTablas.remove(nombreViejo);
			mapAdminTablas.put(nombreConexion, admin);
			admin.renombrar(nombreConexion);
		}
		
		Map<String, String> map = adminConexiones.getConexion(nombreViejo);
		adminConexiones.editarConexion(nombreViejo, nombreConexion, ip, puerto, id, map.get("tiempo"), map.get("alarmaMin"), map.get("alarmaMax"));
		if(conexiones.get(nombreConexion)!= null)
			nuevaConexion(nombreConexion);
		
	}
	
	public void eliminarConexion(String nombreConexion) {
		if(conexiones.containsKey(nombreConexion)) {
			timers.get(nombreConexion).cancel();
			conexiones.get(nombreConexion).cerrarConexion();
			conexiones.remove(nombreConexion);
		}
		if(mapAdminTablas.containsKey(nombreConexion)) {
			mapAdminTablas.get(nombreConexion).borrarLista();
			mapAdminTablas.remove(nombreConexion);
		}
		
		adminConexiones.eliminarConexion(nombreConexion);
		interfaz.eliminarConexion(nombreConexion);
		
	}

	public boolean estaConectado(String nombre) {
		
		return conexiones.containsKey(nombre);
	}

	public void configurarAlarma(String nombreConexion, String alarmaMin, String alarmaMax) {
		Integer min = 0;
		Integer max = 0;
		if(alarmaMin.equals("")) 
			alarmaMin = "-1";
		if(alarmaMax.equals(""))
			alarmaMax = "-1";
		
		try {
			min = Integer.parseInt(alarmaMin);
			max = Integer.parseInt(alarmaMax);
		}
		catch(NumberFormatException e) {
			adminMensajes.mostrarMensajeError("El valor ingresado no es un numero entero valido");
		}
		if(min > 105 || max > 105) {
			adminMensajes.mostrarMensajeError("El valor ingresado no puede exceder el 105%");
		}
		if(min != -1 && max!=-1) {
			if( min < 0 || max < 0) {
				adminMensajes.mostrarMensajeError("El valor ingresado no puede ser menor a 0%");
			}
		}
		
		Map<String, String> map = adminConexiones.getConexion(nombreConexion);
		adminConexiones.editarConexion(nombreConexion, nombreConexion, map.get("ip"), map.get("puerto"), map.get("id"), map.get("tiempo"), alarmaMin, alarmaMax);
		alarmaNivel.actualizarAlarma(nombreConexion, min, max);
		
		
	}

	public synchronized void  consultarEstado(String nombre) {
		System.out.println("---");
		System.out.println("consulta "+nombre);
		Conexion conexion = conexiones.get(nombre);
		
		String resu = conexion.consultarEstado(nombre);
		if(!resu.equals("") && resu.charAt(0) == '<') {
			DatosMensaje datos = new DatosMensaje(resu);
			String nombreReal = conexion.idToNombre(datos.getMiID());
			System.out.println(conexion.toString());
			System.out.println(resu+" "+nombreReal);
			System.out.println("---");
			mapAdminTablas.get(nombreReal).agregarFila(datos);
			alarmaNivel.checkAlarma(nombreReal, datos);
			adminInfo.actualizarDatos(nombreReal, datos);
			if(nombreReal.equals(conexionSeleccionada)) {
				adminInfo.mostrarDatos(nombreReal);
			}
		}
		
	}
	
	private Conexion buscarConexionConIp(String ip) {
		Conexion encontre = null;
		for(Conexion con: conexiones.values()) {
			if(con.getIp().equals(ip)) {
				encontre = con;
				break;
			}
		}
		return encontre;
	}

	

	

}
