package controlador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import almacenamiento.AlmacenamientoArchivo;
import interfaz.TablaGUI;

public class AdminTabla {
	
	
	private String identificador;
	public static final SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
	
	private AlmacenamientoArchivo almacenamientoArchivo;
	private TablaGUI<String> interfaz = null;
	private static final String[] columnas = {
    		"FECHA",
            "PORCENTAJE",
            "ALTURA",
            "BOMBA 1",
            "BOMBA 2"};
	
	public AdminTabla(TablaGUI<String> interfaz, String identificador) {
		this.interfaz = interfaz; 
		almacenamientoArchivo = new AlmacenamientoArchivo(identificador+"Tabla");
		actualizarDatosTabla();
		
		this.identificador = identificador;
		
	}
	
	public void actualizarDatosTabla() {
		List<String> entradas = almacenamientoArchivo.getEntradas();
		String[][] filasPrevias = new String[entradas.size()][columnas.length];
		int i = 0;
		for(String entrada: entradas) {
			String[] tokens = entrada.split(",");
			for(int j = 0; j < tokens.length; j++ ) {
				filasPrevias[i][j] = tokens[j];
			}
			i++;
		}
		interfaz.actualizarDatosTabla(filasPrevias);	
		
	}

	public static String[] getNombresColumnas() {
		return columnas;
	}
	
	public void agregarFila(String dato) {
		Date fecha = Calendar.getInstance().getTime();
		String sFecha = formateador.format(fecha);
		
		String[] tokens = dato.substring(1, dato.length()-2).split(",");
		
		String[] filaArr = new String[columnas.length];
		filaArr[0] = sFecha;
		
		//Le saco espacios si hay
		String distanciaRaw = tokens[5].substring(2).replaceAll("\\s", "");
		int distancia = Integer.parseInt(distanciaRaw);
		
		
		String nivelMedioRaw = tokens[7].substring(2).replaceAll("\\s", "");
		int nivelMedio = Integer.parseInt(nivelMedioRaw);
		int altura = nivelMedio - distancia;
		
		filaArr[2] = Integer.toString(altura);
		
		//Le saco espacios si hay
		String NMRaw = tokens[8].substring(2).replaceAll("\\s", "");
		int NM = Integer.parseInt(NMRaw);
		
		double porcentaje = ((double)altura / (nivelMedio - NM) *100);
		String sPorcentaje = String.format("%.00f", porcentaje);
		filaArr[1] = sPorcentaje;
		
		filaArr[3] = onOff(tokens[1].substring(2));
		
		filaArr[4] = onOff(tokens[3].substring(2));
		
		String fila = String.join(",", filaArr);
		
		almacenamientoArchivo.guardarEntrada(fila);
		
		actualizarDatosTabla();
	}
	
	private String onOff(String estado) {
		if(estado.equals("0"))
			return "off";
		return "on";
	}
	
	public List<String> getDatosTabla() {
		return almacenamientoArchivo.getEntradas();
	}

	public void borrarLista() {
		almacenamientoArchivo.borrarEntradas();
		interfaz.actualizarDatosTabla(new String[0][0]);
		
	}

	public void renombrar(String nombreConexion) {
		almacenamientoArchivo.renombrarArchivo(nombreConexion);
		
	}
	
}
