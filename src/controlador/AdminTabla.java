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
	
	public void agregarFila(DatosMensaje datos) {
		
		Date fecha = Calendar.getInstance().getTime();
		String sFecha = formateador.format(fecha);
		
		
		String[] filaArr = new String[columnas.length];
		filaArr[0] = sFecha;
		
		//Le saco espacios si hay
		String valorNI= datos.getValorNI();
		int distancia = Integer.parseInt(valorNI);
		
		
		String valorNm = datos.getValorNm();
		int nivelMedio = Integer.parseInt(valorNm);
		int altura = nivelMedio - distancia;
		
		filaArr[2] = Integer.toString(altura);
		
		//Le saco espacios si hay
		String valorNM = datos.getValorNM();
		int NM = Integer.parseInt(valorNM);
		
		double porcentaje = ((double)altura / (nivelMedio - NM) *100);
		String sPorcentaje = String.format("%.00f", porcentaje);
		filaArr[1] = sPorcentaje;
		
		filaArr[3] = onOff(datos.getValorE1());
		
		filaArr[4] = onOff(datos.getValorE2());
		
		String fila = String.join(",", filaArr);
		
		almacenamientoArchivo.guardarEntrada(fila);
		
		actualizarDatosTabla();
	}
	
	private String onOff(String estado) {
		if(estado.equals("0"))
			return "OFF";
		return "ON";
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
