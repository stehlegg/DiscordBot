package Core;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
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
	//* Loading Config and getter for specified values from cfg              *//
	////////////////////////////////////////////////////////////////////////////
	public static String getValue(String key) throws IOException {
		loadConfig();
		/*
		int len = cfg.getProperty(key).split(",").length;
		String[] arr = cfg.getProperty(key).split(",");
		Random rand = new Random();
		int random = rand.nextInt((len));
		return arr[random];
		*/
		logger.info("Retrieved Value for Key: " + key);
		return cfg.getProperty(key);
	}

	////////////////////////////////////////////////////////////////////////////
	//* Checking if a value for a specified key is present in the config     *//
	////////////////////////////////////////////////////////////////////////////
	public static boolean isPresent(String key) throws IOException {
		loadConfig();
		return cfg.getProperty(key) != null;
	}
}
