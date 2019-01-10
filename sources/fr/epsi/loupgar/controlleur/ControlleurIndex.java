package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ControlleurIndex implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException
	{
		t.getResponseHeaders().add("Content-Type", "text/html");
		//t.getResponseHeaders().add("Set-Cookie", "LGEPSIESSID=yolo");
		t.sendResponseHeaders(200, 0);
		
		OutputStream outputStream = t.getResponseBody();
		for(String header : t.getRequestHeaders().keySet()) 
		{
			outputStream.write((header + ":\n").getBytes());
			List<String> list = t.getRequestHeaders().get(header);
			for(String value : list)
			{
				outputStream.write(("\t" + value + "\n").getBytes());
			}
			
		}
		outputStream.close();
	}
	
	

}
