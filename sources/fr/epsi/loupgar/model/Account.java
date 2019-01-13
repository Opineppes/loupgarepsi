package fr.epsi.loupgar.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.epsi.loupgar.bdd.TableAccount;

public class Account 
{

	private static TableAccount tableAccount = new TableAccount();

	private int ID = -1;

	public String nom;
	public String prenom;
	public String pseudo;
	public String passwd;

	public Account(int ID) throws IOException 
	{
		this.ID = ID;

		select();
	}

	public Account(String nom, String prenom, String pseudo, String passwd) throws IOException 
	{
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.passwd = passwd;

		insert();
	}

	private Account(Map<String, String> values) 
	{
		this.ID = Integer.parseInt(values.get("Id"));
		
		this.nom = values.get(TableAccount.NOM);
		this.prenom = values.get(TableAccount.PRENOM);
		this.pseudo = values.get(TableAccount.PSEUDO);
		this.passwd = values.get(TableAccount.MDP);
	}

	public void insert() throws IOException
	{
		Map<String, String> account = new HashMap<String, String>();

		account.put(TableAccount.NOM, nom);
		account.put(TableAccount.PRENOM, prenom);
		account.put(TableAccount.PSEUDO, pseudo);
		account.put(TableAccount.MDP, passwd);

		ID = tableAccount.insertOne(account);
	}

	public void update() throws IOException 
	{
		Map<String, String> account = new HashMap<String, String>();

		account.put(TableAccount.NOM, nom);
		account.put(TableAccount.PRENOM, prenom);
		account.put(TableAccount.PSEUDO, pseudo);
		account.put(TableAccount.MDP, passwd);

		tableAccount.updateOne(ID, account);
	}

	public void select() throws IOException 
	{
		Map<String, String> account = tableAccount.selectOne(ID);

		this.nom = account.get(TableAccount.NOM);
		this.prenom = account.get(TableAccount.PRENOM);
		this.pseudo = account.get(TableAccount.PSEUDO);
		this.passwd = account.get(TableAccount.MDP);
	}

	public void delete() throws IOException 
	{
		tableAccount.deleteOne(ID);
	}
	
	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "ID: " + ID + ", Nom: " + nom + ", Prenom: " + prenom + ", Pseudo: " + pseudo + ", MotDePasse: " + passwd;
	}

	public static List<Account> selectAll() throws IOException 
	{
		List<Map<String, String>> accounts = tableAccount.selectAll();
		List<Account> ret = new ArrayList<Account>();
		for (Map<String, String> account : accounts) {
			ret.add(new Account(account));
		}

		return ret;
	}
	
	public static Account fromPseudo(String pseudo) throws IOException
	{
		List<Map<String, String>> accounts = tableAccount.selectAll();
		
		for(Map<String, String> account : accounts)
		{
			if(account.get(TableAccount.PSEUDO).equals(pseudo))
			{
				return new Account(account);
			}
		}
		
		return null;
	}
}
