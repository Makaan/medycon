package almacenamiento;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controlador.AdminMensajes;
import controlador.AdminTabla;

public class EscritorExcel {
	
	private static AdminMensajes adminMensajes = AdminMensajes.getInstancia();
	
	private EscritorExcel() {}
	
	public static void exportar(String[] nombresColumnas, List<String> listaDatos, String nombre) {
		
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Datos");
        Font fuenteCabecera = workbook.createFont();
        fuenteCabecera.setBold(true);
        fuenteCabecera.setFontHeightInPoints((short) 12);

        // Create a CellStyle with the font
        CellStyle estiloCabecera = workbook.createCellStyle();
        estiloCabecera.setFont(fuenteCabecera);
        
        Row cabecera = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < nombresColumnas.length; i++) {
            Cell celda = cabecera.createCell(i);
            celda.setCellValue(nombresColumnas[i]);
            celda.setCellStyle(estiloCabecera);
        }
        
        int numFila = 1;
        for(String dato : listaDatos) {
        	String[] tokens = dato.split(",");
        	Row fila = sheet.createRow(numFila);
        	numFila++;
        	//Especial para la fecha
        	Date fecha = null;
        	try {
				fecha = AdminTabla.formateador.parse(tokens[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	fila.createCell(0).setCellValue(new SimpleDateFormat("dd','MMM','yyyy','HH:mm:ss").format(fecha));
        	for(int i = 1 ; i < tokens.length; i++) {
        		fila.createCell(i).setCellValue(tokens[i]);
        	}
        }
		// Resize all columns to fit the content size
        for(int i = 0; i < nombresColumnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(nombre+".xlsx");
			workbook.write(fileOut);
	        fileOut.close();

	        // Closing the workbook
	        workbook.close();
		} catch (FileNotFoundException e) {
			adminMensajes.mostrarMensajeError("No se pudo guardar en esa ruta");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	adminMensajes.mostrarMensajeInfo("Se exportó exitosamente el archivo");
        }


	}

}
