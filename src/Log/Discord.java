package Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Discord {
	////////////////////////////////////////////////////////////////////////////
	//* Creating Logger object to log stuff                                  *//
	//* Getter for Logger object                                             *//
	////////////////////////////////////////////////////////////////////////////
	private static final Logger logger = LoggerFactory.getLogger(Discord.class);
	public static Logger getLogger()    {
		return logger;
	}
}
