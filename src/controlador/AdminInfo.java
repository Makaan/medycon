package controlador;

import java.util.HashMap;
import java.util.Map;

import interfaz.InfoGUI;

public class AdminInfo {
	
	private InfoGUI interfaz;
	
	public AdminInfo(InfoGUI interfaz) {
		this.interfaz = interfaz;
	}

	public void actualizarDatos(String rawData) {
		String[] tokens = rawData.substring(1, rawData.length()-2).split(",");
		Map<String, String> mapeoDatos = new HashMap<String, String>();
		
		mapeoDatos.put("NivelAbsolutoMax" , tokens[8].substring(2));
		mapeoDatos.put("NivelAbsolutoMin", tokens[7].substring(2));
		
		mapeoDatos.put("Rele1Max", tokens[10].substring(2));
		mapeoDatos.put("Rele1Min", tokens[9].substring(2));
		
		mapeoDatos.put("Rele2Max", tokens[12].substring(2));
		mapeoDatos.put("Rele2Min", tokens[11].substring(2));
		
		mapeoDatos.put("Rele1", tokens[1].substring(2));
		mapeoDatos.put("Rele2", tokens[3].substring(2));
		
		String distanciaRaw = tokens[5].substring(2).replaceAll("\\s", "");
		int distancia = Integer.parseInt(distanciaRaw);
		
		String nivelMedioRaw = tokens[7].substring(2).replaceAll("\\s", "");
		int nivelMedio = Integer.parseInt(nivelMedioRaw);
		int altura = nivelMedio - distancia;
		
		String NMs = tokens[8].substring(2).replaceAll("\\s", "");
		int NM = Integer.parseInt(NMs);
		
		double porcentaje = (((double) altura) / (nivelMedio - NM)) * 100;
		String sPorcentaje = String.format("%.00f", porcentaje);
		
		mapeoDatos.put("porcentaje", sPorcentaje);
		
		interfaz.actualizarInfo(mapeoDatos);

	}
}
