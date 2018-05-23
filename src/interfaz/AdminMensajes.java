package interfaz;

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
}
