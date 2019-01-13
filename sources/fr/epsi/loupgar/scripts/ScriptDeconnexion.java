package fr.epsi.loupgar.scripts;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import fr.epsi.loupgar.model.Session;

public class ScriptDeconnexion implements Script {

	@Override
	public void execute(HttpExchange exchange, Session session) throws IOException {
		session.delete();
	}

}
