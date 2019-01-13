package fr.epsi.loupgar.bdd;

public class TableAccount extends Table {

	public static final String NOM = "Nom";
	public static final String PRENOM = "Prenom";
	public static final String PSEUDO = "Pseudo";
	public static final String MDP = "Passwd";
	
	public TableAccount() {
		super("account", new String[] {NOM, PRENOM, PSEUDO, MDP});
	}
	
}
