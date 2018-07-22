package controlador;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import conexion.AdminConexiones;

public class AlarmaNivel {
	
	private static AdminConexiones adminConexiones = AdminConexiones.getInstancia();
	private static AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	
	private Map<String, Integer> alarmasMax;
	private Map<String, Integer> alarmasMin;
	
	private Clip clipAlertaMin;
	private Clip clipAlertaMax;
	
	public AlarmaNivel() {
		alarmasMax = new HashMap<String, Integer>();
		alarmasMin = new HashMap<String, Integer>();
		
		
		
		for(String nombreConexion: adminConexiones.getNombreConexiones()) {
			Map<String, String> datos = adminConexiones.getConexion(nombreConexion);
			Integer alarmaMin = Integer.parseInt(datos.get("alarmaMin"));
			Integer alarmaMax = Integer.parseInt(datos.get("alarmaMax"));
			if(alarmaMin != -1) {
				alarmasMin.put(nombreConexion, alarmaMin);
			}
			if(alarmaMax != -1) {
				alarmasMax.put(nombreConexion, alarmaMax);
				}		
		}
		try {       
			clipAlertaMin = AudioSystem.getClip();
			clipAlertaMax = AudioSystem.getClip();
			clipAlertaMin.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("res/sound/alarmaMin.wav"))));
			clipAlertaMax.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("res/sound/alarmaMax.wav"))));
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (LineUnavailableException e) {
	    	e.printStackTrace();
	    } 
	}
	
	public void checkAlarma(String nombreConexion, DatosMensaje datosMensaje) {
		System.out.println("checkAlarma");
		
		
		int valorNI = Integer.parseInt(datosMensaje.getValorNI());
		int valorNm = Integer.parseInt(datosMensaje.getValorNm());
		int altura = valorNm - valorNI;
		
		int valorNM = Integer.parseInt(datosMensaje.getValorNM());
		
		double porcentaje = (((double) altura) / (valorNm - valorNM)) * 100;

		
		
		System.out.println("porcentaje "+porcentaje);
		Map<String, String> datos = adminConexiones.getConexion(nombreConexion);
		Integer alarmaMin = Integer.parseInt(datos.get("alarmaMin"));
		Integer alarmaMax = Integer.parseInt(datos.get("alarmaMax"));
		System.out.println("alarmaMin-alarmaMax "+alarmaMin+" "+alarmaMax);
		if(alarmaMin != -1 && alarmaMin > porcentaje) {
			clipAlertaMin.start();
			clipAlertaMin.setFramePosition(0);
			adminMensajes.mostrarMensajeInfo("El nivel porcentual del dispositivo "+nombreConexion+" esta por debajo de "+alarmaMin+" %");
		}
		if(alarmaMax != -1 && alarmaMax < porcentaje) {
			clipAlertaMax.start();
			clipAlertaMax.setFramePosition(0);
			adminMensajes.mostrarMensajeInfo("El nivel porcentual del dispositivo "+nombreConexion+" esta por encima de "+alarmaMax	+" %");
		}
	}
	
	public void actualizarAlarma(String nombreConexion, Integer min, Integer max) {
		if(min < -1 || max < -1) {
			adminMensajes.mostrarMensajeError("Los valores de las alarmas no pueden ser negativas");
		}
		alarmasMin.put(nombreConexion, min);
		alarmasMax.put(nombreConexion, max);
		
	}
	
	
	
}
