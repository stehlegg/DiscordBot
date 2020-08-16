package Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	////////////////////////////////////////////////////////////////////////////
	//* Creating Logger object to log stuff                                  *//
	//* Getter for Logger object                                             *//
	////////////////////////////////////////////////////////////////////////////
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	public static Logger getLogger()    {
		return logger;
	}
}
