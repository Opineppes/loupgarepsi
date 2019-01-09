package fr.epsi.lougar.controlleur;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ControlleurIndex implements HttpHandler {

	public void handle(HttpExchange t) throws IOException {
		String response = "Slt";
		
		t.getResponseHeaders().add("Content-Type", "text/html");
		t.sendResponseHeaders(200, response.length()); // Ou 0 (zro); contenu en morceaux
		
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
