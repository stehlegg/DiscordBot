package Core;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Config {
	private static Properties cfg;
	private static final File cfgFile = new File("cfg.properties");
	private static final Logger logger = Log.Config.getLogger();

	////////////////////////////////////////////////////////////////////////////
	//* Loading keys and values in specified config file in properties Obj   *//
	////////////////////////////////////////////////////////////////////////////
	public static void loadConfig() throws IOException {
		FileReader reader = new FileReader(cfgFile);
		cfg = new Properties();
		cfg.load(reader);
		reader.close();
	}

	////////////////////////////////////////////////////////////////////////////
	//* Write Key and Value in config property and to config file            *//
	////////////////////////////////////////////////////////////////////////////
	public static void writeConfig(String key, String value) throws IOException {
		cfg.put(key, value);
		FileWriter writer = new FileWriter(cfgFile);
		cfg.store(writer,null);
		writer.close();
	}

	////////////////////////////////////////////////////////////////////////////
	//* Loading Config and getter for specified values from cfg              *//
	////////////////////////////////////////////////////////////////////////////
	public static String getValue(String key) throws IOException {
		loadConfig();
		int len = cfg.getProperty(key).split(",").length;
		String[] arr = cfg.getProperty(key).split(",");
		Random rand = new Random();
		int random = rand.nextInt((len));
		logger.info("Retrieved Value for Key: " + key);
		return arr[random];
	}

	////////////////////////////////////////////////////////////////////////////
	//* Checking if a value for a specified key is present in the config     *//
	////////////////////////////////////////////////////////////////////////////
	public static boolean present(String key) throws IOException {
		loadConfig();
		return cfg.getProperty(key) != null;
	}
}
