package fr.epsi.loupgar.utils;

import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class HttpSession {
	
	private int identifiant;
	
	private HttpSession(int id)
	{
		
	}
	
	public static String startSession(HttpExchange exchange)
	{
		if(exchange.getRequestHeaders().containsKey("Cookie"))
		{
			String cookiesLine = exchange.getRequestHeaders().get("Cookie").get(0);
			String[] cookies = cookiesLine.split("; ");
			
			for(String cookie : cookies)
			{
				String[] part = cookie.split("=");
				if(part[0].equals("LGEPSISESSID"))
				{
					
				}
			}
		}

		int sessionID = (int) (Math.random() * Integer.MAX_VALUE);
		exchange.getResponseHeaders().add("Set-Cookie", "LGEPSISESSID=" + Integer.toString(sessionID));
		return "Session ID: " + Integer.toString(sessionID);
	}
}
