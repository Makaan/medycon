package controlador;

import interfaz.MensajesGUI;

public class AdminMensajes {
	
	private static AdminMensajes instancia = null;
	private MensajesGUI interfaz;
	
	private AdminMensajes() {
	}
	
	public static AdminMensajes getInstancia() {
		if (instancia == null) {
			instancia = new AdminMensajes();
		}
		return instancia;
	}
	
	public void setInterfaz(MensajesGUI interfaz) {
		this.interfaz = interfaz; 
	}
	
	public void mostrarMensajeError(String msg) {
		if(interfaz != null) {
			interfaz.mostrarMensajeError(msg);
		}
	}
	
	public void mostrarMensajeInfo(String msg) {
		if(interfaz != null) {
			interfaz.mostrarMensajeInfo(msg);
		}
	}
}
