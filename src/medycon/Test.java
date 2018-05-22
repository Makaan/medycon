package medycon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import almacenamiento.AlmacenamientoArchivo;
import almacenamiento.AlmacenamientoConexiones;
import almacenamiento.AlmacenamientoDatosConexion;

public class Test {
	
	public static void main(String[] args) {
		AlmacenamientoDatosConexion ac = AlmacenamientoDatosConexion.getInstancia();
		
		ac.guardarDato("<asd>");
		ac.guardarDato("<asd2>");
		ac.guardarDato("<asd3>");
		
		Map<Date,String> map = ac.getDatos();
		System.out.println(map.size());
		for(Entry<Date, String> entry : map.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
    
	}
	    
}
