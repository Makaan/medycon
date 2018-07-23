package almacenamiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import controlador.AdminMensajes;

public class AlmacenamientoArchivo {
	
	private final String dir = System.getProperty("user.home") + File.separator + "Medycon"+ File.separator;
	
	private String nombreArchivo;
	private File archivo;
	private AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	private String newLine = System.getProperty("line.separator");
	
	public AlmacenamientoArchivo(String nombreArchivo) {
		this.nombreArchivo = dir + nombreArchivo;
		archivo = new File(this.nombreArchivo);
		
        try {
        	archivo.getParentFile().mkdirs();
        	//Si el archivo no existe lo crea.
    		archivo.createNewFile();
        	
        }  
        catch(IOException ex) {
        	adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
        }
	}
	
	public void guardarEntrada(String entrada) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivo, true)));
			writer.print(entrada+newLine);
			writer.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
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
        	ex.printStackTrace();
        	adminMensajes.mostrarMensajeError("No se pudo abrir el archivo'" + nombreArchivo + "'");                
        }
        catch(IOException ex) {
        	ex.printStackTrace();
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
			ex.printStackTrace();
			adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
	    }
		
		
	}

	public void renombrarArchivo(String nuevo) {
		File archivoNuevo = new File(nuevo);
		
        try {
        	//Si el archivo no existe lo crea.
        	if(!archivoNuevo.exists()) {
        		archivoNuevo.createNewFile();
    		}
        	archivo.renameTo(archivoNuevo);
        	nombreArchivo = nuevo;
        	
        }  
        catch(IOException ex) {
        	ex.printStackTrace();
        	adminMensajes.mostrarMensajeError( "Error al leer el archivo '" + nombreArchivo + "'");                  
        }
        
		
	}

}
