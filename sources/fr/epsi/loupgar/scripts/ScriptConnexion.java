package fr.epsi.loupgar.scripts;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import fr.epsi.loupgar.model.Account;
import fr.epsi.loupgar.model.Session;
import fr.epsi.loupgar.utils.HttpParser;
import fr.epsi.loupgar.utils.Password;

public class ScriptConnexion implements Script {

	@Override
	public void execute(HttpExchange exchange, Session session) throws IOException {
		if(session.accountID == -1 && exchange.getRequestMethod().equals("POST"))
		{
			Map<String, String> post = HttpParser.parsePostRequest(exchange);
			
			if(post.containsKey("connexion-pseudo") && post.containsKey("connexion-passwd"))
			{
				String pseudo = post.get("connexion-pseudo");
				String password = Password.hash(post.get("connexion-passwd"));
				
				Account account = Account.fromPseudo(pseudo);
				if(account != null && account.passwd.equals(password))
				{
					session.accountID = account.getID();
					session.update();
				}
			}
		}
	}

}
