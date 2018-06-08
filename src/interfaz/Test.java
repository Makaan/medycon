package interfaz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import almacenamiento.AlmacenamientoArchivo;

public class Test {
	
	public static void main(String[] args) {
		final SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
		AlmacenamientoArchivo aa = new AlmacenamientoArchivo("TestTabla");
		int max = 43200;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -max);
		Random rand = new Random();
		for(int i = 0; i < max; i++) {
			String aGuardar = new String();
			cal.add(Calendar.MINUTE, 1);
			aGuardar+= formateador.format(cal.getTime());
			aGuardar+=",0,";
			aGuardar+=Integer.toString(rand.nextInt(100));
			aGuardar+=",off,off";
			aa.guardarEntrada(aGuardar);
		}
	}
}
