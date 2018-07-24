package controlador;

import java.util.Timer;
import java.util.TimerTask;

public class TimerConexion {
	
	private Timer timer;
	private String nombre;
	
	public TimerConexion(Controlador con, String nm, int tiempo) {
		this.nombre = nm;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				con.consultarEstado(nombre);
			}
			
		}, 0, tiempo);
	}
	
	public void cancel() {
		timer.cancel();
		timer.purge();
	}

}
