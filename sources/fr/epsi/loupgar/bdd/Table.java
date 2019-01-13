package fr.epsi.loupgar.bdd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.epsi.loupgar.utils.FileLoader;

public class Table {
	
	public static String tableDirectory = "bdd/";
	
	private String name;
	private String entete;
	
	public Table(String name, String[] keys)
	{
		this.name = name;
		
		//creation de l'entete par defaut
		entete = "";
		for(String key : keys)
		{
			entete += key + ";";
		}
		entete = entete.substring(0, entete.length() - 1);
	}
	
	public int insertOne(Map<String, String> values) throws IOException
	{
		try 
		{
			checkHeader();
			File tableFile = getTableFile();
			
			String[] csvLines = FileLoader.readAllLines(tableFile);

            String[] keys = csvLines[0].split(";");

            int id = Integer.parseInt(keys[0]);

            String line = keys[0] + ";";
            for(int i = 1; i < keys.length; i++)
            {
                line += (values.containsKey(keys[i]) ? values.get(keys[i]) : "null") + ";";
            }

            keys[0] = Integer.toString(Integer.parseInt(keys[0]) + 1);
            csvLines[0] = String.join(";", keys);

            String[] res = Arrays.<String>copyOf(csvLines, csvLines.length + 1);
            res[csvLines.length] = line.substring(0, line.length() - 1);

            FileLoader.writeAllLines(tableFile, res);

            return id;
		}
		catch(IndexOutOfBoundsException e)
		{
			backupError();
			return insertOne(values);
		}
	}
	
	public List<Map<String, String>> selectAll() throws IOException
    {
		try 
		{
			checkHeader();
	        String[] csvLines = FileLoader.readAllLines(getTableFile());
	        
	        String[] keys = csvLines[0].split(";");
	
	        List<Map<String, String>> liste_elements = new ArrayList<Map<String, String>>();
	        for (int i = 1; i < csvLines.length; i++)
	        {
	            String[] datas = csvLines[i].split(";");
	
	            Map<String, String> element = new HashMap<String, String>();
	            for (int j = 0; j < keys.length; j++)
	            {
	                element.put(j == 0 ? "Id" : keys[j], datas[j]);
	            }
	            liste_elements.add(element);
	        }
	
	        return liste_elements;
		}
		catch(IndexOutOfBoundsException e)
		{
			backupError();
			return new ArrayList<Map<String, String>>();
		}
    }
	
	public Map<String, String> selectOne(int id) throws IOException
    {
		try
		{
			checkHeader();
			String[] csvLines = FileLoader.readAllLines(getTableFile());
	
			String[] keys = csvLines[0].split(";");
	
	        for (int i = 1; i < csvLines.length; i++)
	        {
	        	String[] datas = csvLines[i].split(";");
	
	            if(datas[0].equals(Integer.toString(id)))
	            {
	                Map<String, String> element = new HashMap<String, String>();
	                for (int j = 1; j < keys.length; j++)
	                {
	                    element.put(keys[j], datas[j]);
	                }
	                return element;
	            }
	        }
	
	        return null;
		}
		catch(IndexOutOfBoundsException e)
		{
			backupError();
			return null;
		}
    }
	
	public void deleteOne(int id) throws IOException
    {
		try
		{
			checkHeader();
			File tableFile = getTableFile();
			
	        String[] csvLines = FileLoader.readAllLines(tableFile);
	
	        List<String> res = new ArrayList<String>();
	        res.add(csvLines[0]);
	
	        for (int i = 1; i < csvLines.length; i++)
	        {
	        	String[] datas = csvLines[i].split(";");
	
	            if (!datas[0].equals(Integer.toString(id)))
	            {
	                res.add(csvLines[i]);
	            }
	        }
	
	        FileLoader.writeAllLines(tableFile, res.toArray(new String[res.size()]));
	    }
		catch(IndexOutOfBoundsException e)
		{
			backupError();
		}
    }
	
	public void updateOne(int id, Map<String, String> values) throws IOException
    {
		try
		{
			checkHeader();
			File tableFile = getTableFile();
			
			String[] csvLines = FileLoader.readAllLines(tableFile);
	
			String[] keys = csvLines[0].split(";");
	
	        List<String> res = new ArrayList<String>();
	        res.add(csvLines[0]);
	
	        for (int i = 1; i < csvLines.length; i++)
	        {
	        	String[] datas = csvLines[i].split(";");
	
	            if (datas[0].equals(Integer.toString(id)))
	            {
	                for(int j = 1; j < keys.length; j++)
	                {
	                    if (values.containsKey(keys[j]))
	                        datas[j] = values.get(keys[j]);
	                }
	
	                res.add(String.join(";", datas));
	            }
	            else
	            {
	                res.add(csvLines[i]);
	            }
	        }
	
	        FileLoader.writeAllLines(tableFile, res.toArray(new String[res.size()]));
	    }
		catch(IndexOutOfBoundsException e)
		{
			backupError();
		}
    }
	
	private void backupError() throws IOException
	{
		File tableFile = getTableFile();
		
		//on renome le fichie en <tablename>.csv.old
		tableFile.renameTo(new File(tableFile.getParent(), this.name + ".csv.old"));
		
		//on en recree un nouveau
		tableFile.createNewFile();
		
		//et on ecrit l'entete dans le fichier
		FileOutputStream outputStream = new FileOutputStream(tableFile);
		outputStream.write(("0;" + entete).getBytes()); //on ajoute 0; ce qui va permetre l'autoincrementation
		outputStream.close();
	}
	
	private void checkHeader() throws IOException
	{
		//verification de l'entete du fichier
		File tableFile = getTableFile();
		String[] lines = FileLoader.readAllLines(tableFile);
		
		if(lines[0].equals(""))
		{
			//Si l'entete est vide on l'ecrit sur le fichier
			FileOutputStream outputStream = new FileOutputStream(tableFile);
			outputStream.write(("0;" + entete).getBytes()); //on ajoute 0; ce qui va permetre l'autoincrementation
			outputStream.close();
		}
		else if(!lines[0].substring(lines[0].indexOf(";") + 1).equals(entete))
		{
			// si l'entete ne corespond pas a l'entete prevu on fait une backup de l'ancien 
			//fichier et on en recrrer un avec la bonne entete
			backupError();
		}
	}

	private File getTableFile() throws IOException
	{
		//Verification de l'existance du fichier et de ses dossier parent
		//Si il n'existe pas on les cré
		File directory = new File(tableDirectory);
		
		if(!directory.exists()) directory.mkdirs();
		
		File tableFile = new File(directory, this.name + ".csv");
		
		if(!tableFile.exists()) tableFile.createNewFile();
		
		return tableFile;
	}

}