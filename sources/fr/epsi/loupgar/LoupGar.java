package fr.epsi.loupgar;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fr.epsi.loupgar.controlleur.ControlleurFichier;

public class LoupGar {
	
	public static HttpServer server = null;

	public static void main(String[] args) {
		try {
			server = HttpServer.create(new InetSocketAddress(31000), 0);
			
			//pour ajouter une page on la met a /
			server.createContext("/", new ControlleurFichier("publique/pages/index.html", "text/html"));
			
			//pour ajouter un fichier image, script, ... on le met a /res/
			server.createContext("/res/image.png", new ControlleurFichier("publique/images/image.png"));
			
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
