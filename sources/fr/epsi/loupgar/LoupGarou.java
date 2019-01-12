package fr.epsi.loupgar;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fr.epsi.loupgar.controlleur.ControlleurIndex;
import fr.epsi.loupgar.controlleur.ControlleurRessources;

public class LoupGarou {
	
	public static File sessionDirectory;
	
	public static HttpServer server = null;

	public static void main(String[] args) {
		sessionDirectory = new File("sessions");
		if(!sessionDirectory.exists()) sessionDirectory.mkdir();
		
		try {
			server = HttpServer.create(new InetSocketAddress(80), 0);
			
			//Creation du controlleur de ressource
			ControlleurRessources controlleurRessources = new ControlleurRessources();
			
			//ajout des ressource dans le controlleur
			controlleurRessources.registerRessources("loupgarou.png", "publique/images/loupgarou.png");
			controlleurRessources.registerRessources("chasseur.jpg", "publique/images/chasseur.jpg");
			controlleurRessources.registerRessources("cupidon.jpg", "publique/images/cupidon.jpg");
			controlleurRessources.registerRessources("villageois.jpg", "publique/images/villageois.jpg");
			controlleurRessources.registerRessources("petitefille.jpg", "publique/images/petitefille.jpg");
			controlleurRessources.registerRessources("sorciere.jpg", "publique/images/sorciere.jpg");
			controlleurRessources.registerRessources("voyante.jpg", "publique/images/voyante.jpg");
			controlleurRessources.registerRessources("village.jpg", "publique/images/village.jpg");
			
			controlleurRessources.registerRessources("bootstrap.min.css", "publique/css/bootstrap.min.css");
			controlleurRessources.registerRessources("style.css", "publique/css/style.css");
			
			controlleurRessources.registerRessources("bootstrap.min.js", "publique/js/bootstrap.min.js");
			controlleurRessources.registerRessources("jquery.min.js", "publique/js/jquery-3.3.1.min.js");
		
			
			server.createContext("/", new ControlleurIndex());
			server.createContext("/res/", controlleurRessources);

			
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
