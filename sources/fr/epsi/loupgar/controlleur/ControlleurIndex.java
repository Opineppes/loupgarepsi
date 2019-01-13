package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import fr.epsi.loupgar.model.Session;
import fr.epsi.loupgar.utils.FileLoader;
import fr.epsi.loupgar.utils.HttpHandlerBlocker;

public class ControlleurIndex extends HttpHandlerBlocker {

	@Override
	public void handleIfNotBlocked(HttpExchange exchange) throws IOException 
	{
		Session session = Session.startSession(exchange);
		exchange.getResponseHeaders().add("Content-Type", "text/html");
		exchange.sendResponseHeaders(200, 0);

		//recuperation du flux de sortie
		OutputStream outputStream = exchange.getResponseBody();
		
		if(session.accountID != -1)
		{
			FileLoader.writeFileOnStream("publique/pages/jeu.html", outputStream);
		}
		else
		{
			FileLoader.writeFileOnStream("publique/pages/connexion.html", outputStream);
		}
		
		//fermeture du flux de sortie
		exchange.close();
	}
	
	

}