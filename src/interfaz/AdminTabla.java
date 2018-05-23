package interfaz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import almacenamiento.AlmacenamientoArchivo;

public class AdminTabla {
	
	private static AdminTabla instancia = null;
	
	private final String NOMBRE_ARCHIVO = "datosTabla.txt";
	private final SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy/HH:mm:ss"); 
	
	private AlmacenamientoArchivo almacenamientoArchivo;
	private TablaGUI<String> interfaz = null;
	private String[] columnas = {
    		"FECHA",
            "PORCENTAJE",
            "DISTANCIA",
            "BOMBA 1",
            "BOMBA 2"};
	
	private AdminTabla() {
		almacenamientoArchivo = new AlmacenamientoArchivo(NOMBRE_ARCHIVO);
		
	}
	
	public static AdminTabla getInstancia() {
		if(instancia == null) {
			instancia = new AdminTabla();
		}
		return instancia;
	}
	
	public void setInterfaz(TablaGUI<String> interfaz) {
		this.interfaz = interfaz;
		List<String> entradas = almacenamientoArchivo.getEntradas();
		String[][] filasPrevias = new String[entradas.size()][columnas.length];
		int i = 0;
		for(String entrada: entradas) {
			String[] tokens = entrada.split(",");
			System.out.println(tokens.length);
			for(int j = 0; j < tokens.length; j++ ) {
				System.out.println(i+" "+j+" "+tokens[j]);
				filasPrevias[i][j] = tokens[j];
			}
			i++;
		}
		interfaz.actualizarDatosTabla(filasPrevias);	
	}
	
	public String[] getNombresColumnas() {
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
		String sDistancia = Integer.toString(distancia);
		filaArr[2] = sDistancia;
		
		String nivelMedioRaw = tokens[7].substring(2).replaceAll("\\s", "");
		int nivelMedio = Integer.parseInt(nivelMedioRaw);
		int altura = nivelMedio - distancia;
		
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
		interfaz.aggregarDatoTabla(filaArr);
	}
	
	private String onOff(String estado) {
		if(estado.equals("0"))
			return "off";
		return "on";
	}
	
}
