package fr.epsi.loupgar.utils;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class HttpHandlerBlocker implements HttpHandler {
	
	private String redirectLocation;
	
	public HttpHandlerBlocker() {
		redirectLocation = "/";
	}
	
	public HttpHandlerBlocker(String redirectLocation) {
		this.redirectLocation = redirectLocation;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		if(!arg0.getRequestURI().toString().equals(arg0.getHttpContext().getPath()))
		{
			arg0.getResponseHeaders().add("Location", redirectLocation);
			arg0.sendResponseHeaders(302, 0);
			arg0.close();
		}
		else
		{
			handleIfNotBlocked(arg0);
		}
	}
	
	public abstract void handleIfNotBlocked(HttpExchange exchange) throws IOException;

}
