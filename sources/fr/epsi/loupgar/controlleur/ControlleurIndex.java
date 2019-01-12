package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import fr.epsi.loupgar.utils.HttpHandlerBlocker;
import fr.epsi.loupgar.utils.HttpSession;

public class ControlleurIndex extends HttpHandlerBlocker {

	@Override
	public void handleIfNotBlocked(HttpExchange exchange) throws IOException {
		String session = HttpSession.startSession(exchange);
		
		exchange.getResponseHeaders().add("Content-Type", "text/html");
		exchange.sendResponseHeaders(200, 0);
		
		//recuperation du flux de sortie
		OutputStream outputStream = exchange.getResponseBody();
		
		//affichage des infos contenu dans le header de la requete
		for(String header : exchange.getRequestHeaders().keySet()) 
		{
			outputStream.write((header + ":\n").getBytes());
			List<String> list = exchange.getRequestHeaders().get(header);
			for(String value : list)
			{
				outputStream.write(("\t" + value + "\n").getBytes());
			}
			
		}
		
		outputStream.write(("\n" + session).getBytes());
		
		//fermeture du flux de sortie
		exchange.close();
	}
	
	

}
