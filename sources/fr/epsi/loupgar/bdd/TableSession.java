package fr.epsi.loupgar.bdd;

public class TableSession extends Table {
	
	public static final String SESSID = "SESSID";
	public static final String ACCOUNTID = "AccountID";
	
	public TableSession() {
		super("session", new String[] {SESSID, ACCOUNTID});
	}

}