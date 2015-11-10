package model;

import java.io.*;
import java.util.Properties;

/**
 * Created by Vitor on 05/11/2015.
 */
public class ConfigFile {

	private Properties configFile;

	public ConfigFile(String configFilePath) throws IOException {

		FileInputStream fis;
		fis = new FileInputStream(configFilePath);

		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		configFile = new Properties();
		configFile.load(in);
		in.close();
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * @param key The property key.
	 * @return The value in this property list with the specified key value.
	 */
	public String getProperty(String key) {
		return configFile.getProperty(key);
	}

	/**
	 * Creates a default config.properties file with example values.
	 * @param configFilePath Path where the config.properties file will be created.
	 */
	public static void createDefaultConfigFile(String configFilePath) {

		//TODO: Create default config.properties file.

	}
}
