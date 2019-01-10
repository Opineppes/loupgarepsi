package fr.epsi.loupgar;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fr.epsi.loupgar.controlleur.ControlleurFichier;
import fr.epsi.loupgar.controlleur.ControlleurIndex;

public class LoupGar {
	
	public static HttpServer server = null;

	public static void main(String[] args) {
		try {
			server = HttpServer.create(new InetSocketAddress(80), 0);
			
			//pour ajouter une page on la met a /
			server.createContext("/", new ControlleurIndex()/*new ControlleurFichier("publique/pages/connexion.html", "text/html")*/);
			server.createContext("/", new ControlleurFichier("publique/pages/jeu.html", "text/html"));
			
			//pour ajouter un fichier image, script, ... on le met a /res/
			server.createContext("/res/bootstrap.min.css", new ControlleurFichier("publique/css/bootstrap.min.css"));
			server.createContext("/res/style.css", new ControlleurFichier("publique/css/style.css"));
			
			server.createContext("/res/bootstrap.min.js", new ControlleurFichier("publique/js/bootstrap.min.js"));
			server.createContext("/res/jquery.min.js", new ControlleurFichier("publique/js/jquery-3.3.1.min.js"));
			
			server.createContext("/res/loupgarou.png", new ControlleurFichier("publique/images/loupgarou.png"));
			server.createContext("/res/chasseur.jpg", new ControlleurFichier("publique/images/chasseur.jpg"));
			server.createContext("/res/cupidon.jpg", new ControlleurFichier("publique/images/cupidon.jpg"));
			server.createContext("/res/villageois.jpg", new ControlleurFichier("publique/images/villageois.jpg"));
			server.createContext("/res/petitefille.jpg", new ControlleurFichier("publique/images/petitefille.jpg"));
			server.createContext("/res/sorciere.jpg", new ControlleurFichier("publique/images/sorciere.jpg"));
			server.createContext("/res/voyante.jpg", new ControlleurFichier("publique/images/voyante.jpg"));
			server.createContext("/res/village.jpg", new ControlleurFichier("publique/images/village.jpg"));
			
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
