package almacenamiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import controlador.AdminMensajes;

public class AlmacenamientoArchivo {
	
	private String nombreArchivo;
	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	
	public AlmacenamientoArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
		File archivo = new File(nombreArchivo);
		
        try {
        	//Si el archivo no existe lo crea.
        	if(!archivo.exists()) {
    			archivo.createNewFile();
    		}
        	
        }  
        catch(IOException ex) {
        	adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
        }
	}
	
	public void guardarEntrada(String entrada) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivo, true)));
			writer.println(entrada);
			writer.close();
		}
		catch(IOException ex) {
			adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
	    }
		
	}
	
	public List<String> getEntradas() {
		ArrayList<String> conexiones = new ArrayList<String>();
		try {
        	
            //Creo el lector para leer del archivo.
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            
            String linea = lector.readLine();
            while(linea != null) {
            	conexiones.add(linea);
                linea = lector.readLine();
            }   
            //Cierro el archivo
            lector.close();         
        }
        
        catch(FileNotFoundException ex) {
        	adminMensajes.mostrarMensajeError("No se pudo abrir el archivo'" + nombreArchivo + "'");                
        }
        catch(IOException ex) {
        	adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
        }
		return conexiones;
	}

	public void borrarEntradas() {
		File archivo = new File(nombreArchivo);
		try {
			if(archivo.exists()) {
				PrintWriter writer = new PrintWriter(archivo);
				writer.print("");
				writer.close();
			}
		}
		catch(IOException ex) {
			adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
	    }
		
		
	}

}
