package fr.epsi.loupgar.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class HttpParser {
	
	public static Map<String, String> parseCookies(HttpExchange exchange) {
		String cookiesLine = exchange.getRequestHeaders().get("Cookie").get(0);
		String[] cookies = cookiesLine.split("; ");
		
		Map<String, String> cookiesRes = new HashMap<String, String>();
		for(String cookie : cookies)
		{
			String[] part = cookie.split("=");
			cookiesRes.put(part[0], part[1]);
		}
		
		return cookiesRes;
	}
	
	public static Map<String, String> parsePostRequest(HttpExchange exchange) throws IOException {
		InputStream inputStream = exchange.getRequestBody();
		
		String postString = "";
		while(inputStream.available() > 0)
		{
			byte[] buffer = new byte[Short.MAX_VALUE];
			int count = inputStream.read(buffer);
			postString += new String(buffer, 0, count);
		}

		String[] postParts = postString.split("&");
		
		Map<String, String> post = new HashMap<String, String>();
		for(String postPart : postParts)
		{
			String[] part = postPart.split("=");
			post.put(part[0], part.length > 1 ? part[1] : "");
		}
		
		return post;
	}

}
