package almacenamiento;

import interfaz.AdminTabla;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EscritorExcel {
	
	private static AdminTabla adminTabla = AdminTabla.getInstancia();
	
	private EscritorExcel() {}
	
	public static void exportar() {
		
		String[] nombresColumnas = adminTabla.getNombresColumnas();
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
        
        List<String> listaDatos = adminTabla.getDatosTabla();
        
        int numFila = 1;
        for(String dato : listaDatos) {
        	String[] tokens = dato.split(",");
        	Row fila = sheet.createRow(numFila);
        	numFila++;
        	for(int i = 0 ; i < tokens.length; i++) {
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
			fileOut = new FileOutputStream("datos-exportados.xlsx");
			workbook.write(fileOut);
	        fileOut.close();

	        // Closing the workbook
	        workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        


	}

}
