package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {

	
	public Properties ReadAllProperty(String fileName){
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	public String getKeyValue(String fileName, String property){
		Properties result = null;
		result = ReadAllProperty(fileName);
		return result.getProperty(property);
	}

}
