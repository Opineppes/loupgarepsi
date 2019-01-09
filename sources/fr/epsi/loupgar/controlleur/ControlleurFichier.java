package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ControlleurFichier implements HttpHandler {


	private String filename;
	private String contentType = null;
	
	public ControlleurFichier(String filename) {
		this.filename = filename;
	}
	
	public ControlleurFichier(String filename, String contentType) {
		this.filename = filename;
		this.contentType = contentType;
	}
	
	public void handle(HttpExchange t) throws IOException 
	{
		if(contentType != null) t.getResponseHeaders().add("Content-Type", contentType);
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
