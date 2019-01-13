package fr.epsi.loupgar.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import fr.epsi.loupgar.bdd.TableSession;
import fr.epsi.loupgar.utils.HttpParser;

public class Session {
	
	private static TableSession tableSession = new TableSession();
	
	private int ID = -1;
	
	private String sessID;
	public int accountID;
	
	public Session(int ID) throws IOException
	{
		this.ID = ID;
		
		select();
	}
	
	public Session(String sessID) throws IOException
	{
		this.sessID = sessID;
		this.accountID = -1;
		
		insert();
	}
	
	private Session(Map<String, String> values)
	{
		this.ID = Integer.parseInt(values.get("Id"));
		
		this.sessID = values.get(TableSession.SESSID);
		this.accountID = Integer.parseInt(values.get(TableSession.ACCOUNTID));
	}
	
	public void insert() throws IOException
    {
        Map<String, String> session = new HashMap<String, String>();

        session.put(TableSession.SESSID, sessID);
        session.put(TableSession.ACCOUNTID, Integer.toString(accountID));
        
        ID = tableSession.insertOne(session);
    }

    public void update() throws IOException
    {
        Map<String, String> session = new HashMap<String, String>();

        session.put(TableSession.SESSID, sessID);
        session.put(TableSession.ACCOUNTID, Integer.toString(accountID));

        tableSession.updateOne(ID, session);
    }

    public void select() throws IOException
    {
        Map<String, String> session = tableSession.selectOne(ID);

        sessID = session.get(TableSession.SESSID);
        accountID = Integer.parseInt(session.get(TableSession.ACCOUNTID));
    }

    public void delete() throws IOException
    {
    	tableSession.deleteOne(ID);
    }
	
	@Override
	public String toString() {
		return "ID: " + ID + ", SessionID: " + sessID + ", AccountID: " + accountID;
	}
	
	public static List<Session> selectAll() throws IOException
    {
        List<Map<String, String>> sessions = tableSession.selectAll();
        List<Session> ret = new ArrayList<Session>();
        for(Map<String, String> session : sessions)
        {
            ret.add(new Session(session));
        }

        return ret;
    }
	
	public static Session startSession(HttpExchange exchange) throws IOException
	{
		if(exchange.getRequestHeaders().containsKey("Cookie"))
		{
			Map<String, String> cookies = HttpParser.parseCookies(exchange);
			if(cookies.containsKey("LGEPSISESSID"))
			{
				String sessionID = cookies.get("LGEPSISESSID");
				
				List<Session> sessions = selectAll();
				for(Session session : sessions)
				{
					if(session.sessID.equals(sessionID))
						return session;
				}
			}
		}

		String sessionID = Long.toHexString((long) (Math.random() * Long.MAX_VALUE));
		exchange.getResponseHeaders().add("Set-Cookie", "LGEPSISESSID=" + sessionID);
		return new Session(sessionID);
	}

}