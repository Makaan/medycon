package controlador;

import java.util.HashMap;
import java.util.Map;

import interfaz.InfoGUI;

public class AdminInfo {
	
	private InfoGUI interfaz;
	private Map<String, DatosMensaje> ultimosDatos;
	
	public AdminInfo(InfoGUI interfaz) {
		this.interfaz = interfaz;
		ultimosDatos = new HashMap<String, DatosMensaje>();
	}
	
	public void actualizarDatos(String nombreConexion, DatosMensaje datos) {
		ultimosDatos.put(nombreConexion, datos);
	}

	public void mostrarDatos(String nombreConexion) {
		DatosMensaje datos = ultimosDatos.get(nombreConexion);
		if(datos != null) {
			Map<String, String> mapeoDatos = new HashMap<String, String>();
			
			mapeoDatos.put("NivelAbsolutoMax" , datos.getValorNM());
			mapeoDatos.put("NivelAbsolutoMin", datos.getValorNm());
			
			mapeoDatos.put("Rele1Max", datos.getValor1M());
			mapeoDatos.put("Rele1Min", datos.getValor1m());
			
			mapeoDatos.put("Rele2Max", datos.getValor2M());
			mapeoDatos.put("Rele2Min", datos.getValor2m());
			
			mapeoDatos.put("Rele1", datos.getValorE1());
			mapeoDatos.put("Rele2", datos.getValorE2());
			
			int distancia = Integer.parseInt(datos.getValorNI());
			int nivelMedio = Integer.parseInt(datos.getValorNm());
			int altura = nivelMedio - distancia;
			
			mapeoDatos.put("Altura", Integer.toString(altura));
			
			int NM = Integer.parseInt(datos.getValorNM());
			
			double porcentaje = (((double) altura) / (nivelMedio - NM)) * 100;
			String sPorcentaje = String.format("%.00f", porcentaje);
			
			mapeoDatos.put("Porcentaje", sPorcentaje);
			
			interfaz.actualizarInfo(mapeoDatos);
		}
		

	}
}
