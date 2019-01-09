package fr.epsi.lougar;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import fr.epsi.lougar.controlleur.ControlleurIndex;

public class LoupGar {
	
	public static HttpServer server = null;

	public static void main(String[] args) {
		try {
			server = HttpServer.create(new InetSocketAddress(31000), 0);
			
			server.createContext("/", new ControlleurIndex());
			
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
