package fr.epsi.loupgar;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fr.epsi.loupgar.controlleur.ControlleurIndex;
import fr.epsi.loupgar.controlleur.ControlleurRessources;
import fr.epsi.loupgar.controlleur.ControlleurScript;
import fr.epsi.loupgar.scripts.ScriptConnexion;
import fr.epsi.loupgar.scripts.ScriptDeconnexion;

public class LoupGarou {
	
	public static HttpServer server = null;

	public static void main(String[] args) {
		
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
			
			controlleurRessources.registerRessources("bootstrap.min.css", "publique/css/bootstrap.min.css", "text/css");
			controlleurRessources.registerRessources("bootstrap.min.css.map", "publique/css/bootstrap.min.css.map");
			controlleurRessources.registerRessources("style.css", "publique/css/style.css", "text/css");
			
			controlleurRessources.registerRessources("bootstrap.min.js", "publique/js/bootstrap.min.js");
			controlleurRessources.registerRessources("bootstrap.min.js.map", "publique/js/bootstrap.min.js.map");
			controlleurRessources.registerRessources("jquery.min.js", "publique/js/jquery-3.3.1.min.js");
			controlleurRessources.registerRessources("script_connexion.js", "publique/js/script_connexion.js");
			controlleurRessources.registerRessources("script_jeu.js", "publique/js/script_jeu.js");
			
			controlleurRessources.registerRessources("sinister.ttf", "publique/font/sinister.ttf");
			
			//ajout des script dans le controlleur
			ControlleurScript controlleurScript = new ControlleurScript();
			
			controlleurScript.registerScript("connexion", new ScriptConnexion());
			controlleurScript.registerScript("deconnexion", new ScriptDeconnexion());
		
			
			//creation des context et demarrage du serveur
			server.createContext("/", new ControlleurIndex());
			server.createContext("/res/", controlleurRessources);
			server.createContext("/script/", controlleurScript);

			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}