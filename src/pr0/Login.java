package pr0;

import Core.API;
import Core.Config;
import Core.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.Presence;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login {
	public static void pr0Login(String captcha, TextChannel ch) throws IOException {

		////////////////////////////////////////////////////////////////////////////
		//* Getting Discord Presence Object and reloading Config for Hot Changes *//
		////////////////////////////////////////////////////////////////////////////
		Presence pres = API.getPres();
		Config.loadConfig();

		////////////////////////////////////////////////////////////////////////////
		//* Constructing the Body of our HTTP Request with info from Config file *//
		//* Token from the generated Captcha and argument by the User            *//
		////////////////////////////////////////////////////////////////////////////
		String body = "name=" + URLEncoder.encode(Config.getValue("pr0-name"),"UTF-8") + "&" +
				      "password=" + URLEncoder.encode(Config.getValue("pr0-pwd"),"UTF-8") + "&" +
				      "token=" + URLEncoder.encode(Captcha.token,"UTF-8") + "&" +
				      "captcha=" + URLEncoder.encode(captcha,"UTF-8");

		////////////////////////////////////////////////////////////////////////////
		//* Connection to given URL                                               *//
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
		//* Determining if mission was succesful or we failed to log in          *//
		////////////////////////////////////////////////////////////////////////////
		if(output.contains("\"success\":true")) {
			ch.sendMessage(output).queue();
			pres.setPresence(OnlineStatus.ONLINE, Activity.playing("pr0gramm eingeloggt"));
		}   else    {
			pres.setPresence(OnlineStatus.DO_NOT_DISTURB,Activity.playing("Login failed - !login"));
		}
	}
}
