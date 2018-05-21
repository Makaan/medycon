package medycon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
	
	public static void main(String[] args) {
		String hostname = "181.16.208.167";
        int port = 1000;
 
        try  {
        	Socket socket = new Socket(hostname, port);
        	System.out.println("conctando");
 
            InputStream input = socket.getInputStream();
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            writer.println("<01?ET>");
            
            String line = reader.readLine();
            System.out.println(line);
            
            System.out.println("cerando");
            socket.close();
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
	}
	    
}
