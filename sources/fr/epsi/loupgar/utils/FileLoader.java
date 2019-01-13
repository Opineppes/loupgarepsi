package fr.epsi.loupgar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileLoader {

	public static void writeFileOnStream(String path, OutputStream outputStream) throws IOException {
		InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(path);
		
		while(inputStream.available() > 0) {
			byte[] buffer = new byte[Short.MAX_VALUE];
			int count = inputStream.read(buffer);
			outputStream.write(buffer, 0, count);
		}
		
		inputStream.close();
	}
	
	public static String[] readAllLines(File file) throws IOException
	{
		//on cree un tableau d'octet dans lequel on va mettre tout les octets du fichier
		FileInputStream inputStream = new FileInputStream(file);
		byte[] fileBytes = new byte[(int) file.length()];
		inputStream.read(fileBytes);
		inputStream.close();
		
		//On cree une chaine de caractere a partir des octets du fichier et on separe les lignes
		String fileContent = new String(fileBytes);
		return fileContent.split("\n");
	}
	
	public static void writeAllLines(File file, String[] lines) throws IOException
	{
		String content = "";
		for(String line : lines)
		{
			content += line + "\n";
		}
		content.substring(0, content.length() - 1);
		
		//on cree un tableau d'octet dans lequel on va mettre tout les octets du fichier
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(content.getBytes());
		outputStream.close();
	}

}
