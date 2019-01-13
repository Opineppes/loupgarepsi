package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fr.epsi.loupgar.model.Session;
import fr.epsi.loupgar.scripts.Script;

public class ControlleurScript implements HttpHandler {
	
	private String redirectUrl;
	private Map<String, Script> scripts;
	
	public ControlleurScript() 
	{
		this("/");
	}
	
	public ControlleurScript(String redirectUrl) 
	{
		this.redirectUrl = redirectUrl;
		scripts = new HashMap<String, Script>();
	}
	
	public void registerScript(String name, Script script)
	{
		scripts.put(name, script);
	}
	
	public void unregisterScript(String name)
	{
		scripts.remove(name);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestUrl = exchange.getRequestURI().toString();
		String requestRessource = requestUrl.substring(exchange.getHttpContext().getPath().length());
		if(scripts.containsKey(requestRessource))
		{
			Session session = Session.startSession(exchange);
			exchange.getResponseHeaders().add("Content-Type", "text/html");//"application/json");
			exchange.sendResponseHeaders(200, 0);
			
			scripts.get(requestRessource).execute(exchange, session);
			
			exchange.close();
		}
		else
		{
			exchange.getResponseHeaders().add("Location", redirectUrl);
			exchange.sendResponseHeaders(302, 0);
			exchange.close();
		}
	}

}
