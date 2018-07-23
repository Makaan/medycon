package controlador;

import java.util.HashMap;
import java.util.Map;

public class DatosMensaje {
	
	private String miID;
	private String valorE1;
	private String valorE2;
	private String valorNI;
	private String valorNM;
	private String valorNm;
	private String valor1m;
	private String valor1M;
	private String valor2m;
	private String valor2M;
	
	public DatosMensaje(String msg) {
		String[] parametros = msg.split(",");
		
        for(int i=0; i<parametros.length; i++) {
            miID = parametros[0].replaceAll("<", "");

            if(parametros[i].contains("E1"))
            {
                valorE1 = parametros[i].replaceAll("E1", "");
            }
            else if(parametros[i].contains("E2"))
            {
                valorE2 = parametros[i].replaceAll("E2", "");
            }

            else if(parametros[i].contains("NI"))
            {
                valorNI = parametros[i].replaceAll("NI", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("NM"))
            {
                valorNM = parametros[i].replaceAll("NM", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("Nm"))
            {
                valorNm = parametros[i].replaceAll("Nm", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("1m"))
            {
            	valor1m = parametros[i].replaceAll("1m", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("1M"))
            {
            	valor1M = parametros[i].replaceAll("1M", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("2m"))
            {
            	valor2m = parametros[i].replaceAll("2m", "").replaceAll(" ", "");
            }
            else if(parametros[i].contains("2M"))
            {
            	valor2M = parametros[i].replaceAll("2M", "").replaceAll(" ", "").replaceAll(">", "");
            }
        }
	}

	public String getValor1m() {
		return valor1m;
	}

	public String getValor1M() {
		return valor1M;
	}

	public String getValor2m() {
		return valor2m;
	}

	public String getValor2M() {
		return valor2M;
	}

	public String getMiID() {
		return miID;
	}

	public String getValorE1() {
		return valorE1;
	}

	public String getValorE2() {
		return valorE2;
	}

	public String getValorNI() {
		return valorNI;
	}

	public String getValorNM() {
		return valorNM;
	}

	public String getValorNm() {
		return valorNm;
	}
}
