package fr.epsi.loupgar.controlleur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import fr.epsi.loupgar.utils.FileLoader;

public class ControlleurRessources implements HttpHandler {
	
	private String redirectUrl;
	private Map<String, String> ressources;
	private Map<String, String> contentTypes;
	
	public ControlleurRessources() 
	{
		this("/");
	}
	
	public ControlleurRessources(String redirectUrl) 
	{
		this.redirectUrl = redirectUrl;
		ressources = new HashMap<String, String>();
		contentTypes = new HashMap<String, String>();
	}
	
	public void registerRessources(String name, String path)
	{
		ressources.put(name, path);
	}
	
	public void registerRessources(String name, String path, String contentType)
	{
		ressources.put(name, path);
		contentTypes.put(name, contentType);
	}
	
	public void unregisterRessources(String name)
	{
		ressources.remove(name);
		contentTypes.remove(name);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestUrl = exchange.getRequestURI().toString();
		String requestRessource = requestUrl.substring(exchange.getHttpContext().getPath().length());
		if(ressources.containsKey(requestRessource))
		{
			if(contentTypes.containsKey(requestRessource)) exchange.getResponseHeaders().add("Content-Type", contentTypes.get(requestRessource));
			exchange.sendResponseHeaders(200, 0);
			
			OutputStream outputStream = exchange.getResponseBody();
			FileLoader.writeFileOnStream(ressources.get(requestRessource), outputStream);
			
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
