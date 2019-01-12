package fr.epsi.loupgar.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileLoader {

	public static void writeFileOnStream(String path, OutputStream outputStream) throws IOException {
		InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(path);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		
		while(bufferedInputStream.available() > 0) {
			byte[] buffer = new byte[Short.MAX_VALUE];
			bufferedInputStream.read(buffer);
			outputStream.write(buffer);
		}
		
		inputStream.close();
	}

}
