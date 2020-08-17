package pr0;

import Core.API;
import Core.Config;
import Log.pr0gramm;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login {
	private static final Logger logger = pr0gramm.getLogger();
	public static boolean loggedIn = false;
	public static void pr0Login(String captcha) throws IOException {

		////////////////////////////////////////////////////////////////////////////
		//* Getting Discord Presence Object and reloading Config for Hot Changes *//
		////////////////////////////////////////////////////////////////////////////
		Presence pres = API.getPres();
		Config.loadConfig();

		logger.info("Login requested");

		String name = Config.getValue("pr0-name");
		String pwd = Config.getValue("pr0-pwd");

		////////////////////////////////////////////////////////////////////////////
		//* Constructing the Body of our HTTP Request with info from Config file *//
		//* Token from the generated Captcha and argument by the User            *//
		////////////////////////////////////////////////////////////////////////////
		String body = "name=" + URLEncoder.encode(name,"UTF-8") + "&" +
				      "password=" + URLEncoder.encode(pwd,"UTF-8") + "&" +
				      "token=" + URLEncoder.encode(Captcha.token,"UTF-8") + "&" +
				      "captcha=" + URLEncoder.encode(captcha,"UTF-8");

		////////////////////////////////////////////////////////////////////////////
		//* Connection to given URL                                              *//
		//* Prepare connection for POST request                                  *//
		////////////////////////////////////////////////////////////////////////////
		URL url = new URL("https://pr0gramm.com/api/user/login/post/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(body.length()));

		////////////////////////////////////////////////////////////////////////////
		//* Send prepared Body as urlencoded POST request to the open Connection *//
		////////////////////////////////////////////////////////////////////////////
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(body);
		writer.flush();

		////////////////////////////////////////////////////////////////////////////
		//* Reading response from HTTPConnection                                 *//
		////////////////////////////////////////////////////////////////////////////
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output = reader.readLine();

		////////////////////////////////////////////////////////////////////////////
		//* Determining if mission was successful or we failed to log in         *//
		////////////////////////////////////////////////////////////////////////////
		if(output.contains("\"success\":true")) {
			logger.info("Login successful as:  via Captcha " + captcha + " for " + Captcha.token);
			pres.setPresence(OnlineStatus.ONLINE, Activity.playing("Logged in as: "));
			loggedIn = true;
			Captcha.capMsg = null;
		}   else    {
			pres.setPresence(OnlineStatus.DO_NOT_DISTURB,Activity.playing("Login failed - !login"));
		}
	}
}
