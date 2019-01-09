package fr.epsi.lougar.controlleur;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ControlleurFichier implements HttpHandler {


	private String filename;
	
	public ControlleurFichier(String filename) {
		this.filename = filename;
	}
	
	public void handle(HttpExchange t) throws IOException 
	{
		t.sendResponseHeaders(200, 0);
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
		OutputStream outputStream = t.getResponseBody();
		
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		outputStream.write(buffer);
		
		inputStream.close();
		outputStream.close();
	}

}
