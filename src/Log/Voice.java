package Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Voice {
	////////////////////////////////////////////////////////////////////////////
	//* Creating Logger object to log stuff                                  *//
	//* Getter for Logger object of Voice class                              *//
	////////////////////////////////////////////////////////////////////////////
	private static final Logger logger = LoggerFactory.getLogger(Voice.class);
	public static Logger getLogger()    {
		return logger;
	}
}
