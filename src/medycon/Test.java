package medycon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import almacenamiento.AlmacenamientoArchivo;
import almacenamiento.AlmacenamientoConexiones;

public class Test {
	
	public static void main(String[] args) {
		AlmacenamientoConexiones ac = AlmacenamientoConexiones.getInstancia();
		
		ac.guardarConexion("test", "0.0.0.0", "1");
		ac.guardarConexion("tes2", "0.1.0.1", "2");
		ac.guardarConexion("test", "0.0.0.0", "1");
		
		for(Vector<String> vector : ac.getConexiones().values()) {
			System.out.println(vector.get(0));
			System.out.println(vector.get(1));
			System.out.println();
		}
    
	}
	    
}
