package almacenamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AlmacenamientoDatosConexion {
	
	private final String NOMBRE_ARCHIVO = "datos.txt";
	
	//Formateador para convertir el String a un objeto tipo fecha
	private final SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
	
	private static AlmacenamientoDatosConexion instancia;
	private AlmacenamientoArchivo almacenamientoArchivo;
	
	private AlmacenamientoDatosConexion() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
    }
	
	public static AlmacenamientoDatosConexion getInstancia() {
		if(instancia == null) {
			instancia = new AlmacenamientoDatosConexion();
		}
		return instancia;
	}
	
	public void guardarDato(String dato) {
		Date fecha = Calendar.getInstance().getTime();
		String sFecha = formateador.format(fecha);
		almacenamientoArchivo.guardarEntrada(sFecha+"&"+dato);
	}
	
	public Map<Date, String> getDatos() {
		HashMap<Date, String> mapeoDatos = new HashMap<Date, String>();
		List<String> listaDatos = almacenamientoArchivo.getEntradas();

		
		
		for(String dato : listaDatos) {
			String[] tokens = dato.split("&");
			Date fecha = null;
			try {
				fecha = formateador.parse(tokens[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapeoDatos.put(fecha, tokens[1]);
		}
		return mapeoDatos;
	}
}
