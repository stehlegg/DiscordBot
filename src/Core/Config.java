package Core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Config {
	private static Properties cfg;

	public static void loadConfig() throws IOException {
		File cfgFile = new File("cfg.properties");
		FileReader reader = new FileReader(cfgFile);
		cfg = new Properties();
		cfg.load(reader);
		reader.close();
	}

	public static void writeConfig(String key, String value) {
		cfg.put(key, value);
	}

	public static String getValue(String key)  {
		int len = cfg.getProperty(key).split(",").length;
		String[] arr = cfg.getProperty(key).split(",");
		Random rand = new Random();
		int random = rand.nextInt((len));
		return arr[random];
	}

	public static boolean isDa(String key) {
		return cfg.getProperty(key) != null;
	}
}
